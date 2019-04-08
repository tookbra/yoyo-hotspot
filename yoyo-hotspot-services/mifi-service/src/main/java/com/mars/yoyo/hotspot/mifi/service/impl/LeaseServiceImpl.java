package com.mars.yoyo.hotspot.mifi.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.pagehelper.PageHelper;
import com.mars.yoyo.hotspot.common.dps.config.MessageSourceHandler;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.client.DeviceClient;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.dao.LeaseMapper;
import com.mars.yoyo.hotspot.mifi.domain.*;
import com.mars.yoyo.hotspot.mifi.dto.LeaseDto;
import com.mars.yoyo.hotspot.mifi.dto.PaymentDto;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.dto.input.RentInputDto;
import com.mars.yoyo.hotspot.mifi.dto.input.RentReportInput;
import com.mars.yoyo.hotspot.mifi.dto.output.DeviceItemOutput;
import com.mars.yoyo.hotspot.mifi.enums.ClientTypeEnum;
import com.mars.yoyo.hotspot.mifi.enums.PayChannelEnum;
import com.mars.yoyo.hotspot.mifi.enums.ProductTypeEnum;
import com.mars.yoyo.hotspot.mifi.event.RentEvent;
import com.mars.yoyo.hotspot.mifi.event.ReturnEvent;
import com.mars.yoyo.hotspot.mifi.event.SmsEvent;
import com.mars.yoyo.hotspot.mifi.listener.RentListener;
import com.mars.yoyo.hotspot.mifi.pojo.UserCouponPojo;
import com.mars.yoyo.hotspot.mifi.service.*;
import com.mars.yoyo.hotspot.mifi.vo.LeaseDetailVo;
import com.mars.yoyo.hotspot.mifi.vo.LeaseRecordVo;
import com.mars.yoyo.hotspot.mifi.vo.LeaseStateVo;
import com.mars.yoyo.hotspot.mifi.vo.PreLeaseVo;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.SeqUtil;
import com.mars.yoyo.hotspot.util.StringUtil;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author tookbra
 * @date 2018/7/12
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class LeaseServiceImpl implements LeaseService {

    @Autowired
    LeaseMapper leaseMapper;

    @Autowired
    PayOrderService payOrderService;

    @Autowired
    ProductService productService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeliveryChannelService deliveryChannelService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    DeviceClient deviceClient;

    @Autowired
    CouponService couponService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    DeviceItemService deviceItemService;

    @Autowired
    MessageSourceHandler messageSourceHandler;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    FlowOrderService flowOrderService;

    @Autowired
    RentListener rentListener;


    @Override
    public LeaseStateVo findLeaseState(int userId) {
        Lease lease = this.findNotOver(userId);
        if(lease == null) {
            return null;
        }
        PayOrder payOrder = payOrderService.findByOrderNo(lease.getOrderNo());
        Product product = productService.findProductById(lease.getProductId().toString());
        LeaseStateVo leaseStateVo = new LeaseStateVo();
        leaseStateVo.setProductType(lease.getProductType());
        leaseStateVo.setProductName(lease.getProductName());
        leaseStateVo.setReturned(lease.getReturned());
        Date now = DateTime.now().toDate();
        int time = 0;
        if(lease.getProductType() > CommonConstant.PRODUCT_TYPE_HOUR) {
            if(lease.getReturned()) {
                if(lease.getReturnTime().after(lease.getExpectedReturnTime())) {
                    leaseStateVo.setPayed(false);
                } else {
                    leaseStateVo.setPayed(true);
                }
            } else {
                if(now.after(lease.getExpectedReturnTime())) {
                    leaseStateVo.setPayed(false);
                } else {
                    leaseStateVo.setPayed(true);
                }
                time = DateUtil.getIntervalSec(now, lease.getExpectedReturnTime());
            }
            leaseStateVo.setPrepaid(true);
        } else {
            leaseStateVo.setPrepaid(false);
            if(payOrder == null) {
                leaseStateVo.setPayed(false);
            } else {
                return null;
            }
            time = DateUtil.getIntervalSec(lease.getRentTime(), now);
        }
        BigDecimal amount = this.calculatedAmount(product, lease);
        leaseStateVo.setTime(time);
        leaseStateVo.setAmount(amount);
        return leaseStateVo;
    }

    @Override
    public LeaseDetailVo findDetail(int userId, String lang, boolean convert) {
        Lease lease = this.findNotOver(userId);
        if(lease == null) {
//            log.info("not found current rent. userId={}", userId);
//            throw new BizFeignException("biz.rent.current.not.fount", CommonConstant.CODE_RENT_CURRENT_NOT_FOUND);
            return null;
        }

        Date now = DateTime.now().toDate();
        LeaseDetailVo leaseDetailVo = new LeaseDetailVo();
        leaseDetailVo.setRentId(lease.getId());
        leaseDetailVo.setProductId(lease.getProductId());
        leaseDetailVo.setProductType(lease.getProductType());
        leaseDetailVo.setWifi(leaseDetailVo.getWifi() + lease.getPowerBankId().substring(lease.getPowerBankId().length() - 4));
        leaseDetailVo.setPwd(lease.getPassowrd());
        leaseDetailVo.setProductName(lease.getProductName());

        leaseDetailVo.setLocation(lease.getAddress());
        leaseDetailVo.setReturned(lease.getReturned());
        leaseDetailVo.setRentNo(lease.getLeaseNo());
        leaseDetailVo.setUsedSec(System.currentTimeMillis() - lease.getRentTime().getTime());
        leaseDetailVo.setDeviceType(messageSourceHandler.getMessage("deviceType"));
        if(lease.getExpectedReturnTime() != null) {
            leaseDetailVo.setExpectedReturnTime(lease.getExpectedReturnTime());
        }
        if(lease.getExpectedReturnTime() != null) {
            leaseDetailVo.setExpired(now.after(lease.getExpectedReturnTime()));
        }

        Product product = productService.findProductById(lease.getProductId().toString());
        if(lang.equals(CommonConstant.LANG_EN)) {
            leaseDetailVo.setPrice(product.getPriceEn());
        } else {
            leaseDetailVo.setPrice(product.getPrice());
        }

        // 转换后的使用详情页面，租借时间应为转换后的时间点为准，而不是从最初租用开始计算
//        if(StringUtil.isNotBlank(lease.getLastLeaseNo())) {
//            Lease lastLease = this.findByRentNo(lease.getLastLeaseNo());
//            leaseDetailVo.setUsedMin(DateUtil.getAbsIntervalMin(lastLease.getRentTime(), now));
//            leaseDetailVo.setRentTime(lastLease.getRentTime());
//        } else {
            leaseDetailVo.setUsedMin(DateUtil.getAbsIntervalMin(lease.getRentTime(), now));
            leaseDetailVo.setRentTime(lease.getRentTime());
//        }
        leaseDetailVo.setOrderNo(lease.getOrderNo());
        BigDecimal amount = this.calculatedAmount(product, lease);
        if(convert) {
            leaseDetailVo.setPrice(leaseDetailVo.getPrice().multiply(new BigDecimal(7)));
            amount = amount.multiply(new BigDecimal(7));
        }
        leaseDetailVo.setAmount(amount.setScale(2, RoundingMode.HALF_UP));
        return leaseDetailVo;
    }

    @Override
    public LeaseRecordVo findMyLeaseList(int userId, String lang) {
        LeaseRecordVo leaseRecordVo = new LeaseRecordVo();

        Example example = new Example(Lease.class);
        example.orderBy("rentTime").desc();
        example.createCriteria().andEqualTo("userId", userId).andEqualTo("state", 1);
        List<Lease> leaseList = leaseMapper.selectByExample(example);

        List<LeaseDetailVo> leaseDetailVos = new ArrayList<>();
        Date now = DateTime.now().toDate();
        leaseList.forEach(lease -> {
            Product product = productService.findProductById(lease.getProductId().toString());
            PayOrder payOrder = orderService.findByOrderNo(lease.getUserId(), lease.getOrderNo());
            LeaseDetailVo leaseDetailVo = new LeaseDetailVo();
            leaseDetailVo.setDevice(lease.getDeviceId());
            leaseDetailVo.setDeviceItem(lease.getPowerBankId());
            leaseDetailVo.setProductType(lease.getProductType());
            boolean isHour = product.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR;
            if(lang.equals(CommonConstant.LANG_EN)) {
                leaseDetailVo.setProductName(product.getProductNameEn());
                leaseDetailVo.setPrice(product.getPriceEn());
            } else {
                leaseDetailVo.setProductName(product.getProductName());
                leaseDetailVo.setPrice(product.getPrice());
            }
            Date rentTime = null;
            if(StringUtil.isNotBlank(lease.getLastLeaseNo())) {
                Lease lastLease = this.findByRentNo(lease.getLastLeaseNo());
                rentTime = lastLease.getRentTime();
            } else {
                rentTime = lease.getRentTime();

            }
            if(lease.getReturnTime() != null) {
                leaseDetailVo.setUsedMin(DateUtil.getAbsIntervalMin(rentTime, lease.getRentTime()));
            } else {
                leaseDetailVo.setUsedMin(DateUtil.getAbsIntervalMin(rentTime, now));
            }
            if(isHour) {
                int hour = DateUtil.getIntervalHour(lease.getRentTime(), now);
                leaseDetailVo.setPrice(leaseDetailVo.getPrice().multiply(new BigDecimal(hour)));
            } else {
                leaseDetailVo.setExpectedReturnTime(lease.getExpectedReturnTime());
            }
            if(payOrder == null) {
                leaseDetailVo.setPayed(false);
            } else {
                leaseDetailVo.setPayed(true);
            }
            leaseDetailVo.setReturned(lease.getReturned());
            if(lease.getReturnTime() != null) {
                leaseDetailVo.setUsedSec(lease.getReturnTime().getTime() - lease.getRentTime().getTime());
            } else {
                leaseDetailVo.setUsedSec(System.currentTimeMillis() - lease.getRentTime().getTime());
            }
            BigDecimal amount = this.calculatedAmount(product, lease);
            amount = amount.setScale(2, RoundingMode.HALF_UP);
            leaseDetailVo.setAmount(amount);
            leaseDetailVo.setRentTime(lease.getRentTime());
            leaseDetailVo.setLocation(lease.getAddress());
            leaseDetailVo.setRentNo(lease.getLeaseNo());
            leaseDetailVo.setDeviceType(messageSourceHandler.getMessage("deviceType"));
            if(lease.getOvered()) {
                leaseDetailVos.add(leaseDetailVo);
            } else {
                leaseRecordVo.setCurrentLease(leaseDetailVo);
            }
        });
        leaseRecordVo.setOldList(leaseDetailVos);
        return leaseRecordVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LeaseDetailVo rentHour(UserEnv userEnv, Integer productId) {
        Lease lease = this.findNotOver(userEnv.getUserId());
        if(lease != null) {
            log.info("device has not return or pay. leaseId={}", lease.getId());
            throw new BizFeignException("biz.rent.used");
        }

        // 查询是否有对应的机柜码
        Device device = deviceService.getByToken(userEnv.getChannelId());
        if(device == null) {
            log.info("device not found. channelId={}", userEnv.getChannelId());
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }

        int min = DateUtil.getIntervalMin(device.getLastHeart(), DateTime.now().toDate());
        if (min > 5) {
            log.info("device offline, channelId={}", userEnv.getChannelId());
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }

        // 查询是否有对应的渠道
        DeliveryChannel deliveryChannel = deliveryChannelService.findById(device.getDeliveryChannel());
        if(device == null) {
            log.info("deliveryChannel not found. deliveryChannel={}", device.getDeliveryChannel());
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }

        List<DeviceItem> deviceItemList = deviceItemService.findByDeviceId(device.getBoxId());
        if (CollectionUtils.isEmpty(deviceItemList)) {
            log.info("device has not item, channelId={}", userEnv.getChannelId());
            // 无可用设备
            throw new BizFeignException("biz.device.item.not.found", CommonConstant.DEVICE_ITEM_NOT_FOUND);
        }

        // 判断对应的渠道是否需要支付押金
        User user = userService.getByUserId(userEnv.getUserId());
        if(!user.getDeposited()) {
            log.info("user not pay deposit. userId={}", userEnv.getUserId());
            throw new BizFeignException("biz.deposit.not.pay", CommonConstant.CODE_DEPOSIT_NOT_PAY);
        }

        // 查询对应的产品是否存在
        Product product = productService.findProductById(productId.toString());
        if(product == null) {
            log.info("product not exist. userId={}", productId.toString());
            throw new BizFeignException("biz.product.not.found");
        }

        RestResult<DeviceItemOutput> restResult = deviceClient.findDeviceItem(device.getBoxId());
        if(!restResult.isSuccess()) {
            throw new BizFeignException("biz.device.not.found");
        }


        DeviceItemOutput deviceItemOutput = restResult.getData();
        String orderNo = SeqUtil.generatorId(userEnv.getUserId());
        String pwd = user.getPhone();
        Date now = DateTime.now().toDate();

        lease = new Lease();
        lease.setUserId(userEnv.getUserId());
        lease.setDeviceId(device.getBoxId());
        lease.setProductId(productId);
        lease.setProductType(product.getProductType());
        lease.setPassowrd(pwd);
        lease.setLeaseNo(orderNo);
        lease.setOrderNo(orderNo);
        lease.setRentTime(now);
        lease.setAddress(user.getEn() ? device.getAddressEn() : device.getAddress());
        lease.setDeviceId(deviceItemOutput.getDeviceId());
        lease.setPowerBankId(deviceItemOutput.getPowerBankId());
        lease.setSlot((int)deviceItemOutput.getSlot());

        LeaseDetailVo leaseDetailVo = new LeaseDetailVo();
        leaseDetailVo.setRentTime(now);
        leaseDetailVo.setLocation(lease.getAddress());
        leaseDetailVo.setRentNo(orderNo);
        leaseDetailVo.setWifi(leaseDetailVo.getWifi() + lease.getPowerBankId().substring(lease.getPowerBankId().length() - 4));
        leaseDetailVo.setPwd(pwd);
        leaseDetailVo.setDeviceType(messageSourceHandler.getMessage("deviceType"));
        leaseDetailVo.setAmount(user.getEn() ? product.getPriceEn() : product.getPrice());

        if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
            leaseDetailVo.setProductName(product.getProductNameEn());
            lease.setLangEn(true);
            lease.setProductName(product.getProductNameEn());
        } else {
            leaseDetailVo.setProductName(product.getProductName());
            lease.setProductName(product.getProductName());
        }
        lease.setReturned(false);
        this.addLease(lease);
        leaseDetailVo.setRentId(lease.getId());
        // push rent event
        rentListener.rentEvent(new RentEvent(this, device.getBoxId(), lease.getId()));
//        applicationContext.publishEvent(new RentEvent(this, device.getBoxId(), lease.getId()));
        return leaseDetailVo;
    }

    @Override
    public List<Lease> findLease() {
        Example example = new Example(Lease.class);
        example.createCriteria()
                .andEqualTo("overed", 0)
                .andEqualTo("state", 1);
        List<Lease> list = leaseMapper.selectByExample(example);
        return list;
    }

    @Override
    public Lease findLeaseById(int leaseId) {
        return leaseMapper.selectByPrimaryKey(leaseId);
    }

    @Override
    public Lease findByOrderId(String orderId) {
        Example example = new Example(Lease.class);
        example.createCriteria()
                .andEqualTo("orderNo", orderId);
        return leaseMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLease(Lease lease) {
        leaseMapper.insertSelective(lease);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PreLeaseVo changeRent(UserEnv userEnv, Integer productId, boolean convert) {



        // 查询对应的产品是否存在
        Product product = productService.findProductById(productId.toString());
        if(product == null) {
            log.info("product not exist. userId={}", productId.toString());
            throw new BizFeignException("biz.product.not.found");
        }

        PreLeaseVo preLeaseVo = new PreLeaseVo();
        LeaseDetailVo leaseDetailVo = this.findDetail(userEnv.getUserId(), userEnv.getLang(), false);
        boolean validate = true;
        if(leaseDetailVo != null) {
            if(leaseDetailVo.getAmount().compareTo(BigDecimal.ZERO) <=0) {
                validate = true;
                preLeaseVo.setUsedMin(DateUtil.getAbsIntervalMin(leaseDetailVo.getRentTime(), DateTime.now().toDate()));
                leaseDetailVo.setShow(false);
            } else {
                leaseDetailVo.setShow(true);
            }
        }

        preLeaseVo.setProductType(product.getProductType());
        preLeaseVo.setRentTime(DateTime.now().toDate());
        if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
            preLeaseVo.setProductName(product.getProductNameEn());
            preLeaseVo.setPrice(product.getPriceEn());
        } else {
            preLeaseVo.setProductName(product.getProductName());
            preLeaseVo.setPrice(product.getPrice());
        }
        if(leaseDetailVo == null || !leaseDetailVo.isShow()) {
            preLeaseVo.setPayAmount(preLeaseVo.getPrice());
        } else {
            if(!leaseDetailVo.isReturned() && leaseDetailVo.isExpired()) {
                BigDecimal amount = leaseDetailVo.getAmount().subtract(leaseDetailVo.getPrice());
                preLeaseVo.setPayAmount(preLeaseVo.getPrice().add(amount).setScale(2, RoundingMode.HALF_UP));
                leaseDetailVo.setAmount(amount.setScale(2, RoundingMode.HALF_UP));
            } else {
                preLeaseVo.setPayAmount(preLeaseVo.getPrice().add(leaseDetailVo.getAmount()).setScale(2, RoundingMode.HALF_UP));
            }
        }

        if(convert) {
            if (leaseDetailVo != null || !leaseDetailVo.isShow()) {
                leaseDetailVo.setAmount(leaseDetailVo.getAmount().multiply(new BigDecimal(7)));
            }
            preLeaseVo.setPayAmount(preLeaseVo.getPayAmount().multiply(new BigDecimal(7)));
        }

        if ((leaseDetailVo == null || !leaseDetailVo.isShow()) && !validate) {
            // 查询是否有对应的机柜码
            this.validate(userEnv);
        }
        preLeaseVo.setCurrentDetail(leaseDetailVo);
        return preLeaseVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrePayDto unifiedOrder(UserEnv userEnv, RentInputDto rentInputDto) throws WxPayException, PayPalRESTException {
        // 查询支付渠道
        PayChannelEnum payChannelEnum = PayChannelEnum.getChannel(rentInputDto.getPayChannel());

        if(StringUtils.isNotBlank(rentInputDto.getOrderNo())) {
            boolean flag = orderService.findByOrderNoQuery(rentInputDto.getOrderNo());
            if(flag) {
                PrePayDto prePayDto = new PrePayDto();
                prePayDto.setPayResult(true);
                return prePayDto;
            }
        }

        // 查询对应的产品是否存在
        Product product = productService.findProductById(rentInputDto.getProductId().toString());
        if(product == null) {
            log.info("product not exist. userId={}, productId={}", rentInputDto.getProductId());
            throw new BizFeignException("biz.product.not.found");
        }

        //当前待付金额
        BigDecimal currentAmount = BigDecimal.ZERO;
        LeaseDetailVo leaseDetailVo = this.findDetail(userEnv.getUserId(), userEnv.getLang(), false);
        if(leaseDetailVo != null) {
            //判断当前产品和未归还产品套餐是否一样，如果一样返回错误信息
//            if(product.getProductType() < leaseDetailVo.getProductType()) {
//                log.info("current product has exits, userId={}, productId={}",
//                        userEnv.getUserId(), product.getId());
//                throw new BizFeignException("biz.rent.used");
//            }
            if(leaseDetailVo.isExpired() || leaseDetailVo.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR) {
                currentAmount = leaseDetailVo.getAmount();
            }

            if(!leaseDetailVo.isReturned() && leaseDetailVo.isExpired()) {
                BigDecimal amount = leaseDetailVo.getAmount().subtract(leaseDetailVo.getPrice());
                currentAmount = amount;
            }
        } else {
            if (StringUtils.isBlank(userEnv.getChannelId())) {
                log.info("client no channelId");
                throw new BizFeignException("biz.device.not.found");
            } else {
                // 查询是否有对应的机柜码
                Device device = deviceService.getByToken(userEnv.getChannelId());
                if (device == null) {
                    log.info("device not found. channelId={}", userEnv.getChannelId());
                    throw new BizFeignException("biz.device.not.found");
                }

                // 查询是否有对应的渠道
                DeliveryChannel deliveryChannel = deliveryChannelService.findById(device.getDeliveryChannel());
                if (device == null) {
                    log.info("deliveryChannel not found. deliveryChannel={}", device.getDeliveryChannel());
                    throw new BizFeignException("biz.device.not.found");
                }
                // 判断对应的渠道是否需要支付押金
                User user = userService.getByUserId(userEnv.getUserId());
                // 押金都需要支付
                if (!user.getDeposited()) {
                    log.info("user not pay deposit. userId={}", userEnv.getUserId());
                    throw new BizFeignException("biz.deposit.not.pay", CommonConstant.CODE_DEPOSIT_NOT_PAY);
                }
            }
        }

        // 判断优惠券是否存在
        BigDecimal couponAmount = BigDecimal.ZERO;
        Date now = DateTime.now().toDate();
        int couponId = 0;
        if(rentInputDto.getCouponId() != null) {
            UserCouponPojo userCouponPojo = couponService.getByUserCouponId(userEnv.getUserId(), rentInputDto.getCouponId());
            if (userCouponPojo == null) {
                log.info("coupon not found. userId={}, couponId={}", userEnv.getUserId(), rentInputDto.getCouponId());
                throw new BizFeignException("biz.rent.coupon.not.found");
            }

            if (userCouponPojo.getEndDate().before(now)) {
                log.info("coupon has expired. userId={}, couponId={}", userEnv.getUserId(), rentInputDto.getCouponId());
                throw new BizFeignException("biz.rent.coupon.expired");
            }
            couponId = userCouponPojo.getCouponId();
            couponService.usedCoupon(rentInputDto.getCouponId());
            couponAmount = userCouponPojo.getMoney();
        }

        // 产品金额
        BigDecimal price = BigDecimal.ZERO;
        if(leaseDetailVo != null && leaseDetailVo.getProductType() > product.getProductType()
                && product.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR) {

        } else {
            if (userEnv.getLang().equals(CommonConstant.LANG_EN)) {
                price = product.getPriceEn();
            } else {
                price = product.getPrice();
            }
        }

        // 当前需要支付金额 = 当前待支付金额 + 产品金额 - 优惠券金额
        BigDecimal payAmount = currentAmount.add(price).subtract(couponAmount);
        if(payAmount.compareTo(BigDecimal.ZERO) <= 0) {
            payAmount = BigDecimal.ZERO;
        }
        UserAccount userAccount = userAccountService.getUser(userEnv.getUserId());
        PrePayDto prePayDto = new PrePayDto();
        String orderNo = SeqUtil.generatorId(userEnv.getUserId());

        PayOrder payOrder = new PayOrder();
        payOrder.setPayChannel(payChannelEnum.getCode());
        payOrder.setOrderId(orderNo);
        payOrder.setOrderType(CommonConstant.ORDER_TYPE_RENT);
        payOrder.setUserId(userEnv.getUserId());
        if (userEnv.getLang().equals("en") && rentInputDto.getPayChannel().equals(PayChannelEnum.WECHAT_PAY.getChannel())) {
            payOrder.setAmount(payAmount.multiply(new BigDecimal(7)));
        } else {
            payOrder.setAmount(payAmount);
        }
        payOrder.setPayIp(userEnv.getRequestIp());
        payOrder.setBody(product.getProductName());
        payOrder.setUserCouponId(couponId);

        PaymentDto paymentDto = new PaymentDto();
        if(leaseDetailVo != null) {
            paymentDto.setCurrentOrderNo(leaseDetailVo.getOrderNo());
            paymentDto.setCurrentRentNo(leaseDetailVo.getRentNo());
        }
        paymentDto.setProductId(rentInputDto.getProductId());
        paymentDto.setLang(userEnv.getLang());
        paymentDto.setChannelId(userEnv.getChannelId());
        paymentDto.setOrderNo(orderNo);
        paymentDto.setUserId(userEnv.getUserId());
        payOrder.setExt(JSON.toJSONString(paymentDto));

        if(payChannelEnum.getChannel().equals(PayChannelEnum.BALANCE.getChannel()) || payAmount.equals(BigDecimal.ZERO)) {
            BigDecimal balance = BigDecimal.ZERO;
            if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
                balance = userAccount.getBalanceEn();
            } else {
                balance = userAccount.getBalance();
            }
            if(balance.compareTo(payAmount) >= 0) {
                if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
                    userAccount.setBalanceEn(userAccount.getBalanceEn().subtract(payAmount));
                } else {
                    userAccount.setBalance(userAccount.getBalance().subtract(payAmount));
                }
                userAccountService.saveAccount(userAccount);
                payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
                prePayDto.setPayChannel(PayChannelEnum.BALANCE.getChannel());
                prePayDto.setPayResult(true);
                payOrder.setPaymentId(orderNo);
                paymentDto.setPayChannel(PayChannelEnum.BALANCE.getCode());
                prePayDto.setOrderNo(orderNo);
                orderService.addOrder(payOrder);
                this.rentPaySuccess(paymentDto);
            } else {
                throw new BizFeignException("biz.user.account.balance");
            }
        } else if(payChannelEnum.getChannel().equals(PayChannelEnum.WECHAT_PAY.getChannel())
                || payChannelEnum.getChannel().equals(PayChannelEnum.PAYPAL.getChannel())) {
            prePayDto = orderService.payAdpater(ClientTypeEnum.of(userEnv.getClientType()), rentInputDto.getPath(), payChannelEnum, payOrder);
        }
        return prePayDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PrePayDto unifiedOrderCurrent(UserEnv userEnv, RentInputDto rentInputDto) throws WxPayException, PayPalRESTException {
        PayChannelEnum payChannelEnum = PayChannelEnum.getChannel(rentInputDto.getPayChannel());

        if(StringUtils.isNotBlank(rentInputDto.getOrderNo())) {
            boolean flag = orderService.findByOrderNoQuery(rentInputDto.getOrderNo());
            if(flag) {
                PrePayDto prePayDto = new PrePayDto();
                prePayDto.setPayResult(true);
                return prePayDto;
            }
        }

        // 当前待支付金额
        BigDecimal currentAmount = BigDecimal.ZERO;
        LeaseDetailVo leaseDetailVo = this.findDetail(userEnv.getUserId(), userEnv.getLang(), false);
        if(leaseDetailVo == null) {
            throw new BizFeignException("biz.delivery.channel");
        }

        // 查询对应的产品是否存在
        Product product = productService.findProductById(leaseDetailVo.getProductId().toString());
        if(product == null) {
            log.info("product not exist. userId={}, productId={}", leaseDetailVo.getProductId());
            throw new BizFeignException("biz.product.not.found");
        }

        // 判断优惠券是否存在
        BigDecimal couponAmount = BigDecimal.ZERO;
        int couponId = 0;
        if(rentInputDto.getCouponId() != null) {
            UserCouponPojo userCouponPojo = couponService.getByUserCouponId(userEnv.getUserId(), rentInputDto.getCouponId());
            if (userCouponPojo == null) {
                log.info("coupon not found. userId={}, couponId={}", userEnv.getUserId(), rentInputDto.getCouponId());
                throw new BizFeignException("biz.rent.coupon.not.found");
            }
            Date now = new Date();
            if (userCouponPojo.getEndDate().before(now)) {
                log.info("coupon has expired. userId={}, couponId={}", userEnv.getUserId(), rentInputDto.getCouponId());
                throw new BizFeignException("biz.rent.coupon.expired");
            }
            couponService.usedCoupon(rentInputDto.getCouponId());
            couponAmount = userCouponPojo.getMoney();
            couponId = userCouponPojo.getCouponId();
        }

        // 当前需要支付金额 = 当前待支付金额 + 产品金额 - 优惠券金额
        BigDecimal payAmount = leaseDetailVo.getAmount().subtract(couponAmount);
        log.info("当前需要支付金额={}, userId={}", payAmount, userEnv.getUserId());
        log.info("当前支付渠道={}, userId={}", rentInputDto.getPayChannel(), userEnv.getUserId());
        payAmount = payAmount.setScale(2, RoundingMode.HALF_UP);
        if(payAmount.compareTo(BigDecimal.ZERO) <= 0) {
            payAmount = BigDecimal.ZERO;
        }
        UserAccount userAccount = userAccountService.getUser(userEnv.getUserId());
        PrePayDto prePayDto = new PrePayDto();
        String orderNo = SeqUtil.generatorId(userEnv.getUserId());

        PayOrder payOrder = new PayOrder();
        payOrder.setPayChannel(payChannelEnum.getCode());
        payOrder.setOrderId(orderNo);
        payOrder.setUserId(userEnv.getUserId());

        payOrder.setPayIp(userEnv.getRequestIp());
        payOrder.setBody(product.getProductName());
        payOrder.setUserCouponId(couponId);

        PaymentDto paymentDto = new PaymentDto();
        if(leaseDetailVo.getProductType() > CommonConstant.PRODUCT_TYPE_HOUR) {
            if(leaseDetailVo.isReturned()) {
                paymentDto.setPayType("return");
            } else {
                payOrder.setOrderType(CommonConstant.ORDER_TYPE_RENEWS);
                if(payAmount.compareTo(BigDecimal.ZERO) <= 0) {
                    payAmount = leaseDetailVo.getPrice();
                }
                paymentDto.setPayType("renews");
            }
        } else {
            payOrder.setOrderType(CommonConstant.ORDER_TYPE_RETURN);
            paymentDto.setPayType("return");

            // 小时借超过30
            if(leaseDetailVo.getAmount().compareTo(new BigDecimal(30)) > 0) {
                paymentDto.setPayType("exceed");
            }
        }
        paymentDto.setCurrentOrderNo(leaseDetailVo.getRentNo());
        if(userEnv.getLang().equals("en") && rentInputDto.getPayChannel().equals(PayChannelEnum.WECHAT_PAY.getChannel())) {
            payAmount = payAmount.multiply(new BigDecimal(7));
        }
        payOrder.setAmount(payAmount);
        paymentDto.setAmount(payAmount);
        paymentDto.setOrderNo(payOrder.getOrderId());
        paymentDto.setUserId(userEnv.getUserId());
        paymentDto.setProductId(rentInputDto.getProductId());
        paymentDto.setLang(userEnv.getLang());
        paymentDto.setChannelId(userEnv.getChannelId());
        payOrder.setExt(JSON.toJSONString(paymentDto));

        if(payChannelEnum == PayChannelEnum.BALANCE || payAmount.equals(BigDecimal.ZERO)) {
            BigDecimal balance = BigDecimal.ZERO;
            if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
                balance = userAccount.getBalanceEn();
            } else {
                balance = userAccount.getBalance();
            }
            if(balance.compareTo(payAmount) >= 0) {
                if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
                    userAccount.setBalanceEn(userAccount.getBalanceEn().subtract(payAmount));
                } else {
                    userAccount.setBalance(userAccount.getBalance().subtract(payAmount));
                }
                userAccountService.saveAccount(userAccount);
                paymentDto.setPayChannel(PayChannelEnum.BALANCE.getCode());

                orderService.addOrder(payOrder);
                rentPaySuccess(paymentDto);
                prePayDto.setPayResult(true);
                prePayDto.setPayChannel(PayChannelEnum.BALANCE.getChannel());
            } else {
                throw new BizFeignException("biz.user.account.balance");
            }
        } else if(payChannelEnum == PayChannelEnum.WECHAT_PAY || payChannelEnum == PayChannelEnum.PAYPAL) {
            prePayDto = orderService.payAdpater(ClientTypeEnum.of(userEnv.getClientType()), rentInputDto.getPath(), payChannelEnum, payOrder);
        }
        return prePayDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rentPaySuccess(PaymentDto paymentDto) {
        Date now = DateTime.now().toDate();

        User user = userService.getByUserId(paymentDto.getUserId());
        if(StringUtils.isNotBlank(paymentDto.getPayType())) {
            Lease lease = this.findByRentNo(paymentDto.getCurrentOrderNo());
            //续费
            if(paymentDto.getPayType().equals("renews")) {
                PayOrder payOrder = orderService.findByOrderNo(paymentDto.getUserId(), paymentDto.getOrderNo());
                if(payOrder == null) {
                    return;
                }
                payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
                payOrder.setModifyTime(now);
                orderService.save(payOrder);
                if(!lease.getReturned()) {
                    if (lease.getProductType() == CommonConstant.PRODUCT_TYPE_DAY) {
                        lease.setExpectedReturnTime(DateUtil.addDays(lease.getExpectedReturnTime(), 1));
                    } else if (lease.getProductType() == CommonConstant.PRODUCT_TYPE_MONTH) {
                        lease.setExpectedReturnTime(DateUtil.addMonths(lease.getExpectedReturnTime(), 1));
                    }
                }
                lease.setOrderNo(payOrder.getOrderId());
                lease.setModifyTime(now);
                this.saveLease(lease);

                String imei = lease.getPowerBankId().substring(1, lease.getPowerBankId().length());
                FlowOrder flowOrder = new FlowOrder();
                flowOrder.setPwd(lease.getPassowrd());
                flowOrder.setImei(imei);
                flowOrder.setTransId(String.valueOf(System.currentTimeMillis()));
                flowOrder.setLeaseId(lease.getId());
                String pkgId = flowOrderService.convertPkgId(lease);
                flowOrder.setPkgTypeId(pkgId);
                flowOrderService.enableFlow(flowOrder);
                return;
            }

            //归还
            if(paymentDto.getPayType().equals("return")) {
                PayOrder payOrder = new PayOrder();
                String orderNo = SeqUtil.generatorId(paymentDto.getUserId());
                payOrder.setOrderId(orderNo);
                payOrder.setUserId(paymentDto.getUserId());
                payOrder.setPayChannel(paymentDto.getPayChannel());
                payOrder.setAmount(paymentDto.getAmount());
                if(paymentDto.getPayChannel() == PayChannelEnum.PAYPAL.getCode()) {
                    payOrder.setCurrency("usd");
                } else {
                    payOrder.setCurrency("cny");
                }
                payOrder.setOrderType(CommonConstant.ORDER_TYPE_RENT);
                payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
                orderService.addOrder(payOrder);

                lease.setOrderNo(orderNo);
                lease.setModifyTime(now);
                lease.setOvered(true);
                this.saveLease(lease);
                return;
            }

            // 超过30
            if(paymentDto.getPayType().equals("exceed")) {
                PayOrder payOrder = orderService.findByOrderNo(paymentDto.getUserId(), paymentDto.getOrderNo());
                if(payOrder == null) {
                    return;
                }
                payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);
                payOrder.setModifyTime(now);
                orderService.save(payOrder);

//                lease.setOrderNo(orderNo);
                lease.setModifyTime(now);
                lease.setRentTime(now);
                String imei = lease.getPowerBankId().substring(1, lease.getPowerBankId().length());
                FlowOrder flowOrder = new FlowOrder();
                flowOrder.setPwd(lease.getPassowrd());
                flowOrder.setImei(imei);
                flowOrder.setTransId(String.valueOf(System.currentTimeMillis()));
                flowOrder.setLeaseId(lease.getId());
                String pkgId = flowOrderService.convertPkgId(lease);
                flowOrder.setPkgTypeId(pkgId);
                flowOrderService.enableFlow(flowOrder);

                this.saveLease(lease);
                return;
            }

        }

        // 有之前的订单, 说明是合并支付
        if(StringUtils.isNotBlank(paymentDto.getCurrentOrderNo())) {
            LeaseDetailVo leaseDetailVo = this.findDetail(paymentDto.getUserId(), paymentDto.getLang(), false);
            Lease currentLease = this.findById(leaseDetailVo.getRentId());
            boolean returned = false;
            returned = currentLease.getReturned();
            if(!returned) {
                currentLease.setReturnTime(now);
            }
            currentLease.setOvered(true);
            currentLease.setReturned(true);
            this.saveLease(currentLease);

            PayOrder payOrder = orderService.findByOrderNo(paymentDto.getUserId(), paymentDto.getOrderNo());
            payOrder.setState(CommonConstant.ORDER_STATE_SUCCESS);

            Product product = productService.findProductById(paymentDto.getProductId().toString());

            Lease lease = new Lease();
            if(StringUtils.isNotBlank(paymentDto.getChannelId())) {
                Device device = deviceService.getByToken(paymentDto.getChannelId());
                lease.setDeviceId(device.getBoxId());
                lease.setAddress(device.getAddress());
            } else {
                lease.setDeviceId(currentLease.getDeviceId());
                lease.setAddress(currentLease.getAddress());
            }
            lease.setPowerBankId(currentLease.getPowerBankId());
            lease.setUserId(paymentDto.getUserId());
            lease.setProductId(paymentDto.getProductId());
            String pwd = user.getPhone();
            lease.setPassowrd(pwd);
            lease.setLeaseNo(paymentDto.getOrderNo());
            lease.setLastLeaseNo(leaseDetailVo.getRentNo());
            lease.setAddress(currentLease.getAddress());
            if(product.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR) {
                // 小时借重新生成订单号
                String orderNo = SeqUtil.generatorId(paymentDto.getUserId());
                lease.setOrderNo(orderNo);
            } else {
                lease.setOrderNo(paymentDto.getOrderNo());
            }
            lease.setProductType(product.getProductType());
            if(paymentDto.getLang().equals(CommonConstant.LANG_EN) ) {
                lease.setProductName(product.getProductNameEn());
                lease.setLangEn(true);
            } else {
                lease.setProductName(product.getProductName());
            }
            lease.setRentTime(DateTime.now().toDate());
            lease.setState(1);
            if(product.getProductType() == CommonConstant.PRODUCT_TYPE_DAY) {
                lease.setExpectedReturnTime(DateUtil.addDays(now, 1));
            } else if(product.getProductType() == CommonConstant.PRODUCT_TYPE_MONTH) {
                lease.setExpectedReturnTime(DateUtil.addMonths(now, 1));
            }
            flowOrderService.closeFlow(leaseDetailVo.getRentId());
            this.addLease(lease);

            String imei = lease.getPowerBankId().substring(1, lease.getPowerBankId().length());
            FlowOrder flowOrder = new FlowOrder();
            flowOrder.setPwd(lease.getPassowrd());
            flowOrder.setImei(imei);
            flowOrder.setTransId(String.valueOf(System.currentTimeMillis()));
            flowOrder.setLeaseId(lease.getId());
            String pkgId = flowOrderService.convertPkgId(lease);
            flowOrder.setPkgTypeId(pkgId);
            flowOrderService.enableFlow(flowOrder);

        } else {
            // 查询是否有对应的机柜码
            Device device = deviceService.getByToken(paymentDto.getChannelId());
            Product product = productService.findProductById(paymentDto.getProductId().toString());

            String orderNo = SeqUtil.generatorId(paymentDto.getUserId());
            Lease lease = new Lease();
            lease.setUserId(paymentDto.getUserId());
            lease.setDeviceId(device.getBoxId());
            lease.setProductId(paymentDto.getProductId());
            lease.setProductType(product.getProductType());
            lease.setLeaseNo(orderNo);
            lease.setOrderNo(paymentDto.getOrderNo());
            if(paymentDto.getLang().equals(CommonConstant.LANG_EN) ) {
                lease.setProductName(product.getProductNameEn());
                lease.setLangEn(true);
            } else {
                lease.setProductName(product.getProductName());
            }
            lease.setRentTime(DateTime.now().toDate());
            if(paymentDto.getLang().equals(CommonConstant.LANG_EN)) {
                lease.setAddress(device.getAddressEn());
            } else {
                lease.setAddress(device.getAddress());
            }

            if(product.getProductType() == CommonConstant.PRODUCT_TYPE_DAY) {
                lease.setExpectedReturnTime(DateUtil.addDays(now, 1));
            } else if(product.getProductType() == CommonConstant.PRODUCT_TYPE_MONTH) {
                lease.setExpectedReturnTime(DateUtil.addMonths(now, 1));
            }

            if(device == null) {
                log.info("device not found. channelId={}", paymentDto.getChannelId());
                throw new BizFeignException("biz.device.not.found");
            }
            RestResult<DeviceItemOutput> restResult = deviceClient.findDeviceItem(device.getBoxId());
            if(!restResult.isSuccess()) {
                throw new BizFeignException("biz.device.not.found");
            }

            String pwd = user.getPhone();
            lease.setPassowrd(pwd);
            DeviceItemOutput deviceItemOutput = restResult.getData();
            lease.setPowerBankId(deviceItemOutput.getPowerBankId());
            this.addLease(lease);
            log.info(":{}", lease.getId());
            // push rent event
            rentListener.rentEvent(new RentEvent(this, device.getBoxId(), lease.getId()));
//            applicationContext.publishEvent(new RentEvent(this, device.getBoxId(), lease.getId()));
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLease(Lease lease) {
        leaseMapper.updateByPrimaryKeySelective(lease);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean returnDevice(String deviceId, String powerBankId) {
        DateTime now = new DateTime();

        Lease lease = this.findNotReturnByPowerId(powerBankId);
        if(lease == null) {
            log.info("returnReturn has no return powerBank item, deviceId={}, powerBankId={}", deviceId, powerBankId);
            return false;
        }
        log.info("return device item; deviceId={}, powerBankId={}, leaseId={}, userId={}",
                deviceId, powerBankId, lease.getId(), lease.getUserId());

        if(lease.getProductType() > CommonConstant.PRODUCT_TYPE_HOUR && now.toDate().before(lease.getExpectedReturnTime())) {
            lease.setOvered(true);
        }
        LeaseStateVo leaseStateVo = this.findLeaseState(lease.getUserId());
        lease.setReturned(true);
        lease.setReturnTime(now.toDate());
        lease.setModifyTime(now.toDate());
        lease.setDeviceId(deviceId);
        this.saveLease(lease);

        LeaseDto leaseDto = new LeaseDto();
        BeanUtils.copyProperties(lease, leaseDto);
        if(leaseStateVo != null) {
            leaseDto.setAmount(leaseStateVo.getAmount());
        }

        ReturnEvent returnEvent = new ReturnEvent(this, leaseDto);
        applicationContext.publishEvent(returnEvent);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rentReport(RentReportInput rentReportInput) {
        log.info("rentReportInput={}", rentReportInput.toString());
        PageHelper.startPage(1,1);
        Example example = new Example(Lease.class);
        example.createCriteria()
                .andEqualTo("deviceId", rentReportInput.getDeviceId())
                .andEqualTo("powerBankId", rentReportInput.getPowerBankId())
                .andEqualTo("state", 0).orEqualTo("state", 2);
        example.orderBy("rentTime").desc();
        Lease lease = leaseMapper.selectOneByExample(example);
        Date now = DateTime.now().toDate();
        if(lease != null) {
            lease.setModifyTime(now);
            lease.setState(rentReportInput.isSuccess() ? 1 : 2);
            lease.setSlot((int)rentReportInput.getSlot());
            this.saveLease(lease);

            if(rentReportInput.isSuccess()) {
                DeviceItem deviceItem = deviceItemService.findByPowerBankId(rentReportInput.getDeviceId(), rentReportInput.getPowerBankId());
                if(deviceItem != null) {
                    deviceItem.setLeased(true);
                    deviceItem.setModifyTime(now);
                    deviceItemService.save(deviceItem);
                }
            }

            rentListener.sendSms(rentReportInput.getDeviceId(), lease.getId());
        }
    }

    public Lease findNotOver(int userId) {
        Example example = new Example(Lease.class);
        example.createCriteria().andEqualTo("userId", userId)
                .andEqualTo("overed", 0)
                .andEqualTo("state", 1);
        Lease lease = leaseMapper.selectOneByExample(example);
        return lease;
    }

    public Lease findById(int id) {
        Lease lease = leaseMapper.selectByPrimaryKey(id);
        return lease;
    }

    public Lease findByRentNo(String rentNo) {
        Example example = new Example(Lease.class);
        example.createCriteria().andEqualTo("leaseNo", rentNo);
        Lease lease = leaseMapper.selectOneByExample(example);
        return lease;
    }

    public Lease findNotReturnByPowerId(String powerBankId) {
        Example example = new Example(Lease.class);
        PageHelper.startPage(1,1);
        example.orderBy("rentTime").desc();
        example.createCriteria()
                .andEqualTo("powerBankId", powerBankId)
                .andEqualTo("returned", 0)
                .andEqualTo("overed", 0)
                .andEqualTo("state", 1);
        Lease lease = leaseMapper.selectOneByExample(example);
        return lease;
    }

    public BigDecimal calculatedAmount(Product product, Lease lease) {
        Date now = DateTime.now().toDate();
        Date beginDate = null;
        Date endDate  = null;
        BigDecimal price = BigDecimal.ZERO;
        if(product.getProductType() == CommonConstant.PRODUCT_TYPE_HOUR) {
            beginDate = lease.getRentTime();
            endDate = now;
        } else {
            if(lease.getReturnTime() != null) {
                if(lease.getReturnTime().after(lease.getExpectedReturnTime())) {
                    beginDate = lease.getExpectedReturnTime();
                    endDate = lease.getReturnTime();
                }
            } else {
                if (now.after(lease.getExpectedReturnTime())) {
                    beginDate = lease.getExpectedReturnTime();
                    endDate = now;
                    if(lease.getLangEn()) {
                        price = product.getPriceEn();
                    } else {
                        price = product.getPrice();
                    }
                }
            }
        }
        BigDecimal amount = this.calculatedAmount(lease.getLangEn(), product.getProductType(), beginDate, endDate);
        amount = amount.add(price);
        return amount;
    }

    @Override
    public BigDecimal calculatedAmount(boolean en, int productType, Date beginTime, Date endTime) {
        if(beginTime == null || endTime == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal upperLimit = BigDecimal.ZERO;
        double hourLimit = 0D;
        if(en) {
            upperLimit = new BigDecimal(5);
            hourLimit = 0.5D;
        } else {
            upperLimit = new BigDecimal(12);
            hourLimit = 1.2D;
        }
        long newDate1 = beginTime.getTime() / 1000;
        long newDate2 = endTime.getTime() / 1000;
        long day = (newDate2- newDate1) / (60 * 60 * 24) +1;
        if(day == 1) {
            int hour = DateUtil.getIntervalHour(beginTime, endTime);
            amount = new BigDecimal(hour * hourLimit);
        } else {
            DateTime beginDateTime = DateUtil.dateToDateTime(beginTime);
            beginDateTime = beginDateTime.dayOfYear().roundCeilingCopy();

            int hour1 = DateUtil.getIntervalHour(beginTime, beginDateTime.toDate());
            BigDecimal amountDay1 = BigDecimal.ZERO;
            amountDay1 = new BigDecimal(hour1 * hourLimit);
            if(amountDay1.compareTo(upperLimit) > 0) {
                amountDay1 = upperLimit;
            }

            DateTime endDateTime = DateUtil.dateToDateTime(endTime);
            endDateTime = endDateTime.dayOfYear().roundCeilingCopy();
            int hour2 = DateUtil.getIntervalHour(endTime, endDateTime.toDate());
            BigDecimal amountDay2 = BigDecimal.ZERO;
            amountDay2 = new BigDecimal(hour2 * hourLimit);
            if(amountDay2.compareTo(upperLimit) > 0) {
                amountDay2 = upperLimit;
            }

            BigDecimal amountM = upperLimit.multiply(new BigDecimal((day - 2)));

            amount = amountDay1.add(amountDay2).add(amountM);
        }
        return amount;
    }

    @Override
    public boolean popDevice(int userId, int leaseId) {
        Example example = new Example(Lease.class);
        example.createCriteria()
                .andEqualTo("userId", userId)
                .andEqualTo("id", leaseId);
        Lease lease = leaseMapper.selectOneByExample(example);
        if(lease == null) {
            throw new BizFeignException("");
        }

        if(lease.getState() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean popUpReport(String deviceId, String powerBankId) {
        Example example = new Example(Lease.class);
        example.createCriteria()
                .andEqualTo("deviceId", deviceId)
                .andEqualTo("powerBankId", powerBankId).andEqualTo("state", 0);
        Lease lease = leaseMapper.selectOneByExample(example);
        if(lease == null || lease.getState() == 1) {
            return false;
        }
        lease.setState(1);
        lease.setModifyTime(new Date());
        leaseMapper.updateByPrimaryKeySelective(lease);
        rentListener.sendSms(deviceId, lease.getId());
        return true;
    }

    @Override
    public List<Lease> findNotOver() {
        Example example = new Example(Lease.class);
        example.orderBy("rentTime").asc();
        example.createCriteria()
                .andEqualTo("overed", 0).andEqualTo("state", 1);
        List<Lease> leases = leaseMapper.selectByExample(example);
        return leases;
    }

    private void checkDevice(String channelId) {
        if(StringUtils.isNotBlank(channelId)) {
            Device device = deviceService.getByToken(channelId);
            if (device == null) {
                log.info("device not found. channelId={}", channelId);
                throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
            }

//            int min = DateUtil.getIntervalMin(device.getLastHeart(), DateTime.now().toDate());
//            if (min > 5) {
//                log.info("device offline, channelId={}", userEnv.getChannelId());
//                throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
//            }

            // 查询是否有对应的渠道
            DeliveryChannel deliveryChannel = deliveryChannelService.findById(device.getDeliveryChannel());
            if (device == null) {
                log.info("deliveryChannel not found. deliveryChannel={}", device.getDeliveryChannel());
                throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
            }

            List<DeviceItem> deviceItemList = deviceItemService.findByDeviceId(device.getBoxId());
            if (CollectionUtils.isEmpty(deviceItemList)) {
                log.info("device has not item, channelId={}", channelId);
                // 无可用设备
                throw new BizFeignException("biz.device.item.not.found", CommonConstant.DEVICE_ITEM_NOT_FOUND);
            }
        } else {
            log.info("device not found. channelId={}", channelId);
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }
    }

    private void validate(UserEnv userEnv) {
        Device device = deviceService.getByToken(userEnv.getChannelId());
        if (device == null) {
            log.info("device not found. channelId={}", userEnv.getChannelId());
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }

        int min = DateUtil.getIntervalMin(device.getLastHeart(), DateTime.now().toDate());
        if (min > 5) {
            log.info("device offline, channelId={}", userEnv.getChannelId());
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }

        // 查询是否有对应的渠道
        DeliveryChannel deliveryChannel = deliveryChannelService.findById(device.getDeliveryChannel());
        if (device == null) {
            log.info("deliveryChannel not found. deliveryChannel={}", device.getDeliveryChannel());
            throw new BizFeignException("biz.device.not.found", CommonConstant.DEVICE_NOT_FOUND);
        }

        List<DeviceItem> deviceItemList = deviceItemService.findByDeviceId(device.getBoxId());
        if (CollectionUtils.isEmpty(deviceItemList)) {
            log.info("device has not item, channelId={}", userEnv.getChannelId());
            // 无可用设备
            throw new BizFeignException("biz.device.item.not.found", CommonConstant.DEVICE_ITEM_NOT_FOUND);
        }

        // 判断对应的渠道是否需要支付押金
        User user = userService.getByUserId(userEnv.getUserId());
        // 押金和渠道没关系
        if(!user.getDeposited()) {
            log.info("user not pay deposit. userId={}", userEnv.getUserId());
            throw new BizFeignException("biz.deposit.not.pay", CommonConstant.CODE_DEPOSIT_NOT_PAY);
        }
    }
}

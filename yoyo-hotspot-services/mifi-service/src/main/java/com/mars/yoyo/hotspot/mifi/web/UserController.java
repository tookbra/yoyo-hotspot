package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.exception.BizFeignException;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.domain.*;
import com.mars.yoyo.hotspot.mifi.dto.input.LoginInputDto;
import com.mars.yoyo.hotspot.mifi.dto.output.UserOrderStatusDto;
import com.mars.yoyo.hotspot.mifi.dto.output.UserOutputDto;
import com.mars.yoyo.hotspot.mifi.service.*;
import com.mars.yoyo.hotspot.mifi.vo.LeaseStateVo;
import com.mars.yoyo.hotspot.mifi.vo.RentInfoVo;
import com.mars.yoyo.hotspot.mifi.vo.UserVo;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.DateUtil;
import com.mars.yoyo.hotspot.util.NumberUtil;
import com.mars.yoyo.hotspot.util.PhoneUtil;
import com.mars.yoyo.hotspot.util.TransferUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Slf4j
@Api("用户模块")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserAccountService userAccountService;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedEnvelopeService redEnvelopeService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DeliveryChannelService deliveryChannelService;

    @Autowired
    LeaseService leaseService;

    /**
     * 获取登陆用户信息
     * @return
     */
    @ApiOperation(value = "获取登陆用户")
    @GetMapping
    @Security
    RestResult<UserVo> getUser(UserEnv userEnv) {
        User user = userService.getByUserId(userEnv.getUserId());
        UserVo userVo = TransferUtil.transferBean(user, UserVo.class);

        if(StringUtils.isNotBlank(userEnv.getChannelId())) {
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

//            if (deliveryChannel.getDeposit()) {
//                log.info("user not pay deposit. userId={}", userEnv.getUserId());
//                userVo.setNeedDeposited(true);
//            }
        }

        if(user != null) {
            userVo.setEn(user.getEn());
            UserAccount userAccount = userAccountService.getUser(user.getId());
            if(userAccount != null) {
                if(userEnv.getLang().equals(CommonConstant.LANG_EN)) {
                    userVo.setBalance(userAccount.getBalanceEn());
                    userVo.setDeposit(userAccount.getDepositEn());
                } else {
                    userVo.setBalance(userAccount.getBalance());
                    userVo.setDeposit(userAccount.getDeposit());
                }
                userVo.setCouponNum(userAccount.getCouponNum());
                userVo.setRedEnvelopeNum(userAccount.getRedEnvelopeNum());
                userVo.setIntegral(userAccount.getIntegral());
                userVo.setDeposited(user.getDeposited());
            }
        }

        LeaseStateVo leaseStateVo = leaseService.findLeaseState(userEnv.getUserId());
        if(leaseStateVo != null) {
            userVo.setLeaseStateVo(leaseStateVo);
        }
        return RestResult.success(userVo);
    }

    /**
     * 绑定手机
     *
     * @param phone 手机号
     * @param code 验证码
     * @param channel 渠道
     * @return
     */
    @PostMapping("/bind")
    RestResult bindUser(@RequestParam String phone, @RequestParam String code, @RequestParam String channel) {
        if(!PhoneUtil.isMobileExact(phone)) {
            return RestResult.error("手机号码格式错误");
        }
        BigDecimal couponMoney = userService.bindUser(phone, code);
        return RestResult.success(couponMoney);
    }

    /**
     *
     * @param userEnv 用户环境
     * @param code 微信编码
     * @return
     */
    @PostMapping("/bind/wx")
    @Security
    RestResult wxToken(UserEnv userEnv, @RequestParam String code) {
        userAuthService.bindWx(userEnv.getUserId(), code);
        return RestResult.success("");
    }

    /**
     * 验证用户
     * @param loginInputDto
     * @return
     */
    @PostMapping("/validate")
    RestResult<UserOutputDto> validateUser(@RequestBody @Valid LoginInputDto loginInputDto, BindingResult result) {
        UserOutputDto userOutputDto = userAuthService.validate(loginInputDto);
        return RestResult.success(userOutputDto);
    }

    /**
     * 登陆
     * @param loginInputDto
     * @param result
     * @return
     */
    @PostMapping("/login")
    RestResult<UserOutputDto> login(@RequestBody @Valid LoginInputDto loginInputDto, BindingResult result) {
        UserOutputDto userOutputDto = userAuthService.validate(loginInputDto);
        return RestResult.success(userOutputDto);
    }

    /**
     * 领取红包
     * @param userEnv
     * @return
     */
    @PostMapping("/receiveRed")
    @Security
    RestResult receiveRed(UserEnv userEnv) {
        BigDecimal amount = redEnvelopeService.receiveRed(userEnv.getUserId(), userEnv.getLang());
        return RestResult.success(amount);
    }

}

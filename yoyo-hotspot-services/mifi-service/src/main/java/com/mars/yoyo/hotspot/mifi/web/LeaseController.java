package com.mars.yoyo.hotspot.mifi.web;

import com.github.binarywang.wxpay.exception.WxPayException;
import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.dto.PrePayDto;
import com.mars.yoyo.hotspot.mifi.dto.input.RentInputDto;
import com.mars.yoyo.hotspot.mifi.dto.input.RentReportInput;
import com.mars.yoyo.hotspot.mifi.service.LeaseService;
import com.mars.yoyo.hotspot.mifi.vo.*;
import com.mars.yoyo.hotspot.result.RestResult;
import com.paypal.base.rest.PayPalRESTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/7/13
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/lease")
public class LeaseController {

    @Autowired
    LeaseService leaseService;

    /**
     * 当前租赁状态
     * @param userEnv
     * @return
     */
    @GetMapping("/state")
    @Security
    RestResult<LeaseStateVo> getState(UserEnv userEnv) {
        LeaseStateVo leaseDetailVo = leaseService.findLeaseState(userEnv.getUserId());
        return RestResult.success(leaseDetailVo);
    }

    /**
     * 当前使用详情
     * @param userEnv
     * @return
     */
    @PostMapping("/current/detail")
    @Security
    RestResult<LeaseDetailVo> getCurrentDetail(UserEnv userEnv, @RequestParam(required = false, defaultValue = "0") int convert) {
        LeaseDetailVo leaseDetailVo = leaseService.findDetail(userEnv.getUserId(), userEnv.getLang(), convert == 1);
        return RestResult.success(leaseDetailVo);
    }


    /**
     * 我的租借记录
     * @param userEnv
     * @return
     */
    @GetMapping
    @Security
    RestResult<LeaseRecordVo> getMyRent(UserEnv userEnv) {
        LeaseRecordVo leaseRecordVo = leaseService.findMyLeaseList(userEnv.getUserId(), userEnv.getLang());
        return RestResult.success(leaseRecordVo);
    }

    /**
     * 租赁 - 小时借
     * @param productId 产品id
     * @return
     */
    @PostMapping("/hour/{productId}")
    @Security
    RestResult<LeaseDetailVo> rentHour(@PathVariable int productId, UserEnv userEnv) {
        LeaseDetailVo leaseDetailVo = leaseService.rentHour(userEnv, productId);
        return RestResult.success(leaseDetailVo);
    }

    /**
     * 租赁
     * @param productId 产品id
     * @return
     */
    @PostMapping("/{productId}")
    @Security
    RestResult<PreLeaseVo> preRent(@PathVariable int productId, UserEnv userEnv, @RequestParam(required = false, defaultValue = "0") int convert) {
        PreLeaseVo preLeaseVo = leaseService.changeRent(userEnv, productId, convert == 1);
        return RestResult.success(preLeaseVo);
    }

    /**
     * 租赁 - 统一下单
     * @param userEnv
     *              用户环境
     * @param productId
     *              产品id
     * @param rentInputDto
     * @return
     * @throws PayPalRESTException
     * @throws WxPayException
     */
    @PostMapping("/{productId}/order")
    @Security
    RestResult<PrePayDto> order(UserEnv userEnv,
                     @PathVariable int productId,
                     RentInputDto rentInputDto) throws PayPalRESTException, WxPayException {
        rentInputDto.setProductId(productId);
        PrePayDto prePayDto = leaseService.unifiedOrder(userEnv, rentInputDto);
        return RestResult.success(prePayDto);
    }

    /**
     * 租赁 - 正在使用- 统一下单
     * @param userEnv
     * @return
     */
    @PostMapping("/current/pay")
    @Security
    RestResult<PrePayDto> payCurrent(UserEnv userEnv, RentInputDto rentInputDto) throws PayPalRESTException, WxPayException {
        PrePayDto prePayDto = leaseService.unifiedOrderCurrent(userEnv, rentInputDto);
        return RestResult.success(prePayDto);
    }

    /**
     * 归还充电宝
     * @param deviceId 设备id
     * @param powerBankId 充电宝id
     * @return
     */
    @GetMapping("/return")
    RestResult rentReturn(@RequestParam("deviceId") String deviceId,
                          @RequestParam("powerBankId") String powerBankId) {
        boolean returnResult = leaseService.returnDevice(deviceId, powerBankId);
        return RestResult.success(returnResult);
    }

    /**
     * 租借报告
     * @param rentReportInput {@link RentReportInput}
     * @return {@link RestResult}
     */
    @PostMapping("/report")
    RestResult rentReport(@RequestBody RentReportInput rentReportInput) {
        leaseService.rentReport(rentReportInput);
        return RestResult.success("");
    }

    /**
     * 查询是否弹出设备
     * @param userEnv
     * @param leaseId
     * @return
     */
    @PostMapping("/device/{leaseId}/pop")
    RestResult popDevice(UserEnv userEnv, @PathVariable int leaseId) {
        boolean flag = leaseService.popDevice(userEnv.getUserId(), leaseId);
        return RestResult.success(flag);
    }

    @GetMapping("/popReport")
    RestResult popReport(@RequestParam("deviceId") String deviceId,
                         @RequestParam("powerBankId") String powerBankId) {
        boolean flag = leaseService.popUpReport(deviceId, powerBankId);
        return RestResult.success(flag);
    }
}

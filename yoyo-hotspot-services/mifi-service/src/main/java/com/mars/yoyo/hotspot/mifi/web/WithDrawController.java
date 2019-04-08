package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.common.dps.annotation.Security;
import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.service.WithDrawService;
import com.mars.yoyo.hotspot.mifi.vo.WithDrawVo;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现
 * @author tookbra
 * @date 2018/6/21
 * @description
 */
@RestController
@RequestMapping("/withdraw")
public class WithDrawController {


    @Autowired
    WithDrawService withDrawService;

    @Security
    @PostMapping("/apply")
    RestResult apply(UserEnv userEnv) {
        WithDrawVo withDrawVo = withDrawService.apply(userEnv.getUserId());
        return RestResult.success(withDrawVo);
    }

}

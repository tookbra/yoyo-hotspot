package com.mars.yoyo.hotspot.admin.web.lease;

import com.mars.yoyo.hotspot.admin.params.LeaseParameter;
import com.mars.yoyo.hotspot.admin.resutls.LeaseView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 租借接口
 *
 * @author admin
 * @Date 2018/9/5 17:08
 */
@Controller
@RequestMapping("/lease")
public class LeaseController {

    @Autowired
    private LeaseService leaseService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "lease/list";
    }

    /**
     * 查询订单列表
     * @param parameter
     * @return
     */
    @ResponseBody
    @GetMapping("/listLeaseView")
    public ListResultEx<LeaseView> listLeaseView(LeaseParameter parameter) {
        return leaseService.listLeaseView(parameter);
    }


}

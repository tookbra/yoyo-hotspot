package com.mars.yoyo.hotspot.admin.web.order;

import com.mars.yoyo.hotspot.admin.params.OrderParameter;
import com.mars.yoyo.hotspot.admin.resutls.PayOrderView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单接口
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "order/list";
    }

    /**
     * 查询订单列表
     * @param parameter
     * @return
     */
    @ResponseBody
    @GetMapping("/listOrders")
    public ListResultEx<PayOrderView> listOrders(OrderParameter parameter) {
        return orderService.getOrderList(parameter);
    }

}

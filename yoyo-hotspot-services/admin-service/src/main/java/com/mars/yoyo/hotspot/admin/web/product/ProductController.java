package com.mars.yoyo.hotspot.admin.web.product;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.ProductParameter;
import com.mars.yoyo.hotspot.admin.resutls.ProductView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.ProductService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 产品套餐接口
 *
 * @author admin
 * @create 2018/5/23
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "product/list";
    }

    /**
     * 产品(套餐)列表
     * @return
     */
    @ResponseBody
    @GetMapping("/listProducts")
    public ListResultEx<ProductView> listProducts(CommonParameter parameter) {
        return productService.getProductList(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(){
        return "product/add";
    }

    /**
     * 新增套餐
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult addProduct(ProductParameter parameter) {
        return productService.addProduct(parameter);
    }

    /**
     * 删除套餐
     * @param productId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{productId}")
    public RestResult deleteProduct(@PathVariable Integer productId) {
        return productService.deleteProduct(productId);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{productId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer productId){
        RestResult result = productService.getProductById(productId);
        if (result.getData() != null) {
            modelMap.addAttribute("product", result.getData());
        } else {
            return result.getMsg();
        }
        return "product/edit";
    }

    /**
     * 更新套餐
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateProduct(ProductParameter parameter) {
        return productService.updateProduct(parameter);
    }

}

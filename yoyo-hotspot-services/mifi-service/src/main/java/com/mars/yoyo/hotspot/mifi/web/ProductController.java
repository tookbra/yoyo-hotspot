package com.mars.yoyo.hotspot.mifi.web;

import com.mars.yoyo.hotspot.common.dps.secutiry.UserEnv;
import com.mars.yoyo.hotspot.mifi.constant.CommonConstant;
import com.mars.yoyo.hotspot.mifi.domain.Product;
import com.mars.yoyo.hotspot.mifi.service.ProductService;
import com.mars.yoyo.hotspot.mifi.vo.CurrentProductVo;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.NumberUtil;
import com.mars.yoyo.hotspot.util.TransferUtil;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品控制器
 * @author tookbra
 * @date 2018/5/14
 * @description
 */
@Api("产品模块")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 获取生效的产品
     * @return
     */
    @GetMapping()
    public RestResult<List<CurrentProductVo>> getCurrentProduct(@RequestHeader String lang) {
        List<Product> productList = productService.getCurrentProduct();
        List<CurrentProductVo> list = new ArrayList<>();
        productList.forEach(product -> {
            CurrentProductVo currentProductVo = new CurrentProductVo();
            currentProductVo.setId(product.getId());
            currentProductVo.setProductType(product.getProductType());
            currentProductVo.setUnlimited(product.getUnlimited());
            currentProductVo.setGifted(product.getGifted());
            if(lang.equals(CommonConstant.LANG_EN)) {
                currentProductVo.setDescription(product.getDescriptionEn());
                currentProductVo.setPrice(product.getPriceEn());
                currentProductVo.setProductName(product.getProductNameEn());
                currentProductVo.setGiftContent(product.getGiftMsgEn());
                if(StringUtils.isBlank(product.getGiftMsgEn())) {
                    currentProductVo.setGifted(false);
                }
            } else {
                currentProductVo.setDescription(product.getDescription());
                currentProductVo.setPrice(product.getPrice());
                currentProductVo.setProductName(product.getProductName());
                currentProductVo.setGiftContent(product.getGiftMsg());
                if(StringUtils.isBlank(product.getGiftMsg())) {
                    currentProductVo.setGifted(false);
                }
            }
            list.add(currentProductVo);
        });
        return RestResult.success(list);
    }

    /**
     * 获取产品信息
     * @param id 产品id
     * @return
     */
    @GetMapping("/{id}")
    public RestResult<CurrentProductVo> getCurrentProduct(@PathVariable Integer id) {
        Product product = productService.findProductById(id.toString());
        CurrentProductVo currentProductVo = new CurrentProductVo();
        TransferUtil.transfer(product, currentProductVo);
        return RestResult.success(currentProductVo);
    }

}

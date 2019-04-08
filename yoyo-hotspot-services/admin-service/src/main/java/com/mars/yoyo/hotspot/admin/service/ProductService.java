package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.ProductParameter;
import com.mars.yoyo.hotspot.admin.resutls.ProductView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 产品(套餐接口)
 *
 * @author admin
 * @create 2018/5/14
 * @since 1.0.0
 */
public interface ProductService {

    /**
     * 产品(套餐)列表
     * @return
     */
    ListResultEx<ProductView> getProductList(CommonParameter parameter);

    /**
     * 新增套餐
     * @param parameter
     * @return
     */
    RestResult addProduct(ProductParameter parameter);

    /**
     * 删除套餐
     * @param productId
     * @return
     */
    RestResult deleteProduct(Integer productId);

    /**
     * 更新套餐
     * @param parameter
     * @return
     */
    RestResult updateProduct(ProductParameter parameter);

    /**
     * 查询套餐
     * @param productId
     * @return
     */
    RestResult getProductById(Integer productId);

}

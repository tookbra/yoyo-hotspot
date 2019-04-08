package com.mars.yoyo.hotspot.mifi.service;

import com.mars.yoyo.hotspot.mifi.domain.Product;

import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
public interface ProductService {

    /**
     * 首页产品信息
     * @return {@link Product}
     */
    List<Product> getCurrentProduct();

    /**
     * 获取产品信息
     * @param id 产品id
     * @return
     */
    Product findProductById(String id);

    /**
     * 获取产品信息
     * @param productId 产品id
     * @return {@link Product}
     */
    Product getById(int productId);
}

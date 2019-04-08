package com.mars.yoyo.hotspot.mifi.service.impl;

import com.mars.yoyo.hotspot.mifi.constant.RedisConstant;
import com.mars.yoyo.hotspot.mifi.dao.ProductMapper;
import com.mars.yoyo.hotspot.mifi.domain.Product;
import com.mars.yoyo.hotspot.mifi.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/5/15
 * @description
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = true)
public class ProductServiceImpl implements ProductService {

    @Resource
    ProductMapper productMapper;

    @Override
    public List<Product> getCurrentProduct() {
        Example example = new Example(Product.class);
        example.createCriteria().andEqualTo("status", 1);
        List<Product> list = productMapper.selectByExample(example);
        return list;
    }

    @Override
    @Cacheable(cacheNames = RedisConstant.PRODUCT, key = "#p0", cacheManager = "daysCacheManager")
    public Product findProductById(String id) {
        Product product = productMapper.selectByPrimaryKey(Integer.parseInt(id));
        return product;
    }

    @Override
    public Product getById(int productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        return product;
    }
}

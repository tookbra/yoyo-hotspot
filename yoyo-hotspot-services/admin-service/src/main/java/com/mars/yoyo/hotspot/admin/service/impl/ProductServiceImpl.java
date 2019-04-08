package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.ProductMapper;
import com.mars.yoyo.hotspot.admin.entity.Product;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.ProductParameter;
import com.mars.yoyo.hotspot.admin.resutls.ProductView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.ProductService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品套餐
 *
 * @author admin
 * @create 2018/5/21
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class, readOnly = false)
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ListResultEx<ProductView> getProductList(CommonParameter parameter) {
        ListResultEx<ProductView> resultEx = new ListResultEx<>();

        // 设置参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(Product.class);
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("productName", "%" + parameter.getSearchText() + "%");
        }
        List<Product> productList = productMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = productMapper.selectCountByExample(example);

        // 设置结果参数
        List<ProductView> productViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(productList)) {
            ProductView productView;
            for (Product product : productList) {
                productView = new ProductView();
                BeanUtils.copyProperties(product, productView);

                productViewList.add(productView);
            }
        }

        resultEx.setDataList(productViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult addProduct(ProductParameter parameter) {
        if (null == parameter.getProductType()) {
            return RestResult.error("产品套餐类型未选择");
        }

        Product product = new Product();
        BeanUtils.copyProperties(parameter, product);
        product.setPrice(new BigDecimal(parameter.getPrice()));
        product.setCapPrice(new BigDecimal(parameter.getCapPrice()));
        if (parameter.getLimited() != null) {
            product.setLimited(Integer.parseInt(parameter.getLimited()));
        }
        if (parameter.getLimitNum() != null) {
            product.setLimitNum(Integer.parseInt(parameter.getLimitNum()));
        }

        Date now = new Date();
        product.setCreateTime(now);
        product.setModifyTime(now);

        productMapper.insertSelective(product);

        return RestResult.success("新增产品套餐完成");
    }

    @Override
    public RestResult deleteProduct(Integer productId) {
        if (null == productId) {
            return RestResult.error("参数不能为空");
        }
        productMapper.deleteByPrimaryKey(productId);

        return RestResult.success("删除产品套餐完成");
    }

    @Override
    public RestResult updateProduct(ProductParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(parameter.getId());
        if (null == product) {
            return RestResult.error("产品套餐不存在");
        }
        BeanUtils.copyProperties(parameter, product);
        product.setPrice(new BigDecimal(parameter.getPrice().replace(",", "")));
        product.setCapPrice(new BigDecimal(parameter.getCapPrice().replace(",", "")));
        if (parameter.getLimited() != null) {
            product.setLimited(Integer.parseInt(parameter.getLimited().replace(",", "")));
        }
        if (parameter.getLimitNum() != null) {
            product.setLimitNum(Integer.parseInt(parameter.getLimitNum().replace(",", "")));
        }

        product.setModifyTime(new Date());

        productMapper.updateByPrimaryKeySelective(product);

        return RestResult.success("产品套餐更新完成");
    }

    @Override
    public RestResult getProductById(Integer productId) {
        if (null == productId) {
            return RestResult.error("参数不能为空");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (null == product) {
            return RestResult.error("产品套餐不存在");
        }
        ProductView productView = new ProductView();
        BeanUtils.copyProperties(product, productView);

        return RestResult.success(productView);
    }

}

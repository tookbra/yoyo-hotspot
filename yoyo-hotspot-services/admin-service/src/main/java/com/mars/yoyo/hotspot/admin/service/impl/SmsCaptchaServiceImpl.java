package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.SmsCaptchaMapper;
import com.mars.yoyo.hotspot.admin.entity.SmsCaptcha;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsCaptchaView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.SmsCaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 短信验证码
 *
 * @author admin
 * @create 2018/6/6
 */
@Slf4j
@Service
public class SmsCaptchaServiceImpl implements SmsCaptchaService {

    @Autowired
    private SmsCaptchaMapper smsCaptchaMapper;

    @Override
    public ListResultEx<SmsCaptchaView> getSmsCaptchaList(CommonParameter parameter) {
        ListResultEx<SmsCaptchaView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        // 设置查询参数
        Example example = new Example(SmsCaptcha.class);
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("phone", "%" + parameter.getSearchText() + "%");
        }
        List<SmsCaptcha> captchas = smsCaptchaMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = smsCaptchaMapper.selectCountByExample(example);

        // 设置返回参数
        List<SmsCaptchaView> captchaViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(captchas)) {
            SmsCaptchaView captchaView;
            for (SmsCaptcha captcha : captchas) {
                captchaView = new SmsCaptchaView();
                BeanUtils.copyProperties(captcha, captchaView);

                captchaViewList.add(captchaView);
            }
        }
        resultEx.setDataList(captchaViewList);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

}

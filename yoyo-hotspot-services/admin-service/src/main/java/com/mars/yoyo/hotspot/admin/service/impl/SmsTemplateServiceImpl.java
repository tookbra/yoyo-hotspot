package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.SmsTemplateMapper;
import com.mars.yoyo.hotspot.admin.entity.SmsTemplate;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.params.SmsTemplateParameter;
import com.mars.yoyo.hotspot.admin.resutls.SmsTemplateView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.SmsTemplateService;
import com.mars.yoyo.hotspot.result.RestResult;
import com.mars.yoyo.hotspot.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 短信模版
 *
 * @author admin
 * @create 2018/5/23
 */
@Slf4j
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

    @Autowired
    private SmsTemplateMapper smsTemplateMapper;

    @Override
    public ListResultEx<SmsTemplateView> getSmsTemplateList(CommonParameter parameter) {
        ListResultEx<SmsTemplateView> resultEx = new ListResultEx<>();

        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(SmsTemplate.class);
        example.setOrderByClause(" create_time desc ");
        if (!StringUtils.isEmpty(parameter.getSearchText())) {
             example.createCriteria().andLike("name", "%" + parameter.getSearchText() + "%")
                                     .orLike("code", "%" + parameter.getSearchText() + "%")
                                     .orLike("content", "%" + parameter.getSearchText() + "%");
        }
        List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = smsTemplateMapper.selectCountByExample(example);

        // 转换为返回结果集
        List<SmsTemplateView> smsTemplateViewList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(smsTemplateList)) {
            SmsTemplateView smsTemplateView;
            for (SmsTemplate smsTemplate : smsTemplateList) {
                smsTemplateView = new SmsTemplateView();
                BeanUtils.copyProperties(smsTemplate, smsTemplateView);
                smsTemplateViewList.add(smsTemplateView);
            }
        }

        resultEx.setDataList(smsTemplateViewList);
        resultEx.setTotalCount(total);
        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult addSmsTemplate(SmsTemplateParameter parameter) {
        // 参数判断
        if (StringUtil.isBlank(parameter.getName())) {
            return RestResult.error("参数不能为空");
        }
        SmsTemplate smsTemplate = new SmsTemplate();
        BeanUtils.copyProperties(parameter, smsTemplate);
        Date now = new Date();
        smsTemplate.setCreateTime(now);
        smsTemplate.setModifyTime(now);

        smsTemplateMapper.insertSelective(smsTemplate);

        return RestResult.success("添加短信模版成功");
    }

    @Override
    public RestResult deleteSmsTemplate(Integer templateId) {
        if (null == templateId) {
            return RestResult.error("参数不能为空");
        }
        smsTemplateMapper.deleteByPrimaryKey(templateId);

        return RestResult.success("删除短信模版成功");
    }

    @Override
    public RestResult updateSmsTemplate(SmsTemplateParameter parameter) {
        if (null == parameter.getId()) {
            return RestResult.error("参数不能为空");
        }
        SmsTemplate smsTemplate = smsTemplateMapper.selectByPrimaryKey(parameter.getId());
        if (null == smsTemplate) {
            return RestResult.error("短信模版不存在");
        }
        BeanUtils.copyProperties(parameter, smsTemplate);
        smsTemplate.setModifyTime(new Date());

        smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);

        return RestResult.success("更新短信模版成功");
    }

    @Override
    public RestResult getSmsTemplateById(Integer templateId) {
        if (null == templateId) {
            return RestResult.error("参数不能为空");
        }
        SmsTemplate smsTemplate = smsTemplateMapper.selectByPrimaryKey(templateId);
        if (null == smsTemplate) {
            return RestResult.error("短信模版不存在");
        }
        SmsTemplateView smsTemplateView = new SmsTemplateView();
        BeanUtils.copyProperties(smsTemplate, smsTemplateView);

        return RestResult.success(smsTemplateView);
    }

}

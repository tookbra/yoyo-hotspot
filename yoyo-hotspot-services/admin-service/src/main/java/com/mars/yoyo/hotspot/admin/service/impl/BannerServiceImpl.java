package com.mars.yoyo.hotspot.admin.service.impl;

import com.mars.yoyo.hotspot.admin.dao.BannerMapper;
import com.mars.yoyo.hotspot.admin.entity.Banner;
import com.mars.yoyo.hotspot.admin.params.BannerParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.BannerView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.BannerService;
import com.mars.yoyo.hotspot.result.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 广告接口实现
 *
 * @author admin
 * @Date 2018/9/4 13:26
 */
@Slf4j
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public ListResultEx<BannerView> listBannerView(CommonParameter parameter) {
        ListResultEx<BannerView> resultEx = new ListResultEx<>();
        // 设置查询参数
        RowBounds rowBounds = new RowBounds(parameter.getStart(), parameter.getPageSize());
        Example example = new Example(Banner.class);
        example.setOrderByClause("create_time desc");
        if (StringUtils.isNotBlank(parameter.getSearchText())) {
            example.createCriteria().andLike("name", "%" + parameter.getSearchText() + "%");
        }
        List<Banner> banners = bannerMapper.selectByExampleAndRowBounds(example, rowBounds);
        Integer total = bannerMapper.selectCountByExample(example);

        // 设置返回结果
        List<BannerView> bannerViewList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(banners)) {
            BannerView bannerView;
            for (Banner banner : banners) {
                bannerView = new BannerView();
                BeanUtils.copyProperties(banner, bannerView);
                bannerViewList.add(bannerView);
            }
        }
        resultEx.setDataList(bannerViewList);
        resultEx.setTotalCount(total);

        return resultEx.makeSuccessResult();
    }

    @Override
    public RestResult saveBanner(BannerParameter parameter) {
        // 判断参数
        if (StringUtils.isBlank(parameter.getName())) {
            return RestResult.error("广告标题不能为空");
        }
        if (StringUtils.isBlank(parameter.getImgUrl())) {
            return RestResult.error("图片地址不能为空");
        }
        if (StringUtils.isBlank(parameter.getVideoUrl())) {
            return RestResult.error("视频地址不能为空");
        }
        if (parameter.getState() == null) {
            return RestResult.error("是否启用状态不能为空");
        }

        Banner banner = new Banner();
        BeanUtils.copyProperties(parameter, banner);
        Date now = new Date();
        banner.setCreateTime(now);
        banner.setMofidyTime(now);
        bannerMapper.insertSelective(banner);

        return RestResult.success("添加完成");
    }

    @Override
    public RestResult updateBanner(BannerParameter parameter) {
        // 判断参数
        if (parameter.getId() == null) {
            return  RestResult.error("参数不能为空");
        }
        if (StringUtils.isBlank(parameter.getName())) {
            return RestResult.error("广告标题不能为空");
        }
        if (StringUtils.isBlank(parameter.getImgUrl())) {
            return RestResult.error("图片地址不能为空");
        }
        if (StringUtils.isBlank(parameter.getVideoUrl())) {
            return RestResult.error("视频地址不能为空");
        }
        if (parameter.getState() == null) {
            return RestResult.error("是否启用状态不能为空");
        }

        Banner banner = bannerMapper.selectByPrimaryKey(parameter.getId());
        if (banner == null) {
            banner = new Banner();
            BeanUtils.copyProperties(parameter, banner);
            Date now = new Date();
            banner.setCreateTime(now);
            banner.setMofidyTime(now);
            bannerMapper.insertSelective(banner);
        } else {
            BeanUtils.copyProperties(parameter, banner);
            banner.setMofidyTime(new Date());
            bannerMapper.updateByPrimaryKey(banner);
        }

        return RestResult.success("修改完成");
    }

    @Override
    public RestResult deleteBanner(Integer bannerId) {
        bannerMapper.deleteByPrimaryKey(bannerId);
        return RestResult.success("删除完成");
    }

    @Override
    public RestResult getBannerInfo(Integer bannerId) {
        Banner banner = bannerMapper.selectByPrimaryKey(bannerId);
        if (banner == null) {
            return RestResult.error("当前广告信息不存在");
        }
        BannerView bannerView = new BannerView();
        BeanUtils.copyProperties(banner, bannerView);

        return RestResult.success(bannerView);
    }
}

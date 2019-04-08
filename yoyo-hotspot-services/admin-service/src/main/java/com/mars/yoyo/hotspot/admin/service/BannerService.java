package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.BannerParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.BannerView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 广告
 *
 * @author admin
 * @Date 2018/9/4 13:19
 */
public interface BannerService {

    /**
     * 查询列表
     * @param parameter
     * @return
     */
    ListResultEx<BannerView> listBannerView(CommonParameter parameter);

    /**
     * 添加广告
     * @param parameter
     * @return
     */
    RestResult saveBanner(BannerParameter parameter);

    /**
     * 修改广告
     * @param parameter
     * @return
     */
    RestResult updateBanner(BannerParameter parameter);

    /**
     * 删除广告
     * @param bannerId
     * @return
     */
    RestResult deleteBanner(Integer bannerId);

    /**
     * 查询广告
     * @param bannerId
     * @return
     */
    RestResult getBannerInfo(Integer bannerId);


}

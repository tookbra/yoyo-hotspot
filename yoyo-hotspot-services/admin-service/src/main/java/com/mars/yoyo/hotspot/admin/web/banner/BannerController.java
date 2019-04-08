package com.mars.yoyo.hotspot.admin.web.banner;

import com.mars.yoyo.hotspot.admin.params.BannerParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.BannerView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.admin.service.BannerService;
import com.mars.yoyo.hotspot.result.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 广告
 *
 * @author admin
 * @Date 2018/9/4 9:13
 */
@Controller
@RequestMapping("/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/list")
    public String list(){
        return "banner/list";
    }

    /**
     * 分页查询
     * @return
     */
    @ResponseBody
    @GetMapping("/listBanners")
    public ListResultEx<BannerView> listDevices(CommonParameter parameter) {
        return bannerService.listBannerView(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preAdd")
    public String preAdd(){
        return "banner/add";
    }

    /**
     * 添加设备
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public RestResult saveBanner(BannerParameter parameter) {
        return bannerService.saveBanner(parameter);
    }

    /**
     * 列表跳转页面
     * @return
     */
    @GetMapping("/preEdit/{bannerId}")
    public String preEdit(ModelMap modelMap, @PathVariable Integer bannerId){
        RestResult result = bannerService.getBannerInfo(bannerId);
        if (null == result.getData()) {
            return result.getMsg();
        }
        modelMap.addAttribute("banner", result.getData());

        return "banner/edit";
    }

    /**
     * 更新设备信息
     * @param parameter
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public RestResult updateDevice(BannerParameter parameter) {
        return bannerService.updateBanner(parameter);
    }

    /**
     * 删除设备信息
     * @param bannerId
     * @return
     */
    @ResponseBody
    @PostMapping("/delete/{bannerId}")
    public RestResult deleteDevice(@PathVariable Integer bannerId) {
        return bannerService.deleteBanner(bannerId);
    }

}

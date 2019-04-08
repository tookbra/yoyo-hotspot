package com.mars.yoyo.hotspot.admin.service;

import com.mars.yoyo.hotspot.admin.params.AdminUserParameter;
import com.mars.yoyo.hotspot.admin.params.CommonParameter;
import com.mars.yoyo.hotspot.admin.resutls.AdminView;
import com.mars.yoyo.hotspot.admin.resutls.base.ListResultEx;
import com.mars.yoyo.hotspot.result.RestResult;

/**
 * 管理人员
 *
 * @author admin
 * @create 2018/5/8
 * @since 1.0.0
 */
public interface AdminService {


    /**
     * 分页查询管理员
     * @return
     */
    ListResultEx<AdminView> getAdminList(CommonParameter parameter);

    /**
     * 新增管理员
     * @param  parameter
     * @return
     */
    RestResult addAdminUser(AdminUserParameter parameter);

    /**
     * 删除管理员
     * @param adminId
     * @return
     */
    RestResult deleteAdmin(Integer adminId);

    /**
     * 修改管理员
     * @param parameter
     * @return
     */
    RestResult updateAdmin(AdminUserParameter parameter);

    /**
     * 查询单个信息
     * @param adminId
     * @return
     */
    RestResult<AdminView> getAdminViewById(Integer adminId);

}

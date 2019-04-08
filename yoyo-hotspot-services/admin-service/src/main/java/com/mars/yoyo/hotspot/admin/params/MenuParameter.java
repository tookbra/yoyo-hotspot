package com.mars.yoyo.hotspot.admin.params;

import lombok.Data;

/**
 * 菜单
 *
 * @author admin
 * @create 2018/5/20
 */
@Data
public class MenuParameter extends SessionParameter {

    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 菜单等级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 菜单URL
     */
    private String href;

}

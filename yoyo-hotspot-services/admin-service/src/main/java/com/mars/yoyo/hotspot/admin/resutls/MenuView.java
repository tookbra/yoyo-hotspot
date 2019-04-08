package com.mars.yoyo.hotspot.admin.resutls;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单
 *
 * @author admin
 * @create 2018/5/20
 */
@Data
public class MenuView implements Serializable {

    private static final long serialVersionUID = 2646989364243321743L;

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
     * 父菜单名称
     */
    private String parentTitle;

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

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 角色中菜单是否勾选
     */
    private boolean check = false;

    /**
     * 子菜单
     */
    private List<MenuView> childs = new ArrayList<>();

}

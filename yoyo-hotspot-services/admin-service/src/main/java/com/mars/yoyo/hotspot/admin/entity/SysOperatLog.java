package com.mars.yoyo.hotspot.admin.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_operat_log")
public class SysOperatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单名称
     */
    private String menu;

    /**
     * 操作
     */
    private String option;

    /**
     * 操作路径
     */
    private String uri;

    /**
     * 操作用户
     */
    @Column(name = "operator_id")
    private Integer operatorId;

    /**
     * 操作人用户id
     */
    @Column(name = "operator_ip")
    private String operatorIp;

    /**
     * 操作时间
     */
    @Column(name = "operater_time")
    private Date operaterTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取菜单名称
     *
     * @return menu - 菜单名称
     */
    public String getMenu() {
        return menu;
    }

    /**
     * 设置菜单名称
     *
     * @param menu 菜单名称
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }

    /**
     * 获取操作
     *
     * @return option - 操作
     */
    public String getOption() {
        return option;
    }

    /**
     * 设置操作
     *
     * @param option 操作
     */
    public void setOption(String option) {
        this.option = option;
    }

    /**
     * 获取操作路径
     *
     * @return uri - 操作路径
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置操作路径
     *
     * @param uri 操作路径
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * 获取操作用户
     *
     * @return operator_id - 操作用户
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * 设置操作用户
     *
     * @param operatorId 操作用户
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 获取操作人用户id
     *
     * @return operator_ip - 操作人用户id
     */
    public String getOperatorIp() {
        return operatorIp;
    }

    /**
     * 设置操作人用户id
     *
     * @param operatorIp 操作人用户id
     */
    public void setOperatorIp(String operatorIp) {
        this.operatorIp = operatorIp;
    }

    /**
     * 获取操作时间
     *
     * @return operater_time - 操作时间
     */
    public Date getOperaterTime() {
        return operaterTime;
    }

    /**
     * 设置操作时间
     *
     * @param operaterTime 操作时间
     */
    public void setOperaterTime(Date operaterTime) {
        this.operaterTime = operaterTime;
    }
}
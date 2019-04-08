package com.mars.yoyo.hotspot.admin.params;

/**
 * 短信模版参数
 *
 * @author admin
 * @create 2018/5/23
 */
public class SmsTemplateParameter extends SessionParameter {

    private Integer id;

    private String name;

    private String code;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 是否启用 1是 0 否
     */
    private Integer state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

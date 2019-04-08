package com.mars.yoyo.hotspot.mifi.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/10/28
 * @description
 */
@Data
@ToString
public class DeviceFlow implements Serializable {

    private static final long serialVersionUID = -5398050757140435062L;

    /**
     * 设备IMEI，string，必填
     */
    private String imei;
    /**
     * 请求流水号，string，必填，尽量唯一，可使用uuid。
     */
    private String transId;
    /**
     * 授权码，string，必填。
     */
    private String authToken;
    /**
     * 卡模式，string，可选；值p表示物理卡，值v表示虚拟卡，空值忽略，其他值报错
     */
    private String cardMode = "";
    /**
     * 虚拟卡运营商，string，可选；值ctcc表示中电信，空值忽略，其他值报错
     */
    private String vCarrier = "";
    /**
     * 限速比例，注意不是速度，int，可选；取值[-1, 1000]，-1表示取消限速，0忽略，大于0则为限速，一般设置在(0, 200]以内
     */
    private int netRate;
    /**
     * 达量通知量值，int，可选, 单位是MB； 取值[-1, 1048576], -1表示取消达量通知，其他有效值生效
     */
    private int usageLimit;
    /**
     * 更改密码，string，可选; 空值忽略，尽量在前端拦截特殊字符
     */
    private String wifiPwd;
    /**
     * 更改SSID, string, 可选；空值忽略，尽量在前端拦截特殊字符和中文; 可以和wifi_pwd一起传过来
     */
    private String wifiSsid ="mefi";
    /**
     * 操作wifi, string，可选；值enable表示启用，值disable表示禁用，值reboot表示重启，其他值忽略
     */
    private String wifiCtl = "enable";
    /**
     * 操作系统，string，可选；值halt表示关机，值reboot表示重启，其他值忽略
     */
    private String sysCtl = "";

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(11);
        map.put("auth_token", this.getAuthToken());
        map.put("trans_id", String.valueOf(System.currentTimeMillis()));
        map.put("imei", this.getImei());
        map.put("wifi_pwd", this.getWifiPwd());
        return map;
    }
}

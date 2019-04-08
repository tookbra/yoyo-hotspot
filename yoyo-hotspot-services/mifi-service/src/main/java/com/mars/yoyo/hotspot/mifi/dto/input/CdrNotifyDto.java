package com.mars.yoyo.hotspot.mifi.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author tookbra
 * @date 2018/9/11
 * @description
 */
@Data
@ToString
public class CdrNotifyDto implements Serializable {
    private static final long serialVersionUID = 3449088239167119487L;

    private String method;

    @JsonProperty("trans_id")
    private String transId;

    private String imei;

    private Date timestamp;

    @JsonProperty("cdr_records")
    private List<CdrRecord> recordList;

    @Data
    @ToString
    public class CdrRecord {
        /**
         * 对于1台设备而言话单有个唯一的key，key一般是递增的
         */
        private String key;
        /**
         * 开始和结束unix时间戳
         * 单位是毫秒
         */
        @JsonProperty("start_time")
        private long startTime;
        /**
         * 开始和结束unix时间戳
         * 单位是毫秒
         */
        @JsonProperty("end_time")
        private long endTime;
        /**
         * 在这段时间内的用量
         * 单位是Byte
         */
        @JsonProperty("data_usage")
        private int dataUsage;
    }
}

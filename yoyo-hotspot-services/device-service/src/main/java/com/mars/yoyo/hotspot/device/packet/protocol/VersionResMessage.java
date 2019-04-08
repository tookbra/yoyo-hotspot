package com.mars.yoyo.hotspot.device.packet.protocol;

import com.mars.yoyo.hotspot.device.packet.Header;
import com.mars.yoyo.hotspot.device.packet.Message;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 查询机柜软件版本号
 * @author tookbra
 * @date 2018/4/4
 * @description
 */
@Data
@NoArgsConstructor
@ToString
public class VersionResMessage extends Message {

    private static final long serialVersionUID = -4221782301578418464L;

    /**
     * 机柜软件版本号字段长度
     */
    private int softVerLen;
    /**
     * 机柜软件版本号
     */
    private String softVer;

    public VersionResMessage(Header header) {
        super(header);
    }

}

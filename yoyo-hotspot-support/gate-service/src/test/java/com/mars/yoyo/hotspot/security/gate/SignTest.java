package com.mars.yoyo.hotspot.security.gate;

import com.mars.yoyo.hotspot.security.gate.util.SignUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tookbra
 * @date 2018/5/26
 * @description
 */
public class SignTest {

    @Test
    public void signTest() {
//        String token = "123";
//        long time = System.currentTimeMillis();
//        String timestamp = String.valueOf(time);
//        String nonce = String.valueOf(timestamp.hashCode());
//        String url = "/api/user/login";
//        Map<String, String[]> map = new HashMap<>(16);
//        map.put("username",new String[]{"test"});
//        map.put("password",new String[]{"test"});
//        System.out.println(SignUtil.createSign(map, token, timestamp, nonce, url));
        System.out.println(DigestUtils.md5Hex("eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNTkwNjY5NTcyNiIsInVzZXJJZCI6IjI4IiwibmFtZSI6IiIsImV4cCI6MTUzMzE4MTcyOX0.GAGD3QhK918m4-PwZhMIg1ey2XejmIFd8oNfOwoMpVfBWdiPArNYRV64u8jgX-UPXQCh38jOcANz_gCIT6Pa4fq5SGYtdD4tx2Fa8RpSIG1AggpuSX6fOHYUx8nUjJcCrLNsyAnRcxtAIKmS-Y7D90Bd5mFF0KgJVCmMjO6PevQ153059706058610ZPBRKE/api/mifi/rents/current"));
    }
}

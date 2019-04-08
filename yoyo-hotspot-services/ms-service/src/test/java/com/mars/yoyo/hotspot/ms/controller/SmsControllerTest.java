package com.mars.yoyo.hotspot.ms.controller;

import com.mars.yoyo.hotspot.ms.BaseTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author tookbra
 * @date 2018/5/29
 * @description
 */
public class SmsControllerTest extends BaseTest {


    /**
     * 发送短信
     * @throws Exception
     */
    @Test
    public void sendSms() throws Exception {
        RequestBuilder request = post("/sms")
                .param("phones", "15906695726")
                .param("type", "REG");
        super.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andDo(print());
    }
}

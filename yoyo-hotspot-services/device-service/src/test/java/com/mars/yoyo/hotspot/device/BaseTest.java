package com.mars.yoyo.hotspot.device;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author tookbra
 * @date 2018/4/9
 * @description
 */
@SpringBootTest(classes = DeviceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseTest {

    private MockMvc mvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        //  初始化MockMvc对象
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // 初始化Mock
        MockitoAnnotations.initMocks(this);
    }
}

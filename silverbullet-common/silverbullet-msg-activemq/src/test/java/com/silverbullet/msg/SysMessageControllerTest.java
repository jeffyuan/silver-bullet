package com.silverbullet.msg;

import com.silverbullet.TestApplication;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 消息测试类
 * Created by jeffyuan on 2018/11/21.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@WebAppConfiguration
public class SysMessageControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();//建议使用这种
    }

    @Test
    public void testSendMessage() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/sysmessage/queue/queue-message")
                //.contentType(MediaType.APPLICATION_JSON_UTF8)
                //.param("lat", "123.123").param("lon", "456.456")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
                //.andDo(MockMvcResultHandlers.print())
                //.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));

        mvc.perform(MockMvcRequestBuilders.get("/sysmessage/topic/topic-message")
                //.contentType(MediaType.APPLICATION_JSON_UTF8)
                //.param("lat", "123.123").param("lon", "456.456")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        //.andDo(MockMvcResultHandlers.print())
        //.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("SUCCESS")));
    }
}

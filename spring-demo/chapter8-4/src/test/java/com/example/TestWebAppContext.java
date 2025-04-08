package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

// @SpringBootTest が DI コンテナの設定までやってくれている
@SpringBootTest
public class TestWebAppContext {
  @Autowired WebApplicationContext context;

  MockMvc mockMvc;

  @BeforeEach
  void setupMockMvc() {
    mockMvc =
        MockMvcBuilders.webAppContextSetup(context)
            .apply(SharedHttpSessionConfigurer.sharedHttpSession()) // セッションが保持される
            .build();
  }

  @Test
  void testLoginInvalid() throws Exception {
    // リクエストパラメータが必要
    mockMvc
        .perform(MockMvcRequestBuilders.get("/login"))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void testLoginValid() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/login?userId=mister&password=1234"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void testAccessNoAuth() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/login?userId=mister&password=1234"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print());
    mockMvc
        .perform(MockMvcRequestBuilders.get("/access"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Hello, mister! (Password: 1234)"))
        .andDo(MockMvcResultHandlers.print());
  }
}

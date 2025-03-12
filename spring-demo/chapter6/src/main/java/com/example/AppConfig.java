package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class AppConfig {
  // Jackson の設定
  // Spring MVC は Jackson を組み込みでサポートしているため、追加のライブラリは必要ない
  @Bean
  ObjectMapper objectMapper() {
    return Jackson2ObjectMapperBuilder.json()
        .indentOutput(true)
        .dateFormat(new StdDateFormat()) // リソースクラスのフィールドに @JsonFormat をつけてもいい
        .build();
  }
}

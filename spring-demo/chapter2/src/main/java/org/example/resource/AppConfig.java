package org.example.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

public class AppConfig {
  @Bean
  public Greeting greeting(@Value("classpath:greeting.txt") Resource resource) {
    var greeting = new Greeting();
    greeting.resource = resource;
    return greeting;
  }
}

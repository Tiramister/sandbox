package org.example.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public Person person(@Value("#{ T(java.lang.Math).random() * 100 }") int age) {
    Person person = new Person();
    person.age = age;
    return person;
  }
}

package org.example.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("org.example.scope")
public class AppConfig {
    @Bean
    public UUIDBean singletonUUIDBean() {
        return new UUIDBean();
    }

    @Bean
    @Scope("prototype")
    public UUIDBean prototypeUUIDBean() {
        return new UUIDBean();
    }

    @Bean
    public UserServiceNaive userServiceNaive() {
        return new UserServiceNaive(prototypeUUIDBean());
    }
}

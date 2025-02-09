package org.example.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:property-demo.properties")
public class AppConfig {
  @Bean
  public DataSource dataSource(
      // properties ファイルから値を取得
      @Value("${datasource.url}") String url,
      @Value("${datasource.user:dev-user}") String user,
      @Value("${datasource.pass:pass}") String pass,
      @Value("${datasource.env:dev}") String env) {

    return new DataSource(url, user, pass, env);
  }
}

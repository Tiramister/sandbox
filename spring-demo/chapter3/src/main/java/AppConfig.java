import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
  @Bean
  public DataSource dataSource(
      @Value("${datasource.postgres.driver-class-name}") String postgresDriverClassName,
      @Value("${datasource.postgres.url}") String postgresUrl,
      @Value("${datasource.mysql.driver-class-name}") String mysqlDriverClassName,
      @Value("${datasource.mysql.url}") String mysqlUrl,
      @Value("${datasource.username}") String username,
      @Value("${datasource.password}") String password,
      @Value("${datasource.engine}") String engine) {
    var dataSource = new BasicDataSource();
    switch (engine) {
      case "postgres" -> {
        dataSource.setDriverClassName(postgresDriverClassName);
        dataSource.setUrl(postgresUrl);
      }
      case "mysql" -> {
        dataSource.setDriverClassName(mysqlDriverClassName);
        dataSource.setUrl(mysqlUrl);
      }
      default -> throw new IllegalArgumentException("Unknown engine: " + engine);
    }
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}

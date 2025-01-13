package property;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.property.AppConfig;
import org.example.property.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAll {
  @Test
  void loadDataSource() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);

    var dataSource = context.getBean(DataSource.class);
    assertEquals("jdbc:postgresql://localhost:5432/demo", dataSource.url());
    assertEquals("demo", dataSource.user());
    assertEquals("pass1234", dataSource.pass());
    // デフォルト値が設定されている
    assertEquals("dev", dataSource.env());
  }
}

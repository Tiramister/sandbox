import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

class HelloTest {
  @Test
  void testSayHello() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    var jdbcTemplate = context.getBean(JdbcTemplate.class);

    var rows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM items", Integer.class);
    System.out.println(rows);
  }
}

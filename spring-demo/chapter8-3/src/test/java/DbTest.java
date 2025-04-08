import com.example.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = AppConfig.class) // @ExtendWith と @ContextConfiguration を合わせたやつ
@TestPropertySource(locations = "classpath:test.properties")
@Sql("classpath:sql/prepare.sql") // 各テストの実行前に実行される
public class DbTest {
  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  void testInsert() {
    jdbcTemplate.update("INSERT INTO items (id, name) VALUES (4, 'fourth'), (5, 'fifth')");
    Integer count = jdbcTemplate.queryForObject("select count(*) from items", Integer.class);
    Assertions.assertEquals(5, count);
  }

  @Test
  void testSelect() {
    // 直前に prepare.sql が実行されるので、前のテストの影響を受けない
    Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM items", Integer.class);
    Assertions.assertEquals(3, count);
  }

  @Test
  @Sql("classpath:sql/delete.sql")
  void testDelete() {
    // 直前に prepare.sql は実行されず、代わりに delete.sql が実行される
    Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM items", Integer.class);
    Assertions.assertEquals(0, count);
  }
}

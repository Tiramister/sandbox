import javax.sql.DataSource;
import com.example.AppConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = AppConfig.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // テストの順番を指定する
// 今回はクラスレベルで @Sql を指定していない点に注意
public class TransactionTest {
  @Autowired JdbcTemplate jdbcTemplate;

  // DB の初期化
  @BeforeAll
  static void setUp(@Autowired DataSource dataSource // static なのでフィールドインジェクションできない
      ) {
    var populator = new ResourceDatabasePopulator();
    populator.addScript(new ClassPathResource("sql/prepare.sql"));
    populator.execute(dataSource);
  }

  @Test
  @Order(1)
  @Transactional
  void testSelect1() {
    Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM items", Integer.class);
    Assertions.assertEquals(3, count);
  }

  @Test
  @Order(2)
  @Transactional // デフォルトではテスト終了時にロールバックされる
  void testInsertRollback() {
    jdbcTemplate.update("INSERT INTO items (id, name) VALUES (4, 'fourth'), (5, 'fifth')");
    Integer count = jdbcTemplate.queryForObject("select count(*) from items", Integer.class);
    Assertions.assertEquals(5, count);
  }

  @Test
  @Order(3)
  @Transactional
  void testSelect2() {
    // 前のテストはロールバックされたので影響を受けない
    Integer count = jdbcTemplate.queryForObject("SELECT count(*) FROM items", Integer.class);
    Assertions.assertEquals(3, count);
  }
}

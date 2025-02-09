import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringJUnitConfig(AppConfig.class)
public class IsolationLevelTest {
  @Autowired PlatformTransactionManager txManager;

  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  void test() {
    // ここを書き換える
    int isolationLevel = TransactionDefinition.ISOLATION_DEFAULT;

    try {
      var readerThread = new Thread(() -> run(isolationLevel, "ReaderTx", this::read));
      var writerThread = new Thread(() -> run(isolationLevel, "WriterTx", this::write));
      var revertThread = new Thread(() -> run(isolationLevel, "RevertTx", this::revert));

      readerThread.start();
      writerThread.start();
      readerThread.join();
      writerThread.join();

      revertThread.start();
      revertThread.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  void run(int isolationLevel, String threadName, Consumer<TransactionStatus> runnable) {
    var def = new DefaultTransactionDefinition();
    def.setIsolationLevel(isolationLevel);
    def.setName(threadName);

    TransactionStatus status = txManager.getTransaction(def);
    log("START", status);
    runnable.accept(status);
    txManager.commit(status);
    log("COMMIT", status);
  }

  void read(TransactionStatus status) {
    {
      List<Map<String, Object>> item =
          jdbcTemplate.queryForList("SELECT * FROM items WHERE id = 1");
      log("SELECT item 1: " + item, status);
    }
    {
      List<Map<String, Object>> item =
          jdbcTemplate.queryForList("SELECT * FROM items WHERE id = 999");
      log("SELECT item 999: " + item, status);
    }

    sleep(Duration.ofSeconds(10));
    // 更新済み、コミット前
    {
      List<Map<String, Object>> item =
          jdbcTemplate.queryForList("SELECT * FROM items WHERE id = 1");
      log("SELECT item 1: " + item, status);
    }
    {
      List<Map<String, Object>> item =
          jdbcTemplate.queryForList("SELECT * FROM items WHERE id = 999");
      log("SELECT item 999: " + item, status);
    }

    sleep(Duration.ofSeconds(10));
    // 更新済み、コミット済み
    {
      List<Map<String, Object>> item =
          jdbcTemplate.queryForList("SELECT * FROM items WHERE id = 1");
      log("SELECT item 1: " + item, status);
    }
    {
      List<Map<String, Object>> item =
          jdbcTemplate.queryForList("SELECT * FROM items WHERE id = 999");
      log("SELECT item 999: " + item, status);
    }
  }

  void write(TransactionStatus status) {
    sleep(Duration.ofSeconds(5));
    {
      jdbcTemplate.update("UPDATE items SET name = 'UPDATED' WHERE id = 1");
      log("UPDATE item 1", status);
    }
    {
      jdbcTemplate.update("INSERT INTO items (id, name) VALUES (999, 'INSERTED')");
      log("INSERT item 999", status);
    }
    sleep(Duration.ofSeconds(10));
  }

  void revert(TransactionStatus status) {
    {
      jdbcTemplate.update("UPDATE items SET name = 'first' WHERE id = 1");
      log("REVERT item 1", status);
    }
    {
      jdbcTemplate.update("DELETE FROM items WHERE id = 999");
      log("DELETE item 999", status);
    }
  }

  void log(String message, TransactionStatus status) {
    System.out.printf("[%s] %s%n", status.getTransactionName(), message);
  }

  void sleep(Duration duration) {
    try {
      Thread.sleep(duration.toMillis());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}

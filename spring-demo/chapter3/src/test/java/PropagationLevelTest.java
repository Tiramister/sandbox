import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringJUnitConfig(AppConfig.class)
public class PropagationLevelTest {
  @Autowired PlatformTransactionManager txManager;

  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  void test() {
    try {
      // ここを書き換える
      run(TransactionDefinition.PROPAGATION_REQUIRED, true, false);
    } finally {
      // 最終結果を出力
      List<Map<String, Object>> items = jdbcTemplate.queryForList("SELECT * FROM items");
      System.out.println("items: ");
      System.out.println(items.stream().map(Object::toString).collect(Collectors.joining("\n")));

      // 作ったデータを削除
      jdbcTemplate.update("DELETE FROM items WHERE id >= 100");
    }
  }

  @Transactional(propagation = Propagation.REQUIRED)
  void run(int propagationLevel, boolean innerError, boolean outerError) {
    // 外側は関係ないので REQUIRED で固定
    var outerStatus =
        txManager.getTransaction(
            getDef(DefaultTransactionDefinition.PROPAGATION_REQUIRED, "OuterTx"));
    log("START", outerStatus);

    jdbcTemplate.update("INSERT INTO items (id, name) VALUES (101, 'OUTER BEFORE')");
    log("INSERT item 101", outerStatus);

    {
      var innerStatus = txManager.getTransaction(getDef(propagationLevel, "InnerTx"));
      log("START", innerStatus);

      jdbcTemplate.update("INSERT INTO items (id, name) VALUES (201, 'INNER BEFORE')");
      log("INSERT item 201", innerStatus);

      if (innerError) rollback(innerStatus);
      commit(innerStatus);
    }

    jdbcTemplate.update("INSERT INTO items (id, name) VALUES (109, 'OUTER BEFORE')");
    log("INSERT item 109", outerStatus);

    if (outerError) rollback(outerStatus);
    commit(outerStatus);
  }

  TransactionDefinition getDef(int propagationLevel, String txName) {
    var def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(propagationLevel);
    def.setName(txName);
    return def;
  }

  void commit(TransactionStatus status) {
    if (status.isCompleted()) {
      log("SKIP COMMIT (COMPLETED)", status);
    } else if (status.isRollbackOnly()) {
      log("SKIP COMMIT (ROLLBACK ONLY)", status);
    } else {
      txManager.commit(status);
      log("COMMIT", status);
    }
  }

  void rollback(TransactionStatus status) {
    if (status.isCompleted()) {
      log("SKIP ROLLBACK (COMPLETED)", status);
    } else if (status.isRollbackOnly()) {
      log("SKIP ROLLBACK (ROLLBACK ONLY)", status);
    } else {
      txManager.rollback(status);
      log("ROLLBACK", status);
    }
  }

  void log(String message, TransactionStatus status) {
    System.out.printf("[%s] %s%n", status.getTransactionName(), message);
  }
}

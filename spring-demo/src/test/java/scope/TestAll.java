package scope;

import static org.junit.jupiter.api.Assertions.*;

import org.example.scope.AppConfig;
import org.example.scope.UUIDBean;
import org.example.scope.UserServiceLookup;
import org.example.scope.UserServiceNaive;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestAll {
  @Test
  void singleton() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    var bean1 = context.getBean("singletonUUIDBean", UUIDBean.class);
    var bean2 = context.getBean("singletonUUIDBean", UUIDBean.class);
    System.out.println(bean1.getUuid());
    System.out.println(bean2.getUuid());
    assertEquals(bean1.getUuid(), bean2.getUuid());
  }

  @Test
  void prototype() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    var bean1 = context.getBean("prototypeUUIDBean", UUIDBean.class);
    var bean2 = context.getBean("prototypeUUIDBean", UUIDBean.class);
    System.out.println(bean1.getUuid());
    System.out.println(bean2.getUuid());
    assertNotEquals(bean1.getUuid(), bean2.getUuid());
  }

  @Test
  void prototypeInSingleton() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    // UserService は singleton
    var userService1 = context.getBean(UserServiceNaive.class);
    var userService2 = context.getBean(UserServiceNaive.class);

    // UUIDBean は prototypeだが...
    assertEquals(userService1.getUuid(), userService2.getUuid());
  }

  @Test
  void lookupInSingleton() {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    // UserService は singleton
    var userService1 = context.getBean(UserServiceLookup.class);
    var userService2 = context.getBean(UserServiceLookup.class);

    // Lookup を介して UUIDBean を取得しているので、毎回新しいインスタンスが生成される
    assertNotEquals(userService1.getUuid(), userService2.getUuid());
  }
}

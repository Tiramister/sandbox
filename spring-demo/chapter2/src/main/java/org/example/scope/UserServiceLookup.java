package org.example.scope;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

// Java Config では @Lookup は使えない
// https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-method-injection.html#beans-factory-lookup-method-injection
@Component
public class UserServiceLookup {
  // UUIDBean がフィールドではなくなり、getter メソッドになった
  // private にしてはいけない
  @Lookup("prototypeUUIDBean")
  UUIDBean uuidBean() {
    return null;
  }

  public String getUuid() {
    return uuidBean().getUuid();
  }
}

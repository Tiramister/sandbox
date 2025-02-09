package org.example.scope;

public class UserServiceNaive {
  private final UUIDBean uuidBean;

  public UserServiceNaive(UUIDBean uuidBean) {
    this.uuidBean = uuidBean;
  }

  public String getUuid() {
    return uuidBean.getUuid();
  }
}

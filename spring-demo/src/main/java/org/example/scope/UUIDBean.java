package org.example.scope;

import java.util.UUID;

public class UUIDBean {
    private final String uuid;

    public UUIDBean() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }
}

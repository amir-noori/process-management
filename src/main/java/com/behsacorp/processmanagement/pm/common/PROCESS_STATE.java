package com.behsacorp.processmanagement.pm.common;

public enum PROCESS_STATE {
    ACTIVE("active"),
    CANCELED("canceled"),
    COMPLETED("completed");

    private final String value;

    PROCESS_STATE(String value) {
        this.value = value;
    }
}

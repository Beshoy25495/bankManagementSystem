package com.bwagih.bank.management.system.enums;

import java.util.Objects;

public enum StatusCode {

    ACTIVE("A"),
    INACTIVE("N"),
    PENDING("P");
    private final String code;

    private StatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public static StatusCode getStatusCode(String code) {
        for (StatusCode types : StatusCode.values()) {
            if (Objects.deepEquals(types.getCode(), code))
                return types;
        }
        return null;
    }


}
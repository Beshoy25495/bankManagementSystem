package com.bwagih.bank.management.system.enums;

import java.util.Objects;

public enum RoleName {
    SUPERVISOR("1"),
    TRADER("2"),
    INVESTOR("3");

    private final String code;

    private RoleName(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public static RoleName getStatusCode(String code) {
        for (RoleName types : RoleName.values()) {
            if (Objects.deepEquals(types.getCode(), code))
                return types;
        }
        return null;
    }


}
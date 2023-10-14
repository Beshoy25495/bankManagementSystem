package com.bwagih.bank.management.system.enums;

import java.util.Objects;

public enum TransactionType {
    DEPOSIT("10"),
    WITHDRAW("20");

    private final String code;

    private TransactionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public static TransactionType getTypeId(String code) {
        for (TransactionType types : TransactionType.values()) {
            if (Objects.deepEquals(types.getCode(), code))
                return types;
        }
        return null;
    }


}

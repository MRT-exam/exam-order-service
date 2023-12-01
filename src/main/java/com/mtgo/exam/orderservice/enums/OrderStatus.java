package com.mtgo.exam.orderservice.enums;

public enum OrderStatus {
    PENDING("PEN"),
    ACCEPTED("ACC"),
    CANCELLED("CANC"),
    CLAIMED("CLAIM"),
    DELIVERED("DELIV");

    private String code;

    private OrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

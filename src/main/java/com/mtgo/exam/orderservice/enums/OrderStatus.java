package com.mtgo.exam.orderservice.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    @JsonProperty("PENDING")
    PENDING("PENDING"),
    @JsonProperty("ACCEPTED")
    ACCEPTED("ACCEPTED"),
    @JsonProperty("CANCELED")
    CANCELED("CANCELED"),
    @JsonProperty("CLAIMED")
    CLAIMED("CLAIMED"),
    @JsonProperty("DELIVERED")
    DELIVERED("DELIVERED");

    private String code;

    OrderStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

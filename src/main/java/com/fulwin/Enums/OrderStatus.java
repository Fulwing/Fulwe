package com.fulwin.Enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING(1),
    PROCESSING(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5),
    RETURNING(6),
    RETURNED(7);


    OrderStatus(int code) {
        this.code = code;
    }

    @EnumValue
    @JsonValue
    private final int code;

}

package com.cn.chat.bridge.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum TrueOrFalseEnum {

    TRUE(1, true),
    FALSE(0, false),
    ;
    public final Integer n;
    public final Boolean b;

    /**
     * null --> false
     * 0 --> false
     * 1 --> true
     * 123 ->> false
     * @param i
     * @return
     */
    public static boolean toBoolean(Integer i) {
        return Objects.nonNull(i) && Objects.equals(i, TRUE.n);
    }
}

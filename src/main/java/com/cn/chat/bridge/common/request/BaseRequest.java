package com.cn.chat.bridge.common.request;

import org.jetbrains.annotations.NotNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public interface BaseRequest extends Validator {

    /**
     * 预处理
     */
    default void preTreatment() {

    }

    default void validate() {

    }

    @Override
    default void validate(@NotNull Object o, @NotNull Errors errors) {
        if (!(o instanceof BaseRequest baseRequest)) {
            return;
        }
        baseRequest.preTreatment();
        baseRequest.validate();
    }

    @Override
    default boolean supports(@NotNull Class<?> clazz) {
        return BaseRequest.class.isAssignableFrom(clazz);
    }
}

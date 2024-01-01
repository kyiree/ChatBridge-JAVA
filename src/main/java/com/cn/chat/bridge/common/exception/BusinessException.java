package com.cn.chat.bridge.common.exception;


import com.cn.chat.bridge.common.constant.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;


/**
 * 统一类异常处理
 *
 */
@Data
@SuppressWarnings("all")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessException extends RuntimeException {

    private CodeEnum codeEnum;

    private List<String> params;

    private BusinessException(CodeEnum code) {
        this.codeEnum = code;
    }

    public static BusinessException create(CodeEnum codeEnum) {
        return new BusinessException(codeEnum);
    }

    public static void assertNotBlank(String str) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(CodeEnum.STR_BLANK_ERROR);
        }
    }

    public static void assertNotNull(Object obj) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(CodeEnum.OBJ_NULL_ERROR);
        }
    }

    public static void assertNotNull(Object obj, CodeEnum code) {
        if (Objects.isNull(obj)) {
            throw new BusinessException(code);
        }
    }

    public static void assertTrue(boolean t, CodeEnum code, String ... params) {
        if (!t) {
            throw new BusinessException(code, Lists.newArrayList(params));
        }
    }

    public static void assertNotEmpty(Collection collection, CodeEnum code) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new BusinessException(code);
        }
    }
}

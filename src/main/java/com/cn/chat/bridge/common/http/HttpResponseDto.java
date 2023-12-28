package com.cn.chat.bridge.common.http;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HttpResponseDto {

    private final Integer httpCode;

    private final String responseData;
}

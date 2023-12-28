package com.cn.chat.bridge.common.vo;

import com.cn.chat.bridge.common.constant.CodeEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseVo<T> {

    /**
     * 错误码
     * @see CodeEnum#getCode()
     */
    private Integer code;

    /**
     * 错误描述
     * @see CodeEnum#getMsg()
     */
    private String msg;

    private T data;

    /**
     * 当前时间戳
     */
    private final Long timestamp = System.currentTimeMillis();

    public static <T> ResponseVo<T> success(T data) {
        ResponseVo<T> vo = new ResponseVo<>();
        vo.setCode(CodeEnum.SUCCESS.getCode());
        vo.setMsg(CodeEnum.SUCCESS.getMsg());
        vo.setData(data);
        return vo;
    }

    public static <T> ResponseVo<T> success() {
        ResponseVo<T> vo = new ResponseVo<>();
        vo.setCode(CodeEnum.SUCCESS.getCode());
        vo.setMsg(CodeEnum.SUCCESS.getMsg());
        return vo;
    }

    public static <T> ResponseVo<T> failure(CodeEnum codeEnum) {
        ResponseVo<T> vo = new ResponseVo<>();
        vo.setCode(codeEnum.getCode());
        vo.setMsg(codeEnum.getMsg());
        return vo;
    }
}

package com.cn.chat.bridge.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {

    SUCCESS(0, "成功"),
    STR_BLANK_ERROR(1, "参数不能为空"),

    OBJ_NULL_ERROR(2, "对象不能为空"),

    JSON_ERROR(3, "序列化异常"),

    EMAIL_NOT_EXISTS(4, "该邮箱不存在"),

    CODE_ERROR(5, "验证码错误"),

    VERIFICATION_CODE_ERR(6, "生成验证码失败"),

    ACCOUNT_ALREADY_EXISTS_ERR(7, "该账号已存在"),

    EMAIL_LOGIN_PWD_ERR(8, "邮箱或密码错误"),
    WECHAT_AUTHORIZATION(9, "微信API能力调用失败"),
    WECHAT_CODE_ERR(10, "二维码已过期,请重新扫一扫"),
    COMMON_ERR_PARAM_FORMAT_T(11, "参数不符合格式要求"),
    COMMON_ERR_SYSTEM_ERR(12, "服务出错，请联系技术人员！"),
    EMAIL_BIND_ERR(13, "当前微信号已经绑定过了,不可再次绑定"),
    FREQUENCY_EMPTY(14, "对象不存在或次数用完"),
    PLACE_AN_ORDER_REPEATEDLY_ERR(15, "请勿重复下单"),
    PRODUCT_NULL_ERR(16, "商品不存在"),
    BUILD_FAILED_PAY_ERR(17, "生成支付二维码失败"),
    IS_SIGN_IN(18, "今天你已经签到过了,请明天再来"),
    NOT_EXIST_CODE(19, "兑换码不存在或已失效"),
    CONCURRENT(20, "手速太快了! 请稍后重新点击"),
    CONNECT_CLOSE(21, "连接关闭"),
    LOGIN_ERROR(22, "登录信息失效,请重新登录"),
    FILE_UPLOAD_ERROR(23, "上传文件异常"),
    PRIVILEGE_ERROR(24, "数据不存在或没有访问权限"),

    COMMON_EXIST(201, "%s已存在"),

    OBJ_NULL_ERROR_WITH_PARAM(202, "%s不能为空"),
    ;
    public final Integer code;
    public final String msg;
}

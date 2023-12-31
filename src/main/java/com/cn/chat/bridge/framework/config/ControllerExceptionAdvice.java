package com.cn.chat.bridge.framework.config;

import cn.dev33.satoken.exception.NotLoginException;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import com.cn.chat.bridge.common.request.BaseRequest;
import com.cn.chat.bridge.common.vo.ResponseVo;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target instanceof BaseRequest baseRequest) {
            binder.addValidators(baseRequest);
        }
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    private <T> ResponseVo<T> validExceptionHandler(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        if (cause instanceof InvalidFormatException exception) {
            List<JsonMappingException.Reference> path = exception.getPath();
            Optional<JsonMappingException.Reference> optionalReference = path.stream()
                    .filter(o -> AnnotationUtils.isCandidateClass(o.getFrom().getClass(), ""))
                    .findFirst();
            if (optionalReference.isPresent()) {
                JsonMappingException.Reference reference = optionalReference.get();
                String fieldName = reference.getFieldName();
                String errorMsg = String.format("%s[%s]",
                        fieldName,
                        exception.getValue());
                log.error(errorMsg);
                return ResponseVo.failure(CodeEnum.COMMON_ERR_PARAM_FORMAT_T);
            }
        }
        return ResponseVo.failure(CodeEnum.COMMON_ERR_PARAM_FORMAT_T);
    }

    @ExceptionHandler(value = BindException.class)
    private <T> ResponseVo<T> bindExceptionHandler(BindException e) {
        List<FieldError> allErrors = e.getBindingResult().getFieldErrors();
        ResponseVo<T> responseDto = ResponseVo.failure(CodeEnum.COMMON_ERR_PARAM_FORMAT_T);
        responseDto.setMsg(logBindError(allErrors));
        return responseDto;
    }

    @ExceptionHandler(value = BusinessException.class)
    private <T> ResponseVo<T> businessExceptionHandler(BusinessException e) {
        log.error(e.getMessage(), e);
        ResponseVo<T> responseDto = ResponseVo.failure(e.getCodeEnum());
        responseDto.setMsg(responseDto.getMsg());
        return responseDto;
    }

    @ExceptionHandler(value = Exception.class)
    private <T> ResponseVo<T> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        ResponseVo<T> responseDto = ResponseVo.failure(CodeEnum.COMMON_ERR_SYSTEM_ERR);
        responseDto.setMsg(responseDto.getMsg());
        return responseDto;
    }

    @ExceptionHandler(NotLoginException.class)
    public <T> ResponseVo<T> handlerException(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return ResponseVo.failure(CodeEnum.LOGIN_ERROR);
    }

    private String logBindError(List<FieldError> allErrors) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : allErrors) {
            sb.append(error.getField())
                    .append(" ")
                    .append(error.getDefaultMessage()).append(", ");
        }
        String msg = StringUtils.substringBeforeLast(sb.toString(), ", ");
        log.error("参数校验异常[{}]", msg);
        return msg;
    }

}

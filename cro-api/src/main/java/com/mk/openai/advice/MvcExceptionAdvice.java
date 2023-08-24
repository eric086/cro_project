
package com.mk.openai.advice;


import com.mk.openai.common.JsonResponse;
import com.mk.openai.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;
import java.util.UUID;

import static com.mk.openai.enums.ErrorCodeEnum.INVALID_ARGUMENT;


@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MvcExceptionAdvice implements ResponseBodyAdvice<Object> {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse<?>> handleException(Exception ex) {

        ex.printStackTrace();

        JsonResponse<?> build =
                JsonResponse.builder()
                        .wrapFailed(HttpStatus.BAD_REQUEST.value(), ex.getMessage())
                        .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<JsonResponse<?>> handleBizException(ServiceException ex) {
        JsonResponse<?> build =
                JsonResponse.builder()
                        .wrapFailed(ex.getCode(), ex.getMessage())
                        .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JsonResponse<?>> handleBizException(MethodArgumentNotValidException ex) {
        JsonResponse<?> build =
                JsonResponse.builder()
                        .wrapFailed(INVALID_ARGUMENT.getCode(), Objects.requireNonNull(ex.getFieldError()).getDefaultMessage())
                        .build();

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }



    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String requestId = UUID.randomUUID().toString();
        if (body instanceof JsonResponse) {
            if (Strings.isEmpty(((JsonResponse<?>) body).getRequestId())) {
                ((JsonResponse<?>) body).setRequestId(requestId);
            }
        }
        return body;
    }
}


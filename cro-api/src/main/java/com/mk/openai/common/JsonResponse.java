package com.mk.openai.common;


import com.mk.openai.enums.ErrorCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonResponse<T> {
    private int code;
    private String msg;
    private String requestId;
    private T data;


    public static class JsonResponseBuilder<T> {
        public JsonResponseBuilder<T> wrapSuccess() {
            this.msg(ErrorCodeEnum.SUCCESS.getMsg()).code(ErrorCodeEnum.SUCCESS.getCode());
            return this;
        }

        public JsonResponseBuilder<T> wrapFailed(int code, String msg) {
            this.msg(msg).code(code);
            return this;
        }
    }
}

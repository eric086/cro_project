package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginRequest {
    @NotBlank(message = "手机不能为空")

    private String mobile;
    @NotBlank(message = "密码不能为空")
    private String password;

}

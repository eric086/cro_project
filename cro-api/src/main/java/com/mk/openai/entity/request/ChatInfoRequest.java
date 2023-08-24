package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ChatInfoRequest {
    @NotBlank
    private String ask;

    @NotBlank
    private String answer;

    @NotNull
    private Integer userId;

    @NotNull
    private String createAt;
}

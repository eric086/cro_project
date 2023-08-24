package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class BaseAskRequest {

    @NotBlank
    private String ask;
    private String answer;
}

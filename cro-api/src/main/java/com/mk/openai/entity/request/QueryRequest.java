package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class QueryRequest {
    @NotBlank(message = "查询不能为空")
    private String query;

    private String topK;

}

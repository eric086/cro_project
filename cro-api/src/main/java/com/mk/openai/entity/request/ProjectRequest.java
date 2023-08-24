package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ProjectRequest {

    private Integer projectId;

    @NotBlank
    private String name;
}

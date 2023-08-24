package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class ProjectBaseRequest {
    @NotNull
    private Integer projectId;
    @Valid
    @NotNull
    @Size(min = 1,message = "至少需要一条基本信息")
    private List<BaseAskRequest> baseInfo;
}

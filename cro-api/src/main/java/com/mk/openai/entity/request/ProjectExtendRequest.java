package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class ProjectExtendRequest {

    @NotNull
    private Integer projectId;
    @NotNull
    @Size(min = 1,message = "至少需要一条扩展信息")
    @Valid
    private List<ExtendInfoRequest> extendInfo;
}

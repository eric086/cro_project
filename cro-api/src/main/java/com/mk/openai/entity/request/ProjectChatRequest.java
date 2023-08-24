package com.mk.openai.entity.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class ProjectChatRequest {
    @NotNull
    private Integer projectId;

    @NotNull
    private Integer chatId;

    @Valid
    @Size(min =1)
    private List<ChatInfoRequest> askList;
}

package com.mk.openai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectChatVo {
    private Integer projectId;
    private Integer chatId;
    private Integer number;
    private String title;

}

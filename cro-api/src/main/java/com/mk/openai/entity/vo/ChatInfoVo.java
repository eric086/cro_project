package com.mk.openai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatInfoVo {
    private Integer chatId;
    private String ask;
    private String answer;
    private Integer userId;
    private String userName;
    private String avatar;
    private String createAt;
}

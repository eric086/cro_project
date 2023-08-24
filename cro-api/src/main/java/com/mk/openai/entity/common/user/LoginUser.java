package com.mk.openai.entity.common.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginUser {

    private Integer userId;

    private String mobile;

    private String userName;

    private String avatar;

    private String token;

    private LocalDateTime startTime;

    private LocalDateTime expireTime;
}

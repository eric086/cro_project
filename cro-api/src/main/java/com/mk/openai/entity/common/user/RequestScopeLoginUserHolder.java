package com.mk.openai.entity.common.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class RequestScopeLoginUserHolder {
    private LoginUser currentUser;
}

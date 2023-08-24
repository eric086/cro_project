package com.mk.openai.filter;


import com.mk.openai.entity.common.user.LoginUser;
import com.mk.openai.entity.common.user.RequestScopeLoginUserHolder;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@Order(1)
@AllArgsConstructor
public class TokenFilter extends OncePerRequestFilter {
    public static final String FILTER_ERROR_ATTRIBUTE = "filter.error";
    private static final String BEARER = "Bearer ";
    private final ServerProperties serverProperties;
    private final RequestScopeLoginUserHolder loginUserHolder;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            LoginUser loginUser = getLoginUser(request);
            if (log.isDebugEnabled()) {
                log.debug("user info {}", loginUser);
            }
            loginUserHolder.setCurrentUser(loginUser);

            chain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute(FILTER_ERROR_ATTRIBUTE, e);
            request.getRequestDispatcher(serverProperties.getError().getPath())
                    .forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    private LoginUser getLoginUser(HttpServletRequest httpRequest) {
        String userToken = getTokenByRequest(httpRequest);
        return TokenUtil.getUser(userToken);
    }

    public String getTokenByRequest(HttpServletRequest request) {

        String userToken =
                Optional.ofNullable(request.getHeader(AUTHORIZATION))
                        .orElseThrow(() -> new ServiceException("Unauthorized"));

        if (!userToken.startsWith(BEARER)) {
            log.info(" user token is wrong {}", userToken);
            throw new ServiceException("Unauthorized");
        }
        return userToken.replace(BEARER, "");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return true;

//        if (request.getRequestURI().contains("/actuator")
//                || request.getRequestURI().contains("/login/index")
//        ) {
//            return true;
//        }
//
//        return false;
    }
}

package com.mk.openai.util;


import com.mk.openai.entity.common.user.LoginUser;
import com.mk.openai.entity.dao.TblUser;
import com.mk.openai.enums.ErrorCodeEnum;
import com.mk.openai.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class TokenUtil {
    private static final Map<String, LoginUser> tokenMap = new HashMap<>();

    public static LoginUser generateToken(TblUser user,Integer tokenCacheTime) {
        String token = UUID.randomUUID().toString().replace("-", "");
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime expireTime = startTime.plusSeconds(tokenCacheTime);

        LoginUser loginUser = LoginUser.builder()
                .userId(user.getId())
                .userName(user.getName())
                .avatar(user.getAvatar())
                .mobile(user.getMobile())
                .token(token)
                .startTime(startTime)
                .expireTime(expireTime)
                .build();
        tokenMap.put(token, loginUser);
        return loginUser;
    }

    public static boolean verifyToken(String token) {
        return tokenMap.containsKey(token);
    }

    public static LoginUser getUser(String token) {
        LoginUser loginUser = tokenMap.get(token);
        if(Objects.isNull(loginUser)){
            throw new ServiceException(ErrorCodeEnum.UNAUTHENTICATED);
        }
        return loginUser;
    }

    public static void deleteUser(String token){
        tokenMap.remove(token);
    }

}

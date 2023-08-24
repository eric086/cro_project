package com.mk.openai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.openai.entity.common.user.LoginUser;
import com.mk.openai.entity.dao.TblUser;
import com.mk.openai.entity.request.LoginRequest;


public interface TblUserService extends IService<TblUser> {
    LoginUser login(LoginRequest request);
}

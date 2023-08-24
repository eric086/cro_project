package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.config.ConfigMap;
import com.mk.openai.entity.common.user.LoginUser;
import com.mk.openai.entity.dao.TblUser;
import com.mk.openai.entity.request.LoginRequest;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.util.TokenUtil;
import lombok.AllArgsConstructor;
import com.mk.openai.mapper.TblUserMapper;
import org.springframework.stereotype.Service;
import com.mk.openai.service.TblUserService;

import java.util.Objects;

@Service
@AllArgsConstructor
public class TblUserServiceImpl extends ServiceImpl<TblUserMapper, TblUser>
    implements TblUserService{

    private final ConfigMap configMap;

    @Override
    public LoginUser login(LoginRequest request){
        LambdaQueryWrapper<TblUser> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblUser::getMobile, request.getMobile());

        TblUser tblUserDao = getOne(lambdaQuery);

        if (Objects.isNull(tblUserDao)) {
            throw new ServiceException("用户不存在");
        }

        if (!tblUserDao.getPassword().equals(request.getPassword())) {
            throw new ServiceException("密码错误");
        }

        return TokenUtil.generateToken(tblUserDao,configMap.getTokenCacheTime());
    }

}





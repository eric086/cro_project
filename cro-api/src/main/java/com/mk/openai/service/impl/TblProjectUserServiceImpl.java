package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.entity.dao.TblProjectUser;
import com.mk.openai.service.TblProjectUserService;
import com.mk.openai.mapper.TblProjectUserMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TblProjectUserServiceImpl extends ServiceImpl<TblProjectUserMapper, TblProjectUser>
    implements TblProjectUserService{

    @Override
    public List<Integer> getProjectIdsByUserId(Integer userId) {
        LambdaQueryWrapper<TblProjectUser> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(TblProjectUser::getUserId, userId);
        List<TblProjectUser> list  = list(lambdaQueryWrapper);

        List<Integer> projectIds = new ArrayList<>();

        if(!list.isEmpty()){
            list.forEach(i->projectIds.add(i.getProjectId()));
        }
        return projectIds;
    }

}





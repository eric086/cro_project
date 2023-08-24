package com.mk.openai.service;

import com.mk.openai.entity.dao.TblProjectUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TblProjectUserService extends IService<TblProjectUser> {
    List<Integer> getProjectIdsByUserId(Integer userId);
}

package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.entity.dao.TblCategory;
import com.mk.openai.service.TblCategoryService;
import com.mk.openai.mapper.TblCategoryMapper;
import org.springframework.stereotype.Service;

@Service
public class TblCategoryServiceImpl extends ServiceImpl<TblCategoryMapper, TblCategory>
    implements TblCategoryService{

}





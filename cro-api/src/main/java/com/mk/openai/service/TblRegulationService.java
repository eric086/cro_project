package com.mk.openai.service;

import com.mk.openai.entity.dao.TblRegulation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.openai.entity.vo.RegulationVo;

import java.util.List;


public interface TblRegulationService extends IService<TblRegulation> {
    List<RegulationVo> getRegulationList();
}

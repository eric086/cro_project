package com.mk.openai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.openai.entity.dao.TblProjectRegulation;
import com.mk.openai.entity.request.ProjectRegulationRequest;
import com.mk.openai.entity.vo.ProjectRegulationVo;

import java.util.List;


public interface TblProjectRegulationService extends IService<TblProjectRegulation> {
    List<ProjectRegulationVo> getProjectRegulationList(Integer projectId);

    void saveProjectRegulation(ProjectRegulationRequest request);
}

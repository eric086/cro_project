package com.mk.openai.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.openai.entity.dao.TblProject;

import com.mk.openai.entity.request.ProjectBaseRequest;
import com.mk.openai.entity.request.ProjectExtendRequest;
import com.mk.openai.entity.request.ProjectRequest;
import com.mk.openai.entity.vo.ProjectVo;

import java.util.List;


public interface TblProjectService extends IService<TblProject> {
    List<ProjectVo> getListByUserId(Integer userId);

    ProjectVo getProjectInfoById(Integer projectId);

    int createProject(Integer userId, ProjectRequest request);

    void updateProject(Integer userId,ProjectRequest request);

    void saveProjectBaseInfo(ProjectBaseRequest request);

    void saveProjectExtend(ProjectExtendRequest request);

    TblProject checkProject(Integer projectId);




}

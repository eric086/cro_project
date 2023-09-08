package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.entity.dao.TblCategory;
import com.mk.openai.entity.dao.TblProjectBase;
import com.mk.openai.entity.dao.TblProjectRegulation;
import com.mk.openai.entity.dao.TblRegulation;
import com.mk.openai.entity.request.ProjectRegulationRequest;
import com.mk.openai.entity.vo.ProjectRegulationVo;
import com.mk.openai.service.*;
import com.mk.openai.mapper.TblProjectRegulationMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TblProjectRegulationServiceImpl extends ServiceImpl<TblProjectRegulationMapper, TblProjectRegulation>
        implements TblProjectRegulationService {


    private final TblRegulationService tblRegulationService;

    private final TblCategoryService tblCategoryService;

    private final TblProjectService tblProjectService;

    private final AiService aiService;

    @Override
    public List<ProjectRegulationVo> getProjectRegulationList(Integer projectId) {

        LambdaQueryWrapper<TblProjectRegulation> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblProjectRegulation::getProjectId, projectId);

        List<TblProjectRegulation> projectRegulationList = list(lambdaQuery);

        if (projectRegulationList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> regulationIds = new ArrayList<>();

        projectRegulationList.forEach(i -> {
            regulationIds.add(i.getRegulationId());
        });

        List<TblRegulation> regulationList = tblRegulationService.listByIds(regulationIds);

        List<TblCategory> categoryList = tblCategoryService.list();

        Map<Integer, TblCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(TblCategory::getId,
                Function.identity()));


        List<ProjectRegulationVo> projectRegulationVoList = new ArrayList<>();

        regulationList.forEach(r -> {

            String categoryName = categoryMap.containsKey(r.getCategoryId()) ?
                    categoryMap.get(r.getCategoryId()).getName() : "";

            ProjectRegulationVo projectRegulationVo = ProjectRegulationVo.builder()
                    .projectId(projectId)
                    .regulationId(r.getId())
                    .fileName(r.getFileName())
                    .filePath(r.getFilePath())
                    .categoryId(r.getCategoryId())
                    .categoryName(categoryName)
                    .build();
            projectRegulationVoList.add(projectRegulationVo);
        });

        return projectRegulationVoList;
    }

    @Override
    @Transactional
    public void saveProjectRegulation(ProjectRegulationRequest request) {

        tblProjectService.checkProject(request.getProjectId());

        // 删除
        LambdaQueryWrapper<TblProjectRegulation> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblProjectRegulation::getProjectId,request.getProjectId());
        remove(lambdaQuery);

        List<TblProjectRegulation> projectRegulationList = new ArrayList<>();
        request.getRegulationIdList().forEach(r->{
            TblProjectRegulation projectRegulation = TblProjectRegulation.builder()
                    .projectId(request.getProjectId())
                    .regulationId(r)
                    .build();
            projectRegulationList.add(projectRegulation);
        });
        saveBatch(projectRegulationList);
    }
}





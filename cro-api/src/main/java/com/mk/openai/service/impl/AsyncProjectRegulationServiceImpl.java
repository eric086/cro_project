package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mk.openai.entity.dao.TblProjectRegulation;
import com.mk.openai.mapper.TblProjectRegulationMapper;
import com.mk.openai.service.AiService;
import com.mk.openai.service.AsyncProjectRegulationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class AsyncProjectRegulationServiceImpl implements AsyncProjectRegulationService {
    private final AiService aiService;
    private final TblProjectRegulationMapper tblProjectRegulationMapper;

    @Transactional
    @Async
    public void baseInfoChangeProjectRegulation(Integer projectId, List<String> queryList) {
        // 删除
        LambdaQueryWrapper<TblProjectRegulation> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblProjectRegulation::getProjectId, projectId);
        tblProjectRegulationMapper.delete(lambdaQuery);

        List<Integer> regulationIdList = aiService.queryList(queryList);
        regulationIdList.forEach(r -> {
            TblProjectRegulation projectRegulation = TblProjectRegulation.builder()
                    .projectId(projectId)
                    .regulationId(r)
                    .build();
            tblProjectRegulationMapper.insert(projectRegulation);
        });
        log.info("任务完成");
    }
}





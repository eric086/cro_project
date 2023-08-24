package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.entity.dao.TblCategory;
import com.mk.openai.entity.dao.TblRegulation;
import com.mk.openai.entity.vo.ProjectRegulationVo;
import com.mk.openai.entity.vo.RegulationVo;
import com.mk.openai.service.TblCategoryService;
import com.mk.openai.service.TblRegulationService;
import com.mk.openai.mapper.TblRegulationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TblRegulationServiceImpl extends ServiceImpl<TblRegulationMapper, TblRegulation>
    implements TblRegulationService{

    private final TblCategoryService tblCategoryService;

    @Override
    public List<RegulationVo> getRegulationList() {
        List<TblRegulation> regulationList = list();

        if (regulationList.isEmpty()) {
            return Collections.emptyList();
        }
        List<TblCategory> categoryList = tblCategoryService.list();

        Map<Integer, TblCategory> categoryMap = categoryList.stream().collect(Collectors.toMap(TblCategory::getId,
                Function.identity()));

        List<RegulationVo> regulationVoList = new ArrayList<>();

        regulationList.forEach(r -> {

            String categoryName = categoryMap.containsKey(r.getCategoryId()) ?
                    categoryMap.get(r.getCategoryId()).getName() : "";

            RegulationVo regulationVo = RegulationVo.builder()
                    .regulationId(r.getId())
                    .fileName(r.getFileName())
                    .filePath(r.getFilePath())
                    .categoryId(r.getCategoryId())
                    .categoryName(categoryName)
                    .build();
            regulationVoList.add(regulationVo);
        });

        return regulationVoList;
    }
}





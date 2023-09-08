package com.mk.openai.service;


import java.util.List;

public interface AsyncProjectRegulationService {
    void baseInfoChangeProjectRegulation(Integer projectId, List<String> queryList);
}

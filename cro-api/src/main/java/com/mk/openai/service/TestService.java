package com.mk.openai.service;

import com.mk.openai.entity.vo.RegulationVo;

import java.util.List;

public interface TestService {
    List<RegulationVo> query(String query, String topK);
}

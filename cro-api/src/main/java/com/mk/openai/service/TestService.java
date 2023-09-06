package com.mk.openai.service;

import com.mk.openai.entity.vo.PassageVo;

import java.util.List;

public interface TestService {
    List<PassageVo> query(String query, String topK);
}

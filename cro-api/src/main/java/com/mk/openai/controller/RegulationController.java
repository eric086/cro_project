package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.vo.RegulationVo;
import com.mk.openai.service.TblRegulationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/regulation")
public class RegulationController {
    private final TblRegulationService tblRegulationService;

    @GetMapping("/list")
    public JsonResponse<List<RegulationVo>> getList() {
        return JsonResponse.<List<RegulationVo>>builder().data(tblRegulationService.getRegulationList()).wrapSuccess().build();
    }
}

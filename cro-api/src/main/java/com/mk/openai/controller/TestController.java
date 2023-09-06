package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.request.QueryRequest;
import com.mk.openai.entity.vo.PassageVo;
import com.mk.openai.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {


    private TestService testService;

    @PostMapping("/query")
    public JsonResponse<List<PassageVo>> query(@RequestBody @Valid QueryRequest request) {
        return JsonResponse.<List<PassageVo>>builder().data(testService.query(request.getQuery(),request.getTopK())).wrapSuccess().build();
    }
}

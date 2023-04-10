package com.mk.openai.controller;

import com.mk.openai.service.TextCompletionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Resource
    TextCompletionService completionService;

    @PostMapping("/ask")
    public String search(@RequestParam String question) {
        return completionService.ask(question);
    }
}

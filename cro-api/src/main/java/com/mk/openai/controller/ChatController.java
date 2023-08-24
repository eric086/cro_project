package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.request.ProjectChatRequest;
import com.mk.openai.entity.vo.ChatInfoVo;
import com.mk.openai.entity.vo.ProjectChatVo;
import com.mk.openai.service.TblChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/project/ask")
public class ChatController {
    private final TblChatService tblChatService;

    @GetMapping("/list/{projectId}")
    public JsonResponse<List<ProjectChatVo>> getList(@PathVariable Integer projectId) {
        return JsonResponse.<List<ProjectChatVo>>builder().data(tblChatService.getListByProjectId(projectId)).wrapSuccess().build();
    }

    @GetMapping("/info/{chatId}")
    public JsonResponse<List<ChatInfoVo>> getOne(@PathVariable Integer chatId) {
        return JsonResponse.<List<ChatInfoVo>>builder().data(tblChatService.getChatInfoListByChatId(chatId)).wrapSuccess().build();
    }

    @PostMapping("/save")
    public JsonResponse<Void> save(@RequestBody @Valid ProjectChatRequest request) {
        tblChatService.saveChatInfo(request);
        return JsonResponse.<Void>builder().wrapSuccess().build();
    }
}

package com.mk.openai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.openai.entity.dao.TblChat;
import com.mk.openai.entity.request.ProjectChatRequest;
import com.mk.openai.entity.vo.ChatInfoVo;
import com.mk.openai.entity.vo.ProjectChatVo;

import java.util.List;


public interface TblChatService extends IService<TblChat> {
    List<ProjectChatVo> getListByProjectId(Integer projectId);

    List<ChatInfoVo> getChatInfoListByChatId(Integer chatId);

    void saveChatInfo(ProjectChatRequest request);
}

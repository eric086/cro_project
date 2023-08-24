package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.entity.dao.TblChat;
import com.mk.openai.entity.dao.TblChatInfo;
import com.mk.openai.entity.dao.TblProjectExtend;
import com.mk.openai.entity.dao.TblUser;
import com.mk.openai.entity.request.ProjectChatRequest;
import com.mk.openai.entity.vo.ChatInfoVo;
import com.mk.openai.entity.vo.ProjectChatVo;
import com.mk.openai.mapper.TblChatMapper;
import com.mk.openai.service.TblChatInfoService;
import com.mk.openai.service.TblChatService;
import com.mk.openai.service.TblProjectService;
import com.mk.openai.service.TblUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class TblChatServiceImpl extends ServiceImpl<TblChatMapper, TblChat>
        implements TblChatService {

    private final TblChatInfoService tblChatInfoService;

    private final TblUserService tblUserService;

    private final TblProjectService tblProjectService;

    @Override
    public List<ProjectChatVo> getListByProjectId(Integer projectId) {

        LambdaQueryWrapper<TblChat> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblChat::getProjectId, projectId);
        List<TblChat> list = list(lambdaQuery);

        if (list.isEmpty()) {
            return Collections.emptyList();
        }

        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatInfoVo> getChatInfoListByChatId(Integer chatId) {
        LambdaQueryWrapper<TblChatInfo> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblChatInfo::getChatId, chatId);
        lambdaQuery.orderByAsc(TblChatInfo::getCreateAt);
        List<TblChatInfo> chatInfoList = tblChatInfoService.list(lambdaQuery);

        if (chatInfoList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> userIds = chatInfoList.stream().map(TblChatInfo::getQuestioner).collect(Collectors.toList());

        Map<Integer,TblUser> userMap = tblUserService.listByIds(userIds).stream().collect(Collectors.toMap(TblUser::getId, Function.identity()));

        List<ChatInfoVo> list = new ArrayList<>();

        chatInfoList.forEach(chatInfo->{

            TblUser user = userMap.getOrDefault(chatInfo.getQuestioner(), null);
            String userName = Objects.nonNull(user) ? user.getName() : null;
            String avatar = Objects.nonNull(user) ? user.getAvatar() : null;

            LocalDateTime createAt = chatInfo.getCreateAt().toInstant().atZone(ZoneOffset.of("+8")).toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            ChatInfoVo chatInfoVo =  ChatInfoVo.builder()
                    .chatId(chatInfo.getChatId())
                    .ask(chatInfo.getAsk())
                    .answer(chatInfo.getAnswer())
                    .userId(chatInfo.getQuestioner())
                    .userName(userName)
                    .avatar(avatar)
                    .createAt(createAt.format(formatter))
                    .build();
            list.add(chatInfoVo);
        });
        return list;
    }

    @Override
    public void saveChatInfo(ProjectChatRequest request) {
        tblProjectService.checkProject(request.getProjectId());

        // 删除
        LambdaQueryWrapper<TblChatInfo> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblChatInfo::getChatId, request.getChatId());
        tblChatInfoService.remove(lambdaQuery);

        List<TblChatInfo> chatInfoList = new ArrayList<>();




        request.getAskList().forEach(a->{
            LocalDateTime createAt = LocalDateTime.parse(a.getCreateAt(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            TblChatInfo chatInfo = new TblChatInfo();
            chatInfo.setChatId(request.getChatId());
            chatInfo.setAsk(a.getAsk());
            chatInfo.setAnswer(a.getAnswer());
            chatInfo.setQuestioner(a.getUserId());
            chatInfo.setCreateAt(Date.from(createAt.toInstant(ZoneOffset.of("+8"))));
            chatInfoList.add(chatInfo);
        });

        tblChatInfoService.saveBatch(chatInfoList);
    }

    private ProjectChatVo convert(TblChat chat) {
        return ProjectChatVo.builder()
                .projectId(chat.getProjectId())
                .chatId(chat.getId())
                .number(chat.getNumber())
                .title(chat.getTitle())
                .build();
    }

}





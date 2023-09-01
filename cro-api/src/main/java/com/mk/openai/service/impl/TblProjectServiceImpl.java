package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.entity.dao.*;
import com.mk.openai.entity.request.ProjectBaseRequest;
import com.mk.openai.entity.request.ProjectExtendRequest;
import com.mk.openai.entity.request.ProjectRequest;
import com.mk.openai.entity.vo.*;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.mapper.TblProjectMapper;
import com.mk.openai.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TblProjectServiceImpl extends ServiceImpl<TblProjectMapper, TblProject>
        implements TblProjectService {

    private final TblProjectUserService tblProjectUserService;
    private final TblProjectBaseService tblProjectBaseService;
    private final TblProjectExtendService tblProjectExtendService;
    private final TblFileService tblFileService;
    private final TblUserService tblUserService;

    @Override
    public List<ProjectVo> getListByUserId(Integer userId) {

        List<Integer> projectIds = tblProjectUserService.getProjectIdsByUserId(userId);

        if (projectIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<TblProject> list = listByIds(projectIds);

        return list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public ProjectVo getProjectInfoById(Integer projectId) {

        TblProject project = checkProject(projectId);

        //base info
        LambdaQueryWrapper<TblProjectBase> baseLambdaQuery = Wrappers.lambdaQuery();
        baseLambdaQuery.eq(TblProjectBase::getProjectId, projectId);
        baseLambdaQuery.orderByAsc(TblProjectBase::getId);
        List<TblProjectBase> baseInfoList = tblProjectBaseService.list(baseLambdaQuery);

        //extend info
        LambdaQueryWrapper<TblProjectExtend> extendLambdaQuery = Wrappers.lambdaQuery();
        extendLambdaQuery.eq(TblProjectExtend::getProjectId, projectId);
        extendLambdaQuery.orderByAsc(TblProjectExtend::getId);
        List<TblProjectExtend> extendInfoList = tblProjectExtendService.list(extendLambdaQuery);


        return convert(project, baseInfoList, extendInfoList);
    }

    @Override
    public int createProject(Integer userId, ProjectRequest request) {
        TblProject tblProject = new TblProject();
        tblProject.setName(request.getName());
        tblProject.setCreateBy(userId);
        save(tblProject);
        return tblProject.getId();
    }

    @Override
    public void updateProject(Integer userId, ProjectRequest request) {
        TblProject tblProject = new TblProject();
        tblProject.setUpdateBy(userId);
        tblProject.setName(request.getName());
        tblProject.setUpdateAt(new Date());
        tblProject.setId(request.getProjectId());
        updateById(tblProject);
    }

    @Override
    public void saveProjectBaseInfo(ProjectBaseRequest request) {
        checkProject(request.getProjectId());
        // 删除
        LambdaQueryWrapper<TblProjectBase> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblProjectBase::getProjectId, request.getProjectId());
        tblProjectBaseService.remove(lambdaQuery);

        List<TblProjectBase> insertList = new ArrayList<>();
        request.getBaseInfo().forEach(baseInfo -> {
            TblProjectBase base = TblProjectBase.builder()
                    .projectId(request.getProjectId())
                    .ask(baseInfo.getAsk())
                    .answer(baseInfo.getAnswer())
                    .build();
            insertList.add(base);
        });

        tblProjectBaseService.saveBatch(insertList);

        //TODO AI重新选择法规
    }

    @Override
    public void saveProjectExtend(ProjectExtendRequest request) {
        checkProject(request.getProjectId());

        checkExtend(request);

        // 删除
        LambdaQueryWrapper<TblProjectExtend> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(TblProjectExtend::getProjectId, request.getProjectId());
        tblProjectExtendService.remove(lambdaQuery);

        List<TblProjectExtend> insertList = new ArrayList<>();
        request.getExtendInfo().forEach(extendInfo -> {

            String fileStr = Objects.isNull(extendInfo.getFiles()) ? null : String.join(",", extendInfo.getFiles());
            String imgStr = Objects.isNull(extendInfo.getImgs()) ? null : String.join(",", extendInfo.getImgs());

            TblProjectExtend extend = TblProjectExtend.builder()
                    .projectId(request.getProjectId())
                    .keyword(extendInfo.getKeyword())
                    .text(extendInfo.getText())
                    .file(fileStr)
                    .img(imgStr)
                    .build();
            insertList.add(extend);
        });

        tblProjectExtendService.saveBatch(insertList);

        //TODO AI重新选择法规

    }

    @Override
    public TblProject checkProject(Integer projectId) {
        TblProject project = getById(projectId);
        if (Objects.isNull(project)) {
            throw new ServiceException("项目不存在");
        }
        return project;
    }

    private void checkExtend(ProjectExtendRequest request) {
        List<String> keywords = new ArrayList<>();

        request.getExtendInfo().forEach(extendInfo -> {
            List<String> keyword = Arrays.asList(extendInfo.getKeyword().split(";"));
            keywords.addAll(keyword);
            if (Objects.isNull(extendInfo.getText()) && Objects.isNull(extendInfo.getFiles()) && Objects.isNull(extendInfo.getImgs())) {
                throw new ServiceException("扩展信息内容为空");
            }
        });

        keywords.forEach(k -> {
            int num = Collections.frequency(keywords, k);
            if (num > 1) {
                throw new ServiceException("关键字重复：" + k);
            }
        });
    }

    private ProjectVo convert(TblProject project) {
        LocalDateTime createAt = project.getCreateAt().toInstant().atZone(ZoneOffset.of("+8")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime updateAt = Objects.isNull(project.getUpdateAt()) ? null :
                project.getUpdateAt().toInstant().atZone(ZoneOffset.of("+8")).toLocalDateTime();
        String updateAtStr = Objects.isNull(updateAt) ? null : updateAt.format(formatter);

        return ProjectVo.builder()
                .id(project.getId())
                .name(project.getName())
                .createBy(project.getCreateBy())
                .createAt(createAt.format(formatter))
                .updateAt(updateAtStr)

                .build();
    }

    private ProjectVo convert(TblProject project, List<TblProjectBase> baseInfoList,
                              List<TblProjectExtend> extendInfoList) {

        LocalDateTime createAt = project.getCreateAt().toInstant().atZone(ZoneOffset.of("+8")).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime updateAt = Objects.isNull(project.getUpdateAt()) ? null :
                project.getUpdateAt().toInstant().atZone(ZoneOffset.of("+8")).toLocalDateTime();

        String updateAtStr = Objects.isNull(updateAt) ? null : updateAt.format(formatter);

        //base info
        ProjectBaseInfoVo baseInfoVo = null;
        if (!baseInfoList.isEmpty()) {
            baseInfoVo = new ProjectBaseInfoVo();
            baseInfoVo.setTotal(baseInfoList.size());
            int askNum = 0;
            int noAskNumber = 0;
            List<BaseAskVo> askList = new ArrayList<>();

            for (TblProjectBase base : baseInfoList) {
                if (Objects.equals(base.getAnswer(), "") || Objects.isNull(base.getAnswer())) {
                    noAskNumber++;
                } else {
                    askNum++;
                }
                BaseAskVo askVo = new BaseAskVo();
                askVo.setAsk(base.getAsk());
                askVo.setAnswer(base.getAnswer());
                askList.add(askVo);
            }

            baseInfoVo.setList(askList);
            baseInfoVo.setAskNumber(askNum);
            baseInfoVo.setNoAskNumber(noAskNumber);
        }

        //extend info
        ProjectExtendInfoVo projectExtInfoVo = null;
        if (!extendInfoList.isEmpty()) {
            projectExtInfoVo = new ProjectExtendInfoVo();
            projectExtInfoVo.setTotal(extendInfoList.size());

            List<ExtendInfoVo> extendInfoVoList = new ArrayList<>();
            extendInfoList.forEach(e -> {
                ExtendInfoVo extInfoVo = new ExtendInfoVo();
                extInfoVo.setKeyword(e.getKeyword());
                extInfoVo.setText(e.getText());

                if (Objects.nonNull(e.getFile())) {
                    extInfoVo.setFiles(getFiles(e.getFile()));
                }

                if (Objects.nonNull(e.getImg())) {
                    extInfoVo.setImgs(getFiles(e.getImg()));
                }
                extendInfoVoList.add(extInfoVo);
            });

            projectExtInfoVo.setList(extendInfoVoList);
        }

        TblUser user = tblUserService.getById(project.getCreateBy());

        return ProjectVo.builder()
                .id(project.getId())
                .name(project.getName())
                .createBy(project.getCreateBy())
                .createByName(user.getName())
                .createAt(createAt.format(formatter))
                .updateAt(updateAtStr)
                .baseInfo(baseInfoVo)
                .extendInfo(projectExtInfoVo)
                .build();
    }

    private List<FileVo> getFiles(String fileIdStr) {
        List<String> fileIds = Arrays.asList(fileIdStr.split(","));
        List<TblFile> fileList = tblFileService.listByIds(fileIds);

        if (fileList.isEmpty()) {
            return Collections.emptyList();
        }

        List<FileVo> list = new ArrayList<>();
        fileList.forEach(f -> {
            FileVo fileVo = FileVo.builder()
                    .fileId(f.getId())
                    .originalFilename(f.getOriginalFilename())
                    .filePath(f.getFilePath())
                    .build();
            list.add(fileVo);

        });
        return list;
    }
}





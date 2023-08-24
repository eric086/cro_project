package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.common.user.RequestScopeLoginUserHolder;
import com.mk.openai.entity.request.ProjectBaseRequest;
import com.mk.openai.entity.request.ProjectExtendRequest;
import com.mk.openai.entity.request.ProjectRegulationRequest;
import com.mk.openai.entity.request.ProjectRequest;
import com.mk.openai.entity.vo.ProjectRegulationVo;
import com.mk.openai.entity.vo.ProjectVo;
import com.mk.openai.service.TblProjectRegulationService;
import com.mk.openai.service.TblProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final RequestScopeLoginUserHolder loginUserHolder;

    private final TblProjectService tblProjectService;

    private final TblProjectRegulationService tblProjectRegulationService;

    @GetMapping("/list")
    public JsonResponse<List<ProjectVo>> getList() {
        return JsonResponse.<List<ProjectVo>>builder().data(tblProjectService.getListByUserId(loginUserHolder.getCurrentUser().getUserId())).wrapSuccess().build();
    }

    @GetMapping("/info/{projectId}")
    public JsonResponse<ProjectVo> getOne(@PathVariable @Valid @NotNull Integer projectId) {
        return JsonResponse.<ProjectVo>builder().data(tblProjectService.getProjectInfoById(projectId)).wrapSuccess().build();
    }

    @PostMapping("/create")
    public JsonResponse<Map<String, Integer>> create(@RequestBody @Valid ProjectRequest request) {
        Map<String, Integer> rs = new HashMap<>();
        int id = tblProjectService.createProject(loginUserHolder.getCurrentUser().getUserId(), request);
        rs.put("id", id);
        return JsonResponse.<Map<String, Integer>>builder().data(rs).wrapSuccess().build();
    }

    @PostMapping("/update")
    public JsonResponse<Void> update(@RequestBody @Valid ProjectRequest request) {

        tblProjectService.updateProject(loginUserHolder.getCurrentUser().getUserId(), request);

        return JsonResponse.<Void>builder().wrapSuccess().build();
    }

    @PostMapping("/base/save")
    public JsonResponse<Void> baseInfoSave(@RequestBody @Valid ProjectBaseRequest request) {
        tblProjectService.saveProjectBaseInfo(request);

        return JsonResponse.<Void>builder().wrapSuccess().build();
    }

    @PostMapping("/extend/save")
    public JsonResponse<Void> extendInfoSave(@RequestBody @Valid ProjectExtendRequest request) {
        tblProjectService.saveProjectExtend(request);
        return JsonResponse.<Void>builder().wrapSuccess().build();
    }

    @GetMapping("/regulation/{projectId}")
    public JsonResponse<List<ProjectRegulationVo>> getRegulation(@PathVariable @Valid @NotNull Integer projectId) {
        return JsonResponse.<List<ProjectRegulationVo>>builder().data(tblProjectRegulationService.getProjectRegulationList(projectId)).wrapSuccess().build();
    }

    @PostMapping("/regulation/save")
    public JsonResponse<Void> regulationSave(@RequestBody @Valid ProjectRegulationRequest request) {
        tblProjectRegulationService.saveProjectRegulation(request);
        return JsonResponse.<Void>builder().wrapSuccess().build();
    }
}

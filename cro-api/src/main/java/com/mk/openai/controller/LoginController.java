package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.common.user.LoginUser;
import com.mk.openai.entity.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mk.openai.service.TblUserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
@AllArgsConstructor
public class LoginController {


    private final TblUserService tblUserService;

    @PostMapping("/index")
    public JsonResponse<LoginUser> search(@RequestBody @Valid LoginRequest request) {
        return JsonResponse.<LoginUser>builder().data(tblUserService.login(request)).wrapSuccess().build();
    }
}

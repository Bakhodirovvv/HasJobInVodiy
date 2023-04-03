package com.company.hasjob.controller;

import com.company.hasjob.dto.UsersInfoDto;
import com.company.hasjob.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/userInfo")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<?> giveUsersInfo(@RequestParam String phoneNumber) {
        return userInfoService.getUserInfo(phoneNumber);
    }

    @PostMapping
    public HttpEntity<?> editUserInfo(@RequestBody UsersInfoDto dto, @RequestParam String phoneNumber) {
        return userInfoService.editUserInfo(dto, phoneNumber);
    }
}
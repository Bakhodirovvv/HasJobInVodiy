package com.company.hasjob.controller;

import com.company.hasjob.dto.SignInUserDto;
import com.company.hasjob.dto.UserDto;
import com.company.hasjob.dto.UserSmsDto;
import com.company.hasjob.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final RegisterService registerService;

    @GetMapping("/getAllJob")
    public HttpEntity<?> getAllJobTypes() {
        return registerService.getAllJobTypes();
    }

    @PostMapping("/signUp")
    public HttpEntity<?> signUp(@RequestBody UserDto userDto) {
        return registerService.signUp(userDto);
    }

    @PostMapping("/sendSms")
    public ResponseEntity<?> getSmsCode(@RequestBody UserSmsDto userSmsDto) {
        return registerService.getSmsCode(userSmsDto.getPhoneNumber(), userSmsDto.getRandomCode());
    }

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInUserDto signInUserDto) {
        return registerService.signIn(signInUserDto);
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getMainMenu(@RequestParam String phoneNumber) {
        return registerService.getMainMenu(phoneNumber);
    }


}

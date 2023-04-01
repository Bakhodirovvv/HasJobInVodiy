package com.company.hasjob.controller;

import com.company.hasjob.dto.UserDto;
import com.company.hasjob.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/abdumomin/register")
public class RegisterController {
    private final RegisterService registerService;

    @GetMapping
    public HttpEntity<?> getAllJobTypes() {
        return registerService.getAllJobTypes();
    }

    @PostMapping("/signUp")
    public HttpEntity<?> signUp(@RequestBody UserDto userDto){
        return registerService.signUp(userDto);
    }
}

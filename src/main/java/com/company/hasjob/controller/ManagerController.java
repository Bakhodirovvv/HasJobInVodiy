package com.company.hasjob.controller;

import com.company.hasjob.dto.JobTypeDto;
import com.company.hasjob.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manager")
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping("/manager/users/read")
    public HttpEntity<?> getAllUser() {
        return managerService.getAlL();
    }
    @PostMapping("/manager/addjob")
    public HttpEntity<?> addJob(@RequestBody JobTypeDto jobTypeDto){
        return managerService.addJob(jobTypeDto);
    }
    @GetMapping("manager/jobs/read")
    public HttpEntity<?> getAllJob(){
        return managerService.getAllJobs();
    }
}

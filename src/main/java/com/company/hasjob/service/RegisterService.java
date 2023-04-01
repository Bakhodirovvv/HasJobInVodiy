package com.company.hasjob.service;

import com.company.hasjob.dto.JobTypeDto;
import com.company.hasjob.dto.UserDto;
import com.company.hasjob.entity.JobType;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.JobTypeRepository;
import com.company.hasjob.repository.UsersRepository;
import com.company.hasjob.validator.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class RegisterService {
    private final UsersRepository usersRepository;
    private final JobTypeRepository jobTypeRepository;
    private final ObjectMapper objectMapper;

    public HttpEntity<?> getAllJobTypes() {
        List<JobType> all = jobTypeRepository.findAll();
        List<JobTypeDto> jobTypeDtos = new ArrayList<>();
        JobTypeDto jobTypeDto = null;
        for (JobType jobType : all) {
            jobTypeDto = objectMapper.convertValue(jobType, JobTypeDto.class);
            jobTypeDtos.add(jobTypeDto);
        }
        return ResponseEntity.status(HttpStatus.OK).body(jobTypeDtos);
    }

    public HttpEntity<?> signUp(UserDto userDto) {
        if (!UserValidator.signUp(userDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Try again");
        }
        // phone number or jobType ni database dan tekshirish
        Users userByPhoneNumber = usersRepository.findByPhoneNumber(userDto.phoneNumber());
        System.out.println(userByPhoneNumber);
        if (userByPhoneNumber == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Try again");
        }

        return null;
    }
}

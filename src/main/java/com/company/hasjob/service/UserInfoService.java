package com.company.hasjob.service;

import com.company.hasjob.dto.UsersInfoDto;
import com.company.hasjob.entity.JobType;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.JobTypeRepository;
import com.company.hasjob.repository.UserInfoRepository;
import com.company.hasjob.repository.UsersRepository;
import com.company.hasjob.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UsersRepository usersRepository;
    private final UserInfoRepository userInfoRepository;
    private final JobTypeRepository jobTypeRepository;
    public ResponseEntity<?> getUserInfo(String phoneNumber) {
        phoneNumber = "+998" + phoneNumber;
        Users user = userInfoRepository.findByPhoneNumber(phoneNumber);
        UsersInfoDto usersInfoDto = UsersInfoDto.builder()
                .fio(user.getFio())
                .job(user.getJob().getName())
                .photoUrl(user.getPhotoUrl())
                .experience(user.getExperience())
                .build();
        System.out.println("usersInfoDto = " + usersInfoDto.toString());

        return ResponseEntity.status(HttpStatus.OK).body(usersInfoDto);
    }

    public HttpEntity<?> editUserInfo(UsersInfoDto dto, String phoneNumber) {
        if (!UserValidator.userInfoIsEmpty(dto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" Some Input is empty");
        }
        //jobType ni database dan tekshirish
        JobType jobByName = jobTypeRepository.findByNameAndActiveTrue(dto.getJob());
        if (jobByName == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job type is not found");
        }
        Users userByPhoneNumber = usersRepository.findByPhoneNumber(phoneNumber);
        //Save To DB
        userByPhoneNumber.setFio(dto.getFio());
        userByPhoneNumber.setJob(jobByName);
        userByPhoneNumber.setPhotoUrl(dto.getPhotoUrl());
        userByPhoneNumber.setExperience(dto.getExperience());

        System.out.println("****************************************");
        System.out.println("buildUser = " + userByPhoneNumber);
        System.out.println("****************************************");
        usersRepository.saveAndFlush(userByPhoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body("Successfully Edited");
    }
}
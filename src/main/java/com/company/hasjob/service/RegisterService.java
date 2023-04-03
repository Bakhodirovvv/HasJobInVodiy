package com.company.hasjob.service;

import com.company.hasjob.dto.JobTypeDto;
import com.company.hasjob.dto.SignInUserDto;
import com.company.hasjob.dto.UserDto;
import com.company.hasjob.entity.Ads;
import com.company.hasjob.entity.JobType;
import com.company.hasjob.entity.UserSMS;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.JobTypeRepository;
import com.company.hasjob.repository.UserSMSRepository;
import com.company.hasjob.repository.UsersRepository;
import com.company.hasjob.validator.UserValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor

public class RegisterService {
    private final UsersRepository usersRepository;
    private final JobTypeRepository jobTypeRepository;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final Random random;
    private final UserSMSRepository userSMSRepository;

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
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" Not checked");
        }
        // phone number or jobType ni database dan tekshirish
        Users userByPhoneNumber = usersRepository.findByPhoneNumber(userDto.getPhoneNumber());
        System.out.println(userByPhoneNumber);
        if (userByPhoneNumber != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("PhoneNumber is already exists");
        }
        JobType jobByName = jobTypeRepository.findByName(userDto.getJob());
        if (jobByName == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job type is not found");
        }
        String userPassword = UserValidator.encodeUserPassword(userDto.getPassword());
        Users build = Users.builder()
                .fio(userDto.getFio())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userPassword)
                .job(jobByName)
                .build();
        Users savedUser = usersRepository.saveAndFlush(build);

        Ads ads = new Ads();

        UserDto dto = modelMapper.map(savedUser, UserDto.class);
        int code = random.nextInt(899999) + 100000;
        System.out.println(" \n ++++++++++++++++++++++++++ \n " + code + "  ++++++++++++++++++++++++++ \n");
        Optional<UserSMS> byUsers = userSMSRepository.findByUsers(savedUser);
        if (byUsers.isPresent()){
            // userSMS ni codeni update qilindi
            UserSMS userSMS = byUsers.get();
            userSMS.setVerified(false);
            userSMS.setRandomCode(code);
            userSMSRepository.save(userSMS);
        } else {
            // userSMS ni code bilan user endi table ga saqlandi
            UserSMS userSMS = UserSMS.builder()
                    .users(savedUser)
                    .randomCode(code)
                    .build();
            userSMSRepository.save(userSMS);
        }
        /*
            code yuborish logic shu yerga yozilishi kerak
        */
        return ResponseEntity.ok(dto.getPhoneNumber());
    }

    public ResponseEntity<?> getSmsCode(String phoneNumber, Integer randomCode) {
        Users user = usersRepository.findByPhoneNumber(phoneNumber);
        //  findByUsersAndRandomCodeAndVerifiedFalse ushbu method true holatidan foydalanishimiz kerak
        UserSMS userSMS = userSMSRepository.findByUsersAndRandomCodeAndVerifiedFalse(user, randomCode);
        Users users = userSMS.getUsers();

        return ResponseEntity.ok(" Royxatdan o'tib bolding " + users.getStatus());
    }

    public ResponseEntity<?> signIn(SignInUserDto signInUserDto) {
        Users user = usersRepository.findByPhoneNumber(signInUserDto.getPhoneNumber());
        if (!UserValidator.validatePasswordDB(signInUserDto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password is invalid");
        }
        return ResponseEntity.ok(user.getStatus());
    }
}

package com.company.hasjob.service;

import com.company.hasjob.dto.*;
import com.company.hasjob.entity.Ads;
import com.company.hasjob.entity.JobType;
import com.company.hasjob.entity.UserSMS;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.AdsRepository;
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

import java.util.*;

@Service
@RequiredArgsConstructor

public class RegisterService {
    private final UsersRepository usersRepository;
    private final JobTypeRepository jobTypeRepository;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final Random random;
    private final UserSMSRepository userSMSRepository;
    private final AdsRepository adsRepository;

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
        return ResponseEntity.ok(users.getFio());
    }

    public ResponseEntity<?> signIn(SignInUserDto signInUserDto) {
        Users user = usersRepository.findByPhoneNumber(signInUserDto.getPhoneNumber());
        if (!UserValidator.validatePasswordDB(signInUserDto.getPassword(), user.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Password is invalid");
        }
        return ResponseEntity.ok(user.getFio());
    }

    public ResponseEntity<?> getMainMenu(String phoneNumber) {
        Users user = usersRepository.findByPhoneNumber(phoneNumber);
        JobType jobType = jobTypeRepository.findByName("Ish beruvchi");
        if (user == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User not found");
        }
        if (user.getJob().equals(jobType)){
            List<Users> allByJob = usersRepository.findAllByJob(jobType);
            List<ResponseUserDto> responseUserDtos = new ArrayList<>();
            for (Users users : allByJob) {
                ResponseUserDto build = ResponseUserDto.builder()
                        .photoUrl(users.getPhotoUrl())
                        .fio(users.getFio())
                        .experience(users.getExperience())
                        .jobName(users.getJob().getName())
                        .phoneNumber(users.getPhoneNumber())
                        .rate(users.getRate())
                        .build();
                responseUserDtos.add(build);
            }
            return ResponseEntity.ok(responseUserDtos);
        } else {
            List<Ads> allByActiveTrue = adsRepository.findAllByActiveTrue();
            List<ResponseAdsDto> responseAdsDtos = new ArrayList<>();
            for (Ads ads : allByActiveTrue) {
                ResponseAdsDto build = ResponseAdsDto.builder()
                        .title(ads.getTitle())
                        .userName(ads.getUser().getFio())
                        .longitude(ads.getLongitude())
                        .address(ads.getAddress())
                        .price(ads.getPrice())
                        .description(ads.getDescription())
                        .latitude(ads.getLatitude())
                        .build();
                responseAdsDtos.add(build);
            }
            return ResponseEntity.ok(responseAdsDtos);
        }
    }

    public ResponseEntity<?> saveAds(AdsSaveDto adsSaveDto) {
        Optional<Users> byId = usersRepository.findById(adsSaveDto.getUserId());
        if (byId.isPresent()) {
            Ads ads = Ads.builder()
                    .user(byId.get())
                    .longitude(adsSaveDto.getLongitude())
                    .latitude(adsSaveDto.getLatitude())
                    .address(adsSaveDto.getAddress())
                    .price(adsSaveDto.getPrice())
                    .title(adsSaveDto.getTitle())
                    .description(adsSaveDto.getDescription())
                    .build();
            Ads save = adsRepository.save(ads);
            return ResponseEntity.ok("OK");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Don't save");
    }

    public ResponseEntity<?> getEmployeeAdsById(Integer adsId) {
        Optional<Ads> byId = adsRepository.findByAdsId(adsId);
        if (byId.isPresent()){
            Ads ads = byId.get();
            AdsSaveDto build = AdsSaveDto.builder()
                    .address(ads.getAddress())
                    .price(ads.getPrice())
                    .userId(ads.getUser().getId())
                    .title(ads.getTitle())
                    .longitude(ads.getLongitude())
                    .description(ads.getDescription())
                    .latitude(ads.getLatitude())
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(build);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ads is not found");
    }

    public ResponseEntity<?> deleteEmployeeAdsById(Integer adsId) {
        Optional<Ads> byAdsId = adsRepository.findByAdsId(adsId);
        if (byAdsId.isPresent()){
            Ads ads = byAdsId.get();
            ads.setActive(false);
            adsRepository.save(ads);
            return ResponseEntity.ok("Successfully deleted");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ads is not found");
    }
}

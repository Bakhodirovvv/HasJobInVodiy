package com.company.hasjob.service;

import com.company.hasjob.dto.JobTypeDto;
import com.company.hasjob.dto.ResponseManagerDto;
import com.company.hasjob.entity.JobType;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.JobTypeRepository;
import com.company.hasjob.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ManagerService {
    private final UsersRepository usersRepository;
    private final JobTypeRepository jobTypeRepository;

    public HttpEntity<?> getAlL() {
        List<Users> allByStatus = usersRepository.findAllByStatus();
        List<ResponseManagerDto> dto = new ArrayList<>();
        ResponseManagerDto build;
        for (Users byStatus : allByStatus) {
            build = ResponseManagerDto.builder()
                    .fio(byStatus.getFio())
                    .phoneNumber(byStatus.getPhoneNumber())
                    .photoUrl(byStatus.getPhotoUrl())
                    .job(byStatus.getJob().getName())
                    .rate(byStatus.getRate())
                    .build();
            dto.add(build);
        }
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    public HttpEntity<?> addJob(JobTypeDto jobTypeDto) {
        JobType build = JobType.builder()
                .name(jobTypeDto.getName())
                .build();
        jobTypeRepository.save(build);
        return ResponseEntity.status(HttpStatus.OK).body("successfully save");
    }

    public HttpEntity<?> getAllJobs() {
        List<JobType> allByActiveTrue = jobTypeRepository.getAllByActiveTrue();
        List<JobTypeDto> jobTypeDto = new ArrayList<>();
        JobTypeDto build;
        for (JobType jobType : allByActiveTrue) {
            build = JobTypeDto.builder()
              .name(jobType.getName())
            .build();
            jobTypeDto.add(build);
        }
        return ResponseEntity.status(HttpStatus.OK).body(jobTypeDto);
    }
}

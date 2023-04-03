package com.company.hasjob.service;

import com.company.hasjob.dto.ResponseManagerDto;
import com.company.hasjob.entity.Users;
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
}

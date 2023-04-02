package com.company.hasjob.repository;

import com.company.hasjob.entity.UserSMS;
import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSMSRepository extends JpaRepository<UserSMS, Integer> {

    Optional<UserSMS> findByUsers(Users users);
    UserSMS findByUsersAndRandomCodeAndVerifiedFalse(Users users, Integer randomCode);
    UserSMS findByUsersAndRandomCodeAndVerifiedTrue(Users users, Integer randomCode);

}

package com.company.hasjob.repository;


import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserInfoRepository extends JpaRepository<Users, Integer> {
    @Query("select u from Users u where u.phoneNumber like concat('%', ?1)")
    Users findByPhoneNumber(String phoneNumber);
//    Users findByPhoneNumber(String phoneNumber);
}
package com.company.hasjob.repository;

import com.company.hasjob.entity.Users;
import com.google.common.base.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByPhoneNumber(String phoneNumber);
    @Query("select u from Users u where u.status='ACTIVE' and u.authRole='USER' ")
    List<Users> findAllByStatus();




}

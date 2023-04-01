package com.company.hasjob.repository;

import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    Users findByPhoneNumber(String phoneNumber);

}

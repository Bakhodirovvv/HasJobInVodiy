package com.company.hasjob.repository;

import com.company.hasjob.entity.JobType;
import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    @Query("select u from Users u where u.phoneNumber = ?1 and u.status='ACTIVE'")
    Users findByPhoneNumber(String phoneNumber);
    @Query("select u from Users u where u.status='ACTIVE' and u.authRole='USER' ")
    List<Users> findAllByStatus();

    @Query("select u from Users u where u.status='ACTIVE' and u.authRole='USER' and u.job <> ?1")
    List<Users> findAllByJob(JobType jobType);

}

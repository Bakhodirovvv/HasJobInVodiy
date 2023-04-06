package com.company.hasjob.repository;

import com.company.hasjob.dto.JobTypeDto;
import com.company.hasjob.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobTypeRepository extends JpaRepository<JobType, Integer> {
    @Query("select j from JobType j where j.name = ?1 and j.isActive = true")
    JobType findByNameAndActiveTrue(String name);
    @Query("select j from JobType j where j.isActive = true")
    List<JobType> getAllByActiveTrue();
    @Query("select j from JobType j where j.name = ?1")
    JobType updateJobTypeByName(String name);
}

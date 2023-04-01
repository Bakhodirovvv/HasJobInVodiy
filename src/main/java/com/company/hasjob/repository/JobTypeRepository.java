package com.company.hasjob.repository;

import com.company.hasjob.entity.JobType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTypeRepository extends JpaRepository<JobType, Integer> {
}

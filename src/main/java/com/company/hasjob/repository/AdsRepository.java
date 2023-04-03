package com.company.hasjob.repository;

import com.company.hasjob.entity.Ads;
import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdsRepository extends JpaRepository<Ads, Integer> {
    Users getAllByActive();
}

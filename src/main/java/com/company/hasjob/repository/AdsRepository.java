package com.company.hasjob.repository;

import com.company.hasjob.entity.Ads;
import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdsRepository extends JpaRepository<Ads, Integer> {

    @Query("select a from Ads a where a.isActive = true")
    List<Ads> findAllByActiveTrue();

    Users getAllByActive();
}

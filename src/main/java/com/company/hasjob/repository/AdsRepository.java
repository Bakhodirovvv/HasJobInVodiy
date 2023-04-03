package com.company.hasjob.repository;

import com.company.hasjob.entity.Ads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<Ads, Integer> {

    @Query("select a from Ads a where a.isActive = true")
    List<Ads> findAllByActiveTrue();

    @Query("select a from Ads a where a.isActive = true and a.id = ?1")
    Optional<Ads> findByAdsId(Integer id);
}

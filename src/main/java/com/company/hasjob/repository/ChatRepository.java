package com.company.hasjob.repository;

import com.company.hasjob.entity.Chat;
import com.company.hasjob.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("select c from Chat c where c.isActive = true and c.fromUserId = ?1 ")
    List<Chat> findAllByActiveTrueAndFromUserId(Users fromUserId);

}
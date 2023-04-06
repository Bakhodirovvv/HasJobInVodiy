package com.company.hasjob.repository;

import com.company.hasjob.entity.Chat;
import com.company.hasjob.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("select m from Message m where m.isActive = true and m.chat = ?1 order by m.localDateTime asc ")
    List<Message> findAllByActiveTrueAndChat(Chat chat);

}
package com.company.hasjob.service;

import com.company.hasjob.dto.MessageSendDto;
import com.company.hasjob.entity.Chat;
import com.company.hasjob.entity.Message;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.ChatRepository;
import com.company.hasjob.repository.MessageRepository;
import com.company.hasjob.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UsersRepository usersRepository;
    private final ChatRepository chatRepository;


    public ResponseEntity<?> messageSave(MessageSendDto messageSendDto) {
        Optional<Chat> byId = chatRepository.findById(messageSendDto.getChatId());
        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(" This Chat is not found");
        }
        Chat chat = byId.get();
        Users user = usersRepository.findByPhoneNumber(messageSendDto.getUserPhoneNumber());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This User is not found");
        }
        if (!chat.getToUserId().equals(user) || !chat.getFromUserId().equals(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bu User ushbu Chat ga mos kelmadi");
        }
        Message message = Message.builder()
                .chat(chat)
                .users(user)
                .text(messageSendDto.getText())
                .build();
        if (messageSendDto.getLocalDateTime() != null) {
            message.setLocalDateTime(messageSendDto.getLocalDateTime());
        }
        Message save = messageRepository.save(message);
        return getMessageSendDtoList(chat);
    }

    public ResponseEntity<?> getMessageSendDtoList(Chat chat) {
        List<Message> priviousMessages = messageRepository.findAllByActiveTrueAndChat(chat);
        List<MessageSendDto> sendDtos = new ArrayList<>();
        MessageSendDto dto;
        for (Message key : priviousMessages) {
            dto = MessageSendDto.builder()
                    .text(key.getText())
                    .chatId(key.getChat().getId())
                    .userPhoneNumber(key.getUsers().getPhoneNumber())
                    .localDateTime(key.getLocalDateTime())
                    .build();
            sendDtos.add(dto);
        }
        return ResponseEntity.ok(sendDtos);
    }

}

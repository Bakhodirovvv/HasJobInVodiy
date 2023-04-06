package com.company.hasjob.service;

import com.company.hasjob.dto.MessageSendDto;
import com.company.hasjob.dto.OneChatDto;
import com.company.hasjob.dto.ResponseUserDto;
import com.company.hasjob.entity.Chat;
import com.company.hasjob.entity.Message;
import com.company.hasjob.entity.Users;
import com.company.hasjob.repository.ChatRepository;
import com.company.hasjob.repository.MessageRepository;
import com.company.hasjob.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UsersRepository usersRepository;

    public ResponseEntity<?> getAllChat(String phoneNumber) {
        Users byPhoneNumber = usersRepository.findByPhoneNumber(phoneNumber);
        List<Chat> all = chatRepository.findAllByActiveTrueAndFromUserId(byPhoneNumber);
        List<ResponseUserDto> responses = new ArrayList<>();
        Users toUser;
        for (Chat chat : all) {
            toUser = chat.getToUserId();
            ResponseUserDto dto = ResponseUserDto.builder()
                    .fio(toUser.getFio())
                    .rate(toUser.getRate())
                    .jobName(toUser.getJob().getName())
                    .experience(toUser.getExperience())
                    .phoneNumber(toUser.getPhoneNumber())
                    .photoUrl(toUser.getPhotoUrl())
                    .build();
            responses.add(dto);
        }
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<?> getOneChat(OneChatDto oneChatDto) {
        Users byPhoneNumber = usersRepository.findByPhoneNumber(oneChatDto.getFromPhoneNumber());
        List<Chat> all = chatRepository.findAllByActiveTrueAndFromUserId(byPhoneNumber);
        Users toUser;
        Users toUserId = usersRepository.findByPhoneNumber(oneChatDto.getToPhoneNumber());
        ResponseUserDto dto = null;
        for (Chat chat : all) {
            if (Objects.equals(chat.getToUserId(), toUserId)){
                toUser = chat.getToUserId();
                dto = ResponseUserDto.builder()
                        .fio(toUser.getFio())
                        .rate(toUser.getRate())
                        .jobName(toUser.getJob().getName())
                        .experience(toUser.getExperience())
                        .phoneNumber(toUser.getPhoneNumber())
                        .photoUrl(toUser.getPhotoUrl())
                        .build();
                break;
            }
        }
        return ResponseEntity.ok(dto);
    }
    public ResponseEntity<?> createChat(OneChatDto oneChatDto) {
        Users fromUser = usersRepository.findByPhoneNumber(oneChatDto.getFromPhoneNumber());
        Users toUser = usersRepository.findByPhoneNumber(oneChatDto.getToPhoneNumber());
        Chat priviousChat = chatRepository.findByActiveTrueAndFromUserIdAndToUserId(fromUser, toUser);
        if (priviousChat != null){
            List<Message> priviousMessages = messageRepository.findAllByActiveTrueAndChat(priviousChat);
            List<MessageSendDto> sendDtos = new ArrayList<>();
            MessageSendDto dto;
            for (Message message : priviousMessages) {
                dto = MessageSendDto.builder()
                        .text(message.getText())
                        .chatId(message.getChat().getId())
                        .userPhoneNumber(message.getUsers().getPhoneNumber())
                        .localDateTime(message.getLocalDateTime())
                        .build();
                sendDtos.add(dto);
            }
            return ResponseEntity.ok(sendDtos);
        }

        Chat chat = Chat.builder()
                .fromUserId(fromUser)
                .toUserId(toUser)
                .build();
        Chat save = chatRepository.save(chat);
        return getOneChat(oneChatDto);
    }

    public Chat getChatForMessage(OneChatDto oneChatDto) {
        Users fromUser = usersRepository.findByPhoneNumber(oneChatDto.getFromPhoneNumber());
        Users toUser = usersRepository.findByPhoneNumber(oneChatDto.getToPhoneNumber());
        return chatRepository.findByActiveTrueAndFromUserIdAndToUserId(fromUser, toUser);
    }
}

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
import java.util.Optional;

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
            ResponseUserDto build = ResponseUserDto.builder()
                    .fio(toUser.getFio())
                    .rate(toUser.getRate())
                    .jobName(toUser.getJob().getName())
                    .experience(toUser.getExperience())
                    .phoneNumber(toUser.getPhoneNumber())
                    .photoUrl(toUser.getPhotoUrl())
                    .build();
            responses.add(build);
        }
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<?> getOneChat(OneChatDto oneChatDto) {
        Users byPhoneNumber = usersRepository.findByPhoneNumber(oneChatDto.getFromPhoneNumber());
        List<Chat> all = chatRepository.findAllByActiveTrueAndFromUserId(byPhoneNumber);
        Users toUser;
        Users toUserId = usersRepository.findByPhoneNumber(oneChatDto.getToPhoneNumber());
        ResponseUserDto build = null;
        for (Chat chat : all) {
            if (Objects.equals(chat.getToUserId(), toUserId)){
                toUser = chat.getToUserId();
                build = ResponseUserDto.builder()
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
        return ResponseEntity.ok(build);
    }


    public ResponseEntity<?> createChat(OneChatDto oneChatDto) {
        Users fromUser = usersRepository.findByPhoneNumber(oneChatDto.getFromPhoneNumber());
        Users toUser = usersRepository.findByPhoneNumber(oneChatDto.getToPhoneNumber());
        Chat chat = Chat.builder()
                .fromUserId(fromUser)
                .toUserId(toUser)
                .build();
        Chat save = chatRepository.save(chat);
        List<Message> messageList = messageRepository.findAllByActiveTrueAndChat(save);
        List<MessageSendDto> sendDtos = new ArrayList<>();
        MessageSendDto build;
        for (Message message : messageList) {
            build = MessageSendDto.builder()
                    .text(message.getText())
                    .phoneNumber(message.getUsers().getPhoneNumber())
                    .localDateTime(message.getLocalDateTime())
                    .build();
            sendDtos.add(build);
        }
        return ResponseEntity.ok(sendDtos);
    }
}

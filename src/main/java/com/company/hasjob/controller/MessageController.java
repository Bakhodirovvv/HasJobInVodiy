package com.company.hasjob.controller;

import com.company.hasjob.dto.MessageSendDto;
import com.company.hasjob.dto.OneChatDto;
import com.company.hasjob.entity.Chat;
import com.company.hasjob.service.ChatService;
import com.company.hasjob.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;
    private final ChatService chatService;

    @PostMapping("/save")
    public ResponseEntity<?> messageSave(@RequestBody MessageSendDto messageSandDto) {
        return messageService.messageSave(messageSandDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMessage(OneChatDto oneChatDto){
        Chat chat = chatService.getChatForMessage(oneChatDto);
        if (chat == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Chat is not found");
        }
        return messageService.getMessageSendDtoList(chat);
    }
}

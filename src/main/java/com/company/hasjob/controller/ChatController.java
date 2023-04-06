package com.company.hasjob.controller;

import com.company.hasjob.dto.OneChatDto;
import com.company.hasjob.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllChat(@RequestParam String phoneNumber){
        return chatService.getAllChat(phoneNumber);
    }

    @PostMapping("/getOneChat")
    public HttpEntity<?> getOneChat(@RequestBody OneChatDto oneChatDto){
        return chatService.getOneChat(oneChatDto);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChat(@RequestBody OneChatDto oneChatDto ){
        return chatService.createChat(oneChatDto);
    }

}

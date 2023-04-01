package com.company.hasjob.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
       Message message=new Message();
       message.setId(UUID.randomUUID());
        System.out.println("message = " + message);
        System.out.println(LocalDateTime.now().plusMonths(9).format(DateTimeFormatter.ofPattern("MMM dd, HH:mm")));
    }
}

package com.company.hasjob.validator;

import com.company.hasjob.dto.UserDto;

import java.util.Base64;

public class UserValidator {

    public static boolean signUp(UserDto userDto) {
        if (userDto.getFio() == null || userDto.getJob() == null) {
            return false;
        }
        if (!userDto.getPassword().equals(userDto.getPrePassword())) {
            return false;
        }
        if (!userDto.getPhoneNumber().matches("(\\d{2})([- ])?(\\d{3})([- ])?(\\d{2})([- ])?(\\d{2})$")) {
            return false;
        }
        return userDto.getPassword().length() == 8;
    }

    public static String encodeUserPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static boolean validatePasswordDB(String password, String encodeDbPassword) {
        return encodeUserPassword(password).equals(encodeDbPassword);
    }
}

package com.company.hasjob.validator;

import com.company.hasjob.dto.UserDto;

public class UserValidator {

    public static boolean signUp(UserDto userDto){
        if (userDto.fio() == null || userDto.job() == null) {
            return false;
        }
        if (!userDto.password().equals(userDto.prePassword())) {
            return false;
        }
        if (!userDto.phoneNumber().matches("^\\+998([- ])?(\\d{2})([- ])?(\\d{3})([- ])?(\\d{2})([- ])?(\\d{2})$")) {
            return false;
        }
        return userDto.password().length() == 8;
    }

}

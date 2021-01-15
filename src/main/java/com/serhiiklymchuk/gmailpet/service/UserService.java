package com.serhiiklymchuk.gmailpet.service;

import com.serhiiklymchuk.gmailpet.domain.User;
import com.serhiiklymchuk.gmailpet.dto.UserDto;

public interface UserService {
    void createUser(UserDto userDto);
    User findByUsername(String username);
}

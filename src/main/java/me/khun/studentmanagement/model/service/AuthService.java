package me.khun.studentmanagement.model.service;

import me.khun.studentmanagement.model.dto.UserDto;

public interface AuthService {

    public UserDto login(String email, String password);

}
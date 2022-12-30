package me.khun.studentmanagement.model.service;

import java.util.List;

import me.khun.studentmanagement.model.dto.UserDto;
import me.khun.studentmanagement.model.entity.User;

public interface UserService extends Service {

    public UserDto save(User user);
    
    public boolean approveById(String id);

    public boolean deleteById(User operator, String id);

    public UserDto findById(String id);
    
    public UserDto findByEmail(String email);

    public List<UserDto> findAll();

    public List<UserDto> search(String keyword);
    
    public List<UserDto> search(String keyword, boolean approved);

    public long getCount();
    
    public long getCount(boolean approved);

}
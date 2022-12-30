package me.khun.studentmanagement.model.repo;

import java.util.List;

import me.khun.studentmanagement.model.entity.User;

public interface UserRepo extends BaseRepo<User, String> {

    public List<User> search(String keyword);
    
    public List<User> search(String keyword, boolean approved);

    public User findByEmailAndPassword(String email, String password);
    
    public User findByEmail(String email);
    
    public long getCount(boolean approved);

}
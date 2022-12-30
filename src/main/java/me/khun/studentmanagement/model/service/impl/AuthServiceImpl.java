package me.khun.studentmanagement.model.service.impl;

import me.khun.studentmanagement.model.dto.UserDto;
import me.khun.studentmanagement.model.repo.UserRepo;
import me.khun.studentmanagement.model.service.AuthService;
import me.khun.studentmanagement.model.service.exception.ServiceException;

public class AuthServiceImpl implements AuthService {
	
	private UserRepo userRepo;
	
	public AuthServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDto login(String email, String password) {
		email = email == null ? "" : email;
		password = password == null ? "" : password;
		var user =  userRepo.findByEmailAndPassword(email, password);
		if (user == null) {
			return null;
		}
		
		if (!user.isApproved()) {
			throw new ServiceException("You have not been approved.");
		}
		return UserDto.of(user);
	}

}

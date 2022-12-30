
package me.khun.studentmanagement.model.service.impl;

import java.util.List;
import java.util.Objects;

import me.khun.studentmanagement.model.dto.UserDto;
import me.khun.studentmanagement.model.entity.User;
import me.khun.studentmanagement.model.repo.UserRepo;
import me.khun.studentmanagement.model.repo.exception.DataAccessException;
import me.khun.studentmanagement.model.service.UserService;
import me.khun.studentmanagement.model.service.exception.InvalidFieldException;
import me.khun.studentmanagement.model.service.exception.ServiceException;

public class UserServiceImpl implements UserService {
	
	private UserRepo userRepo;
	
	public UserServiceImpl(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
    public UserDto save(User user) {
		try {
			if (user.getId() == null || user.getId().isBlank()) {
				var created = userRepo.create(user);
	        	return UserDto.of(created);
	        }
			userRepo.update(user);
			return UserDto.of(user);
		} catch (DataAccessException e) {
			throw new InvalidFieldException("email", "duplicated","A user with this email already exists.");
		}
    }
	
	@Override
	public boolean approveById(String id) {
		var user = userRepo.findById(id);
		user.setApproved(true);
		return userRepo.update(user);
	}

	@Override
    public boolean deleteById(User operator, String id) {
		
		if (Objects.equals(operator.getId(), id)) {
			throw new ServiceException("You cannot delete this user.");
		}
		
		if (id == null || id.isBlank()) {
			throw new ServiceException("Invalid user id");
		}
		
        try {
        	return userRepo.deleteById(id);
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not delete this user.");
        }
    }

	@Override
    public UserDto findById(String id) {
		if (id == null || id.isBlank()) {
			return null;
		}
        
		try {
			var entity = userRepo.findById(id);
			if (entity == null) {
				return null;
			}
			return UserDto.of(entity);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the user.");
		}
    }
	
	@Override
	public UserDto findByEmail(String email) {
		if (email == null || email.isBlank()) {
			return null;
		}
        
		try {
			var entity = userRepo.findByEmail(email);
			if (entity == null) {
				return null;
			}
			return UserDto.of(entity);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the user.");
		}
	}

	@Override
    public List<UserDto> findAll() {
		try {
			return UserDto.ofList(userRepo.findAll());
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find the users.");
		}
    }

	@Override
    public List<UserDto> search(String keyword) {
		keyword = keyword == null ? "" : keyword;
        try {
        	return UserDto.ofList(userRepo.search(keyword));
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not search the users.");
        }
    }
	
	@Override
	public List<UserDto> search(String keyword, boolean approved) {
		keyword = keyword == null ? "" : keyword;
        try {
        	return UserDto.ofList(userRepo.search(keyword, approved));
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not search the users.");
        }
	}

	@Override
    public long getCount() {
        try {
        	return userRepo.getCount();
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not get the user count.");
        }
    }
	
	@Override
	public long getCount(boolean approved) {
		try {
        	return userRepo.getCount(approved);
        } catch (DataAccessException e) {
        	throw new ServiceException("Could not get the user count.");
        }
	}

}
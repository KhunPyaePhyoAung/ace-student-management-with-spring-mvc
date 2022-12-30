package me.khun.studentmanagement.security;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import me.khun.studentmanagement.model.dto.UserDto;

public class LoginInfoDto {
	
	private UserDto user;
	private LocalDateTime loginnedInDateTime;

	public static LoginInfoDto of(LoginInfo loginInfo) {
		if (loginInfo == null) {
			return null;
		}
		var dto = new LoginInfoDto();
		dto.setUser(UserDto.of(loginInfo.getUser()));
		dto.setLoginnedInDateTime(loginInfo.getLoginnedInDateTime());
		return dto;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public LocalDateTime getLoginnedInDateTime() {
		return loginnedInDateTime;
	}

	public void setLoginnedInDateTime(LocalDateTime loginnedInDateTime) {
		this.loginnedInDateTime = loginnedInDateTime;
	}
	
	public String getLoggedInDateTimeStirng() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(loginnedInDateTime);
	}
	
}

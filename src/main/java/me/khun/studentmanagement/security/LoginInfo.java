package me.khun.studentmanagement.security;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import me.khun.studentmanagement.model.entity.User;

public class LoginInfo {
	private User user;
	private LocalDateTime loginnedInDateTime;

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getLoginnedInDateTime() {
		return loginnedInDateTime;
	}

	public void setLoggedInDateTime(LocalDateTime loginnedInDateTime) {
		this.loginnedInDateTime = loginnedInDateTime;
	}
	
	public String getLoggedInDateTimeStirng() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(loginnedInDateTime);
	}

}

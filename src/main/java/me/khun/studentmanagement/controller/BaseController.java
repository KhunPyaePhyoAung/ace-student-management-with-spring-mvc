package me.khun.studentmanagement.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import me.khun.studentmanagement.model.service.UserService;
import me.khun.studentmanagement.security.LoginInfo;
import me.khun.studentmanagement.security.LoginInfoDto;

@ControllerAdvice
public class BaseController {

	@Autowired
	private UserService userService;
	
	@ModelAttribute("userRequestCount")
	public Long userRequestCount() {
		
		return userService.getCount(false);
	}
	
	@ModelAttribute("loginUser")
	public LoginInfoDto authenticatedUser(HttpServletRequest req) {
		var session = (HttpSession) req.getSession(false);
		var loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		return LoginInfoDto.of(loginInfo);
	}

}

package me.khun.studentmanagement.security;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import me.khun.studentmanagement.model.dto.UserDto;
import me.khun.studentmanagement.model.service.UserService;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private UserService userService;
	
	public CustomAuthenticationSuccessHandler(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		var session = (HttpSession) request.getSession(true);
		var loginInfo = new LoginInfo();
		var user = UserDto.parse(userService.findByEmail(authentication.getName()));
		loginInfo.setUser(user);
		loginInfo.setLoggedInDateTime(LocalDateTime.now());
		session.setAttribute("loginInfo", loginInfo);
		response.sendRedirect(request.getServletContext().getContextPath() + "/");
	}

}

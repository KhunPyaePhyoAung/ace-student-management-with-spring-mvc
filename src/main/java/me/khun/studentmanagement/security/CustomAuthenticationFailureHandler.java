package me.khun.studentmanagement.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		var message = "";
		
		if (exception instanceof BadCredentialsException) {
			message = "Invalid email or password";
		} else if (exception instanceof DisabledException) {
			message = "You have not been approved";
		}
		
		request.setAttribute("loginFormErrorMessage", message);
		request.getRequestDispatcher("/views/login.jsp").forward(request, response);
	}

}

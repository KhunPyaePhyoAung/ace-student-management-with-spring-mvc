package me.khun.studentmanagement.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.khun.studentmanagement.model.entity.User;
import me.khun.studentmanagement.model.entity.User.Role;
import me.khun.studentmanagement.model.service.UserService;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String showSignUpPage(ModelMap model) {
		var user = (User) model.getAttribute("user");
		user = user == null ? new User() : user;
		user.setRole(Role.USER);
		model.put("user", user);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(
			@Validated
			@ModelAttribute("user")
			User user,
			BindingResult bindingResult,
			RedirectAttributes redirect
			) {
		if (!Objects.equals(user.getPassword(), user.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "notMatch");
		}
		
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
			redirect.addFlashAttribute("user", user);
			return "redirect:/signup";
		}
		
		userService.save(user);
		return "redirect:/login";
	}
	
	@GetMapping("/unauthorized")
	public String unauthorized(ModelMap model) {
		model.put("title", "Unauthorized");
		model.put("message", "You are not authorized.");
		return "error-page";
	}
}

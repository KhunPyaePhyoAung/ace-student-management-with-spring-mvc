
package me.khun.studentmanagement.controller;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.khun.studentmanagement.model.dto.UserDto;
import me.khun.studentmanagement.model.entity.User;
import me.khun.studentmanagement.model.entity.User.Role;
import me.khun.studentmanagement.model.service.UserService;
import me.khun.studentmanagement.model.service.exception.InvalidFieldException;
import me.khun.studentmanagement.model.service.exception.ServiceException;
import me.khun.studentmanagement.security.LoginInfo;
import me.khun.studentmanagement.view.Alert;
import me.khun.studentmanagement.view.Alert.Type;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authManager;

	@GetMapping("/user/user/search")
	public String searchUsers(
			@RequestParam(required = false)
			String keyword,
			ModelMap model
			) {
		var users = userService.search(keyword, true);
		model.put("users", users);
		model.put("title", "Users");
		return "users";
	}
	
	@GetMapping("/admin/user/request/search")
	public String searchUserRequests(
			@RequestParam(required = false)
			String keyword,
			ModelMap model
			) {
		var users = userService.search(keyword, false);
		model.put("users", users);
		model.put("title", "User Requests");
		return "user-requests";
	}
	
	@GetMapping("/admin/user/edit")
	public String editUser(
			@RequestParam(required = false)
			String id,
			ModelMap model
			) {
		var user = (User) model.getAttribute("user");
		user = user == null ? UserDto.parse(userService.findById(id)) : user;
		user = user == null ? new User() : user;
		
		if (user.getId() != null && StringUtils.hasLength(user.getId()) && !user.isApproved()) {
			model.put("title", "Unauthorized");
			model.put("message", "Cannot update unapproved user.");
			return "error-page";
		}
		
		model.put("user", user);
		model.put("title", (user == null || !StringUtils.hasLength(user.getId())) ? "Add New User" : "Edit User");
		return "edit-user";
	}
	
	@PostMapping("/admin/user/save")
	public String saveUser(
			@Validated
			@ModelAttribute("user")
			User user,
			BindingResult bindingResult,
			ModelMap model,
			HttpSession session,
			RedirectAttributes redirect
			) {
		
		if (!Objects.equals(user.getPassword(), user.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "notMatch");
		}
	
		if (!bindingResult.hasErrors()) {
			try {
				var savedUser = userService.save(user);
				var operator = ((LoginInfo) session.getAttribute("loginInfo")).getUser();
				if (Objects.equals(operator.getId(), savedUser.getId())) {
					var token = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
					var auth = authManager.authenticate(token);
					var securityContext = SecurityContextHolder.getContext();
					securityContext.setAuthentication(auth);
					session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
					var loginInfo = new LoginInfo();
					loginInfo.setUser(UserDto.parse(savedUser));
					loginInfo.setLoggedInDateTime(LocalDateTime.now());
					session.setAttribute("loginInfo", loginInfo);
				}
				var isNewUser = !Objects.equals(user.getId(), savedUser.getId());
				var message = isNewUser
								? "Successfully created a new user."
								: "Successfully updated the user.";
				var type = isNewUser ? Type.SUCCESS : Type.INFO;
				redirect.addFlashAttribute("alert", new Alert(message, type));
			} catch (InvalidFieldException e) {
				bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getDefaultMessage());
			}
		}
		
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
			redirect.addFlashAttribute("user", user);
			return "redirect:/admin/user/edit";
		}
		
		return "redirect:/user/user/search";
	}
	
	@GetMapping("/user/user/detail")
	public String showUserDetail(
			@RequestParam
			String id,
			ModelMap model,
			HttpSession session
			) {
		var loginInfo = (LoginInfo) session.getAttribute("loginInfo");
		var user = userService.findById(id);
		
		if (loginInfo.getUser().getRole() == Role.USER && !user.isApproved()) {
			model.put("title", "Unauthorized");
			model.put("message", "You are not authorized.");
			return "error-page";
		}
		
		model.put("user", user);
		model.put("title", "User Detail");
		return "user-detail";
	}
	
	@PostMapping("/admin/user/delete")
	public String deleteUser(
			@RequestParam(required = false)
			String id,
			@RequestParam(required = false, defaultValue = "true")
			Boolean approved,
			HttpSession session,
			RedirectAttributes redirect
			) {
		try {
			var operator = ((LoginInfo) session.getAttribute("loginInfo")).getUser();
			userService.deleteById(operator, id);
			var message = approved ? "Successfully deleted the user." : "Successfully removed the user.";
			redirect.addFlashAttribute("alert", new Alert(message, Type.ERROR));
		} catch (ServiceException e) {
			redirect.addFlashAttribute("alert", new Alert(e.getMessage(), Type.ERROR));
		}
		return approved ? "redirect:/user/user/search" : "redirect:/admin/user/request/search";
	}
	
	@PostMapping("/admin/user/approve")
	public String approveUser(
			@RequestParam
			String id,
			RedirectAttributes redirect
			) {
		userService.approveById(id);
		redirect.addFlashAttribute("alert", new Alert("Successfully approved the user.", Type.SUCCESS));
		return "redirect:/admin/user/request/search";
	}
	
}

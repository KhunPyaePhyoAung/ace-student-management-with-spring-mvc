package me.khun.studentmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import me.khun.studentmanagement.model.service.CourseService;
import me.khun.studentmanagement.model.service.StudentService;
import me.khun.studentmanagement.model.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/")
	public String home(ModelMap model) {
		model.put("userCount", userService.getCount(true));
		model.put("courseCount", courseService.getCount());
		model.put("studentCount", studentService.getCount());
		model.put("title", "Home");
		return "home";
	}
}

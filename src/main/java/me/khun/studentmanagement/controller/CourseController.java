package me.khun.studentmanagement.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import me.khun.studentmanagement.model.dto.CourseDto;
import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.model.service.CourseService;
import me.khun.studentmanagement.model.service.exception.InvalidFieldException;
import me.khun.studentmanagement.model.service.exception.ServiceException;
import me.khun.studentmanagement.tool.JasperExporter;
import me.khun.studentmanagement.view.Alert;
import me.khun.studentmanagement.view.Alert.Type;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;

	@Autowired
	private JasperExporter jasperExporter;
	
	@GetMapping("/user/course/search")
	public String searchCourses(
			@RequestParam(required = false)
			String keyword,
			ModelMap model
			) {
		var courses = courseService.search(keyword);
		model.addAttribute("courses", courses);
		model.addAttribute("title", "Courses");
		return "courses";
	}
	
	@GetMapping("/admin/course/edit")
	public String editCourse(
			@RequestParam(required = false)
			String id,
			ModelMap model) {
		var course = (Course) model.getAttribute("course");
		course = course == null ? CourseDto.parse(courseService.findById(id)) : course;
		course = course == null ? new Course() : course;
		model.put("course", course);
		model.put("title", (course == null || !StringUtils.hasLength(course.getId())) ? "Add New Course" : "Edit Course");
		return "edit-course";
	}
	
	@PostMapping("/admin/course/save")
	public String saveCourse(
			@Validated
			@ModelAttribute("course")
			Course course,
			BindingResult bindingResult,
			RedirectAttributes redirect
			) {
		if (!bindingResult.hasErrors()) {
			try {
				var savedCourse = courseService.save(course);
				var isNewCourse = !Objects.equals(course.getId(), savedCourse.getId());
				var message = isNewCourse
								? "Successfully created a new course."
								: "Successfully updated the course.";
				var type = isNewCourse ? Type.SUCCESS : Type.INFO;
				redirect.addFlashAttribute("alert", new Alert(message, type));
			} catch (InvalidFieldException e) {
				bindingResult.rejectValue(e.getFieldName(), e.getErrorCode(), e.getDefaultMessage());
			}
		}
		
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.course", bindingResult);
			redirect.addFlashAttribute("course", course);
			return "redirect:/admin/course/edit";
		}
		
		return "redirect:/user/course/search";
	}
	
	@GetMapping("/admin/course/export")
	public String exportCourses (
			@RequestParam(required = false)
			String keyword,
			@RequestParam
			String extension,
			ModelMap model,
			HttpServletRequest req,
			HttpServletResponse resp) {
		var courses = courseService.search(keyword);
		
		if (courses.isEmpty()) {
			model.put("alert", new Alert("There is no course to export.", Type.ERROR));
			return searchCourses(keyword, model);
		}
		
		courses.add(0, new CourseDto());
		
		var jasperPath = req.getServletContext().getRealPath("/resources/jasper/course.jrxml");
		
		var fileName = "courses-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
		
		resp.setContentType(JasperExporter.getContentType(extension));
		resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, extension));
		
		var param = new HashMap<String, Object>();
		param.put("title", "Courses");
		
		try {
			var jrDataSource = new JRBeanCollectionDataSource(courses, false);
			param.put("CourseListDataSource", jrDataSource);
			var report = JasperCompileManager.compileReport(jasperPath);
			var print = JasperFillManager.fillReport(report, param, jrDataSource);
			jasperExporter.export(print, extension, resp.getOutputStream());
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@PostMapping("/admin/course/delete")
	public String deleteCourse(
			@RequestParam
			String id,
			RedirectAttributes redirect
			) {
		try {
			courseService.deleteById(id);
			redirect.addFlashAttribute("alert", new Alert("Successfully deleted the course.", Type.ERROR));
		} catch (ServiceException e) {
			redirect.addFlashAttribute("alert", new Alert(e.getMessage(), Type.ERROR));
		}
		return "redirect:/user/course/search";
	}
	
	@GetMapping("/user/course/detail")
	public String showCourseDetail(
			@RequestParam
			String id,
			ModelMap model
			) {
		var course = courseService.findById(id);
		model.put("course", course);
		model.put("title", "Course Detail");
		return "course-detail";
	}
}

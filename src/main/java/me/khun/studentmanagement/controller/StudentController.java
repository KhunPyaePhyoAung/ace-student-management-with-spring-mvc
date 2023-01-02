package me.khun.studentmanagement.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import me.khun.studentmanagement.model.dto.StudentDto;
import me.khun.studentmanagement.model.entity.Course;
import me.khun.studentmanagement.model.entity.Student;
import me.khun.studentmanagement.model.service.CourseService;
import me.khun.studentmanagement.model.service.StudentService;
import me.khun.studentmanagement.model.service.exception.ServiceException;
import me.khun.studentmanagement.tool.JasperExporter;
import me.khun.studentmanagement.view.Alert;
import me.khun.studentmanagement.view.Alert.Type;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private Formatter<Course> courseFormatter;
	
	@Autowired
	private JasperExporter jasperExporter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addCustomFormatter(courseFormatter);
	}
	
	@GetMapping("/user/student/search")
	public String searchStudents(
			@RequestParam(required = false)
			String studentKeyword,
			@RequestParam(required = false)
			String courseKeyword,
			ModelMap model
			) {
		var students = studentService.search(studentKeyword, courseKeyword);
		model.put("students", students);
		model.put("title", "Students");
		return "students";
	}
	
	@GetMapping("/admin/student/edit")
	public String editStudent(
			@RequestParam(required = false)
			String id,
			ModelMap model
			) {
		var student = (Student) model.getAttribute("student");
		student = student == null ? StudentDto.parse(studentService.findById(id)) : student;
		student = student == null ? new Student() : student;
		model.put("student", student);
		model.put("educations", educations());
		model.put("courses", courseService.findAll());
		model.put("title", (student == null || !StringUtils.hasLength(student.getId())) ? "Add New Student" : "Edit Student");
		return "edit-student";
	}
	
	@PostMapping("/admin/student/save")
	public String saveStudent(
			@Validated
			@ModelAttribute("student")
			Student student,
			BindingResult bindingResult,
			RedirectAttributes redirect
			) {
		
		if (!bindingResult.hasErrors()) {
			try {
				var savedStudent = studentService.save(student);
				var isNewStudent = !Objects.equals(student.getId(), savedStudent.getId());
				var message = isNewStudent
								? "Successfully created a new student."
								: "Successfully updated the student.";
				var type = isNewStudent ? Type.SUCCESS : Type.INFO;
				redirect.addFlashAttribute("alert", new Alert(message, type));
			} catch (ServiceException e) {
				redirect.addFlashAttribute("alert", new Alert(e.getMessage(), Type.ERROR));
			}
		}
		
		if (bindingResult.hasErrors()) {
			redirect.addFlashAttribute("org.springframework.validation.BindingResult.student", bindingResult);
			redirect.addFlashAttribute("student", student);
			return "redirect:/admin/student/edit";
		}
		
		return "redirect:/user/student/search";
	}
	
	@GetMapping("/admin/student/export")
	public String exportStudents (
			@RequestParam(required = false)
			String studentKeyword,
			@RequestParam(required = false)
			String courseKeyword,
			@RequestParam
			String extension,
			ModelMap model,
			HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		var students = studentService.search(studentKeyword, courseKeyword);
		if (students.isEmpty()) {
			model.put("alert", new Alert("There is no student to export.", Type.ERROR));
			return searchStudents(studentKeyword, courseKeyword, model);
		}
		students.add(0, new StudentDto());
		var jasperPath = req.getServletContext().getRealPath("/resources/jasper/student.jrxml");
		
		var fileName = "students-%s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));
		
		resp.setContentType(JasperExporter.getContentType(extension));
		resp.setHeader("Content-Disposition", "attachment; filename=%s.%s".formatted(fileName, extension));
		
		var param = new HashMap<String, Object>();
		param.put("title", "Students");
		
		try {
			var jrDataSource = new JRBeanCollectionDataSource(students, false);
			param.put("StudentListDataSource", jrDataSource);
			var report = JasperCompileManager.compileReport(jasperPath);
			var print = JasperFillManager.fillReport(report, param, jrDataSource);
			jasperExporter.export(print, extension, resp.getOutputStream());
		} catch (IOException | JRException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/user/student/detail")
	public String showStudentDetail(
			@RequestParam
			String id,
			ModelMap model
			) {
		var student = studentService.findById(id);
		model.put("student", student);
		model.put("title", "Student Detail");
		return "student-detail";
	}
	
	@PostMapping("/admin/student/delete")
	public String deleteStudent(
			@RequestParam
			String id,
			RedirectAttributes redirect
			) {
		try {
			studentService.deleteById(id);
			redirect.addFlashAttribute("alert", new Alert("Successfully deleted the student.", Type.ERROR));
		} catch (ServiceException e) {
			redirect.addFlashAttribute("alert", new Alert(e.getMessage(), Type.ERROR));
		}
		return "redirect:/user/student/search";
	}
	
	public List<String> educations() {
		var list = new ArrayList<String>();
		list.add("Bachelor of computer science");
		list.add("Bechlor of Information Technology");
		list.add("Diploma in IT");
		list.add("Other");
		return list;
	}
	
}

package me.khun.studentmanagement.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.khun.studentmanagement.model.entity.Student;
import me.khun.studentmanagement.model.entity.Student.Gender;

public class StudentDto {

    private String id;
    
    private String idPrefix;
    
    private Integer idCode;

    private String name;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String phone;

    private String education;

    private List<CourseDto> courses;
    
    public static StudentDto of(Student student) {
    	var dto = new StudentDto();
    	dto.setId(student.getId());
    	dto.setIdPrefix(student.getIdPrefix());
    	dto.setIdCode(student.getIdCode());
    	dto.setName(student.getName());
    	dto.setDateOfBirth(student.getDateOfBirth());
    	dto.setGender(student.getGender());
    	dto.setPhone(student.getPhone());
    	dto.setEducation(student.getEducation());
    	dto.setCourses(CourseDto.ofList(student.getCourses()));
    	return dto;
    }
    
    public static List<StudentDto> ofList(List<Student> students) {
    	var list = new ArrayList<StudentDto>(students.size());
    	for (var s : students) {
    		list.add(of(s));
    	}
    	return list;
    }
    
    public static Student parse(StudentDto dto) {
    	if (dto == null) {
    		return null;
    	}
    	var student = new Student();
    	student.setId(dto.getId());
    	student.setIdPrefix(dto.getIdPrefix());
    	student.setIdCode(dto.getIdCode());
    	student.setName(dto.getName());
    	student.setDateOfBirth(dto.getDateOfBirth());
    	student.setPhone(dto.getPhone());
    	student.setGender(dto.getGender());
    	student.setEducation(dto.getEducation());
    	student.setCourses(CourseDto.parseList(dto.getCourses()));
    	return student;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdPrefix() {
		return idPrefix;
	}

	public void setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
	}

	public Integer getIdCode() {
		return idCode;
	}

	public void setIdCode(Integer idCode) {
		this.idCode = idCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public List<CourseDto> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseDto> courses) {
		this.courses = courses;
	}
	
	public String getCourseShortNames() {
		var stringBuffer = new StringBuffer();
		var first = true;
		for (var course : courses) {
			if (!first) {
				stringBuffer.append(", ");
			}
			stringBuffer.append(course.getShortName());
			first = false;
		}
		return stringBuffer.toString();
	}

	@Override
	public int hashCode() {
		return Objects.hash(courses, dateOfBirth, education, gender, id, idCode, idPrefix, name, phone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentDto other = (StudentDto) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(education, other.education) && gender == other.gender && Objects.equals(id, other.id)
				&& Objects.equals(idCode, other.idCode) && Objects.equals(idPrefix, other.idPrefix)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
	}

}
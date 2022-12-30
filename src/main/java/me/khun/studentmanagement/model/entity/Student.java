package me.khun.studentmanagement.model.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Student {

	public enum Gender {
        MALE,
        FEMALE
    }
	
    private String id;
    
    private String idPrefix;
    
    private Integer idCode;

    @NotBlank(message = "{blank.student.name}")
    @Size(max = 100, message = "{size.student.name}")
    private String name;

    @NotNull(message = "{null.student.dateOfBirth}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull(message = "{null.student.gender}")
    private Gender gender;

    @NotBlank(message = "{blank.student.phone}")
    @Size(min = 6, max = 15, message = "{size.student.phone}")
    private String phone;

    @NotBlank(message = "{blank.student.education}")
    private String education;

    @NotEmpty(message = "{empty.student.courses}")
    private List<Course> courses;

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

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
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
		Student other = (Student) obj;
		return Objects.equals(courses, other.courses) && Objects.equals(dateOfBirth, other.dateOfBirth)
				&& Objects.equals(education, other.education) && gender == other.gender && Objects.equals(id, other.id)
				&& Objects.equals(idCode, other.idCode) && Objects.equals(idPrefix, other.idPrefix)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
	}

}
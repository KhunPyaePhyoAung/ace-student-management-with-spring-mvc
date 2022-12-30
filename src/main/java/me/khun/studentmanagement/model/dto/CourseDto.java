package me.khun.studentmanagement.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import me.khun.studentmanagement.model.entity.Course;

public class CourseDto {

    private String id;
    
    private String idPrefix;
    
    private Integer idCode;

    private String name;
    
    private String shortName;
    
    public static CourseDto of(Course course) {
    	var dto = new CourseDto();
    	dto.setId(course.getId());
    	dto.setIdPrefix(course.getIdPrefix());
    	dto.setIdCode(course.getIdCode());
    	dto.setName(course.getName());
    	dto.setShortName(course.getShortName());
    	return dto;
    }
    
    public static List<CourseDto> ofList(List<Course> courses) {
    	var list = new ArrayList<CourseDto>(courses.size());
    	for (var c : courses) {
    		list.add(of(c));
    	}
    	return list;
    }
    
    public static Course parse(CourseDto dto) {
    	if (dto == null) {
    		return null;
    	}
    	var course = new Course();
    	course.setId(dto.getId());
    	course.setIdPrefix(dto.getIdPrefix());
    	course.setIdCode(dto.getIdCode());
    	course.setName(dto.getName());
    	course.setShortName(dto.getShortName());
    	return course;
    }
    
    public static List<Course> parseList(List<CourseDto> dtoList) {
    	var list = new ArrayList<Course>(dtoList.size());
    	for (var c : dtoList) {
    		list.add(parse(c));
    	}
    	return list;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idCode, idPrefix, name, shortName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDto other = (CourseDto) obj;
		return Objects.equals(id, other.id) && Objects.equals(idCode, other.idCode)
				&& Objects.equals(idPrefix, other.idPrefix) && Objects.equals(name, other.name)
				&& Objects.equals(shortName, other.shortName);
	}

}
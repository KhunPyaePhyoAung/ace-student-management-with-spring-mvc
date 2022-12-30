package me.khun.studentmanagement.model.entity;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Course {
	
    private String id;
    
    private String idPrefix;
    
    private Integer idCode;

    @NotBlank(message = "{blank.course.name}")
    @Size(max = 225, message="{size.course.name}")
    private String name;
    
    @NotBlank(message = "{blank.course.shortName}")
    @Size(max = 10, message="{size.course.shortName}")
    private String shortName;
    
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
		Course other = (Course) obj;
		return Objects.equals(id, other.id) && Objects.equals(idCode, other.idCode)
				&& Objects.equals(idPrefix, other.idPrefix) && Objects.equals(name, other.name)
				&& Objects.equals(shortName, other.shortName);
	}
	
}
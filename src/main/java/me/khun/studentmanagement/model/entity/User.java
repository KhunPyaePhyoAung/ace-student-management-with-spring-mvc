package me.khun.studentmanagement.model.entity;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

	public enum Role {
		ADMIN, USER
	}
	
    private String id;
    
    private String idPrefix;
    
    private Integer idCode;

    @NotBlank(message = "{blank.user.name}")
    @Size(max = 100, message = "{size.user.name}")
    private String name;

    @NotBlank(message = "{blank.user.email}")
    @Email(message = "{invalid.user.email}")
    @Size(max = 225, message = "{size.user.email}")
    private String email;

    @Size(min = 4, max = 50, message="{size.user.password}")
    private String password;
    
    private String confirmPassword;
    
    @NotNull(message = "{null.user.role}")
    private Role role;
    
    private boolean approved;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public int hashCode() {
		return Objects.hash(approved, email, id, idCode, idPrefix, name, password, role);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return approved == other.approved && Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(idCode, other.idCode) && Objects.equals(idPrefix, other.idPrefix)
				&& Objects.equals(name, other.name) && Objects.equals(password, other.password) && role == other.role;
	}

}
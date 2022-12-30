<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="secu" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<jsp:include page="/views/resources.jsp"></jsp:include>
</head>
<body>
	<c:set var="menu" value="users" scope="request"></c:set>
	<jsp:include page="/views/nav-bar.jsp"></jsp:include>
	
	<div class="container my-4">
		<div class="row">
			<div class="col-6 offset-3">
				<span class="fs-4 fw-bold">${title}</span>
				<c:if test="${not empty userFormErrorMessage}">
					<div class="mt-4">
					<label class="text-danger">${userFormErrorMessage}</label>
				</div>
				</c:if>
				
				<c:url var="userSaveUrl" value="/admin/user/save"></c:url>
				
				<form:form id="userForm" cssClass="mt-4" action="${userSaveUrl}" modelAttribute="user">
					<form:hidden path="idPrefix"/>
					<form:hidden path="idCode"/>
					<form:hidden path="id"/>
					<input type="hidden" name="approved" value="true">
					<div class="form-group">
						<form:label path="name" cssClass="form-label">Name</form:label>
						<form:input path="name" type="text" cssClass="form-control"/>
						<form:errors path="name" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="role" cssClass="form-label">Role</form:label>
						<form:select path="role" cssClass="form-select">
							<form:option value="" label="-- select role --"></form:option>
							<form:option value="ADMIN" label="Admin"></form:option>
							<c:set var="userRoleOptionDisabled" value="${loginInfo.getUser().getId() eq user.getId() ? 'true' : 'false'}"></c:set>
							<form:option value="USER" label="User" disabled="${userRoleOptionDisabled}"></form:option>
						</form:select>
						<form:errors path="role" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="email" cssClass="form-label">Email</form:label>
						<form:input path="email" type="email" cssClass="form-control"/>
						<form:errors path="email" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="password" cssClass="form-label">Password</form:label>
						<form:password path="password" cssClass="form-control" showPassword="true"/>
						<form:errors path="password" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="confirmPassword" cssClass="form-label">Confirm Password</form:label>
						<form:password path="confirmPassword" cssClass="form-control" showPassword="true"/>
						<form:errors path="confirmPassword" cssClass="text-danger"></form:errors>
					</div>
					
					<button class="form-control btn btn-primary mt-4" type="submit">
						<i class="fa-solid fa-floppy-disk me-2"></i>
						Save
					</button>
					
					<c:url var="cancelUrl" value="/user/user/search"></c:url>
					<a href="${cancelUrl}" class="btn btn-secondary d-block mt-2">
						<i class="fa-solid fa-xmark me-2"></i>
						Cancel
					</a>
				</form:form>
				
			</div>
		</div>
	</div>

</body>
</html>
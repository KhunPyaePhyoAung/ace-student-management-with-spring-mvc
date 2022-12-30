<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cus" uri="/WEB-INF/custom-tag.tld" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>
<jsp:include page="/views/resources.jsp"></jsp:include>
</head>
<body class="bg-primary">

	<div class="container my-5">
		<div class="row">
			<c:url var="signupUrl" value="/signup"></c:url>
			
			<form:form cssClass="col-4 offset-4 card p-3" action="${signupUrl}" modelAttribute="user">
				<span class="card-title fs-3 fw-bold text-center">Sign Up</span>
				<div class="card-body">
					<c:if test="${not empty signupFormErrorMessage}">
						<label class="text-danger mb-3">${signupFormErrorMessage}</label>
					</c:if>
					<form:hidden path="role"/>
					<div class="form-group">
						<form:label path="name" cssClass="form-label">Name</form:label>
						<form:input path="name" cssClass="form-control"/>
						<form:errors path="name" cssClass="text-danger"></form:errors>
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
					
					<button class="btn btn-primary form-control mt-4" type="submit">
						SIGNUP
					</button>
					
					<div class="text-center mt-4">
						<c:url var="loginUrl" value="/login"></c:url>
						<a href="${loginUrl}">Login</a>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
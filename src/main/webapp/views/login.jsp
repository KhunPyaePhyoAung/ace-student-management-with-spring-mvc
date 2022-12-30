<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<jsp:include page="/views/resources.jsp"></jsp:include>
</head>
<body class="bg-primary">

	<div class="container my-5">
		<div class="row">
			<c:url var="loginUrl" value="/login"></c:url>
			<form action="${loginUrl}" class="col-4 offset-4 card p-3" method="post">
				<span class="card-title fs-4 fw-bold text-center">LOGIN</span>
				
				<div class="card-body">
					<c:if test="${not empty loginFormErrorMessage}">
						<label class="text-danger mb-3">${loginFormErrorMessage}</label>
					</c:if>
					<div class="form-group">
						<label for="email" class="form-label">Email</label>
						<input id="email" type="email" class="form-control" name="email" value="${param.email}" required/>
					</div>
					<div class="form-group mt-3">
						<label for="password" class="form-label">Password</label>
						<input id="password" type="password" class="form-control" name="password" value="${param.password}" required/>
					</div>
					<button class="btn btn-primary form-control mt-4" type="submit">
						LOGIN
					</button>
				</div>
				<div class="text-center">
					<c:url var="signupUrl" value="/signup"></c:url>
					<a href="${signupUrl}">Create a new account</a>
				</div>
			</form>
			
		</div>
	</div>
</body>
</html>
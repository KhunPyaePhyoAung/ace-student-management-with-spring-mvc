<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<jsp:include page="/views/resources.jsp"></jsp:include>
</head>
<body>
	<c:set var="menu" value="home" scope="request"></c:set>
	<jsp:include page="/views/nav-bar.jsp"></jsp:include>
	
	<main class="container my-4">
	
		<div class="row">
			<div class="text-center">
				<c:set var="loginInfo" value="${sessionScope.loginInfo}"></c:set>
				<c:set var="user" value="${loginInfo.getUser()}"></c:set>
				<c:choose>
					<c:when test="${user.getRole() eq 'ADMIN'}">
						<i class="fa-solid fa-user-shield fs-5 text-success"></i>
					</c:when>
					<c:when test="${user.getRole() eq 'USER'}">
						<i class="fa-solid fa-user fs-5 text-secondary"></i>
					</c:when>
				</c:choose>
				<span>${user.getId()}</span> : 
				<span>${user.getName()}</span>
				<i class="fa-solid fa-clock ms-4"></i> : ${loginInfo.getLoggedInDateTimeStirng()}
			</div>
		</div>
		
		<div class="row mt-4">
			<div class="col">
				<div class="card bg-success text-white">
					<div class="card-header text-center">
						<i class="fa-solid fa-user me-2"></i>
						User
					</div>
					<div class="card-body text-center">
						<div class="card-text fs-2">${userCount}</div>
					</div>
				</div>
			</div>
			
			<div class="col">
				<div class="card bg-danger text-white">
					<div class="card-header text-center">
						<i class="fa-solid fa-folder-open me-2"></i>
						Course
					</div>
					<div class="card-body text-center">
						<div class="card-text fs-2">${courseCount}</div>
					</div>
				</div>
			</div>
			
			<div class="col">
				<div class="card bg-secondary text-white">
					<div class="card-header text-center">
						<i class="fa-solid fa-graduation-cap me-2"></i>
						Student
					</div>
					<div class="card-body text-center">
						<div class="card-text fs-2">${studentCount}</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	
</body>
</html>
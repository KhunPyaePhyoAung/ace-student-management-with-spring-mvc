<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	
	<main class="container">
		<div class="row mt-4">
			<div class="col-6 offset-3 fs-4 fw-bold">
				${title}
			</div>
		</div>
		<div class="row mt-4">
			<div class="col-6 offset-3">
				<table class="table">
					<tbody>
						<tr>
							<th>ID</th>
							<td>${student.getId()}</td>
						</tr>
						<tr>
							<th>Name</th>
							<td>${student.getName()}</td>
						</tr>
						<tr>
							<th>Date Of Birth</th>
							<td>${student.getDateOfBirth()}</td>
						</tr>
						<tr>
							<th>Gender</th>
							<td>${student.getGender()}</td>
						</tr>
						<tr>
							<th>Phone</th>
							<td>${student.getPhone()}</td>
						</tr>
						<tr>
							<th>Education</th>
							<td>${student.getEducation()}</td>
						</tr>
						<tr>
							<th>Attend</th>
							<td>
								<c:forEach var="c" items="${student.getCourses()}">
									<c:url var="courseDetailUrl" value="/user/course/detail">
										<c:param name="id">${c.getId()}</c:param>
									</c:url>
									<a class="badge bg-secondary text-light" href="${courseDetailUrl}" title="${c.getId()} : ${c.getName()}">${c.getShortName()}</a>
									
								</c:forEach>
							</td>
						</tr>
					</tbody>
				</table>
				
				<secu:authorize access="hasAuthority('ADMIN')">
					<c:url var="studentEditUrl" value="/admin/student/edit">
						<c:param name="id">${student.getId()}</c:param>
					</c:url>
					<a href="${studentEditUrl}" class="btn btn-primary w-100 mt-4">
						<i class="fa-solid fa-pen-to-square"></i>
						Edit
					</a>
				</secu:authorize>
				
				<c:url var="cancelUrl" value="/user/student/search"></c:url>
				<a href="${cancelUrl}" class="btn btn-secondary w-100 mt-2">
					<i class="fa-solid fa-xmark me-2"></i>
					Close
				</a>
			</div>
		</div>
	</main>

</body>
</html>
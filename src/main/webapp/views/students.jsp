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
	<c:set var="menu" value="students" scope="request"></c:set>
	<jsp:include page="/views/nav-bar.jsp"></jsp:include>
	
	<div class="container my-4">
		<c:url var="searchStudentUrl" value="/user/student/search"></c:url>
		<form action="${searchStudentUrl}" class="row mt-3">
			<div class="col-auto">
				<div class="input-group">
					<input class="form-control" type="text" name="studentKeyword" value="${param.studentKeyword}" placeholder="Search Student" />
					<input class="form-control" type="text" name="courseKeyword" value="${param.courseKeyword}" placeholder="Search Course" />
					<a href="${searchStudentUrl}" class="input-group-text">
						<i class="fa-solid fa-delete-left"></i>
					</a>
					<button class="col-auto btn btn-primary" type="submit">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>
			</div>
			
			<secu:authorize access="hasAuthority('ADMIN')">
				<div class="col-auto">
					<c:url var="addNewStudentUrl" value="/admin/student/edit"></c:url>
					<a href="${addNewStudentUrl}" class="btn btn-success">
						<i class="fa-solid fa-plus"></i>
						Add New
					</a>
				</div>
			</secu:authorize>
			
			<secu:authorize access="hasAuthority('ADMIN')">
				<div class="col-auto btn-group">
					<button type="button" class="btn btn-secondary" >
						<i class="fa-solid fa-print"></i>
						Export As
					</button>
				    <button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-expanded="false" >
				    	<span class="visually-hidden">Toggle Dropdown</span>
				    </button>
					<ul class="dropdown-menu">
						<c:url var="exportStudentPdfUrl" value="/admin/student/export">
							<c:param name="studentKeyword" value="${param.studentKeyword}"></c:param>
							<c:param name="courseKeyword" value="${param.courseKeyword}"></c:param>
							<c:param name="extension" value="pdf"></c:param>
						</c:url>
						<li><a class="dropdown-item" href="${exportStudentPdfUrl}">PDF</a></li>
						
						
						<c:url var="exportStudentExcelUrl" value="/admin/student/export">
							<c:param name="studentKeyword" value="${param.studentKeyword}"></c:param>
							<c:param name="courseKeyword" value="${param.courseKeyword}"></c:param>
							<c:param name="extension" value="xlsx"></c:param>
						</c:url>
						<li><a class="dropdown-item" href="${exportStudentExcelUrl}">Excel</a></li>
					</ul>
				</div>
			</secu:authorize>
			
		</form>
		
		<jsp:include page="/views/alert.jsp"></jsp:include>
		
		<table class="table table-striped align-middle mt-4">
			<thead>
				<tr>
					<th>#</th>
					<th>ID</th>
					<th>Name</th>
					<th>Gender</th>
					<th>Course</th>
					<th class="text-center">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="s" items="${students}" varStatus="status">
					<tr>
						<td class="text-nowrap">${status.getCount()}</td>
						<td class="text-nowrap">${s.getId()}</td>
						<td class="text-nowrap">${s.getName()}</td>
						<td class="text-nowrap">
							<c:choose>
								<c:when test="${s.getGender() eq 'MALE'}">
									<i title="${s.getGender()}" class="fa-solid fa-mars text-success fs-5"></i>
								</c:when>
								<c:when test="${s.getGender() eq 'FEMALE'}">
									<i title="${s.getGender()}" class="fa-solid fa-venus text-danger fs-5"></i>
								</c:when>
							</c:choose>
						</td>
						<td>
							<c:forEach var="c" items="${s.getCourses()}">
								<c:url var="courseDetailUrl" value="/user/course/detail">
									<c:param name="id">${c.getId()}</c:param>
								</c:url>
								<a class="badge bg-secondary text-light" href="${courseDetailUrl}" title="${c.getId()} : ${c.getName()}">${c.getShortName()}</a>
								
							</c:forEach>
						</td>
						<td class="text-center text-nowrap">
							<secu:authorize access="hasAuthority('ADMIN')">
								<c:url var="editStudentUrl" value="/admin/student/edit">
									<c:param name="id">${s.getId()}</c:param>
								</c:url>
								<a href="${editStudentUrl}" class="btn btn-primary">
									<i class="fa-solid fa-pen-to-square"></i>
								</a>
							</secu:authorize>
							
							
							<c:url var="studentDetailUrl" value="/user/student/detail">
								<c:param name="id">${s.getId()}</c:param>
							</c:url>
							<a href="${studentDetailUrl}" class="btn btn-secondary">
								<i class="fa-solid fa-circle-info"></i>
							</a>
							
							<secu:authorize access="hasAuthority('ADMIN')">
								<c:url var="deleteStudentUrl" value="/admin/student/delete">
									<c:param name="id">${s.getId()}</c:param>
								</c:url>
								<form class="d-inline-block" action="${deleteStudentUrl}" method="post">
									<button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure to delete this student.')">
										<i class="fa-solid fa-trash"></i>
									</button>
								</form>
							</secu:authorize>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>
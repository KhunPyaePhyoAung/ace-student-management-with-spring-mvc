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
	<c:set var="menu" value="courses" scope="request"></c:set>
	<jsp:include page="/views/nav-bar.jsp"></jsp:include>
	
	<div class="container my-4">
		<c:url var="searchCourseUrl" value="/user/course/search"></c:url>
		<form action="${searchCourseUrl}"  class="row mt-3">
			<div class="col-auto">
				<div class="input-group">
					<input class="form-control" type="text" name="keyword" value="${param.keyword}" placeholder="Search Course" />
					<a href="${searchCourseUrl}" class="input-group-text">
						<i class="fa-solid fa-delete-left"></i>
					</a>
					<button class="col-auto btn btn-primary" type="submit">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>
			</div>
			
			<secu:authorize access="hasAuthority('ADMIN')">
				<div class="col-auto">
					<c:url var="addNewCourseUrl" value="/admin/course/edit"></c:url>
					<a href="${addNewCourseUrl}" class="btn btn-success">
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
						<c:url var="exportCoursePdfUrl" value="/admin/course/export">
							<c:param name="keyword" value="${param.keyword}"></c:param>
							<c:param name="extension" value="pdf"></c:param>
						</c:url>
						<li><a class="dropdown-item" href="${exportCoursePdfUrl}">PDF</a></li>
						
						
						<c:url var="exportCourseExcelUrl" value="/admin/course/export">
							<c:param name="keyword" value="${param.keyword}"></c:param>
							<c:param name="extension" value="xlsx"></c:param>
						</c:url>
						<li><a class="dropdown-item" href="${exportCourseExcelUrl}">Excel</a></li>
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
					<th>Short Name</th>
					<th class="text-center">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="c" items="${courses}" varStatus="status">
					<tr>
						<td class="text-nowrap">${status.getCount()}</td>
						<td class="text-nowrap">${c.getId()}</td>
						<td>${c.getName()}</td>
						<td class="text-nowrap">${c.getShortName()}</td>
						<td class="text-center text-nowrap">
							<secu:authorize access="hasAuthority('ADMIN')">
								<c:url var="editCourseUrl" value="/admin/course/edit">
									<c:param name="id">${c.getId()}</c:param>
								</c:url>
								<a href="${editCourseUrl}" class="btn btn-primary">
									<i class="fa-solid fa-pen-to-square"></i>
								</a>
							</secu:authorize>
							
							<c:url var="courseDetailUrl" value="/user/course/detail">
								<c:param name="id">${c.getId()}</c:param>
							</c:url>
							<a href="${courseDetailUrl}" class="btn btn-secondary">
								<i class="fa-solid fa-circle-info"></i>
							</a>
							
							<secu:authorize access="hasAuthority('ADMIN')">
								<c:url var="deleteCourseUrl" value="/admin/course/delete">
									<c:param name="id">${c.getId()}</c:param>
								</c:url>
								<form class="d-inline-block" action="${deleteCourseUrl}" method="post">
									<button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure to delete this course.')">
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
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
	
	<div class="container my-4">
		<c:url var="searchUserUrl" value="/user/user/search"></c:url>
		<form id="searchForm" action="${searchUserUrl}" class="row mt-3">
			<div class="col-auto">
				<div class="input-group">
					<input class="form-control" type="text" name="keyword" value="${param.keyword}" placeholder="Search User" />
					<a href="${searchUserUrl}" class="input-group-text">
						<i class="fa-solid fa-delete-left"></i>
					</a>
					<button class="btn btn-primary" type="submit">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>
			</div>
			
			<secu:authorize access="hasAuthority('ADMIN')">
				<div class="col-auto">
					<c:url var="addNewUserUrl" value="/admin/user/edit"></c:url>
					<a href="${addNewUserUrl}" class="btn btn-success">
						<i class="fa-solid fa-plus"></i>
						Add New
					</a>
				</div>
			</secu:authorize>
			
			<secu:authorize access="hasAuthority('ADMIN')">
				<div class="col-auto">
					<c:url var="userRequestUrl" value="/admin/user/request/search">
					</c:url>
					<a href="${userRequestUrl}" class="btn btn-primary">
						<i class="fa-solid fa-user-plus"></i>
						Requests
						<secu:authorize access="hasAuthority('ADMIN')">
							<c:if test="${userRequestCount gt 0}">
								<span class="badge bg-danger">${userRequestCount}</span>
							</c:if>
						</secu:authorize>
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
						<c:url var="exportUserPdfUrl" value="/admin/user/export">
							<c:param name="keyword" value="${param.keyword}"></c:param>
							<c:param name="extension" value="pdf"></c:param>
						</c:url>
						<li><a class="dropdown-item" href="${exportUserPdfUrl}">PDF</a></li>
						
						
						<c:url var="exportUserExcelUrl" value="/admin/user/export">
							<c:param name="keyword" value="${param.keyword}"></c:param>
							<c:param name="extension" value="xlsx"></c:param>
						</c:url>
						<li><a class="dropdown-item" href="${exportUserExcelUrl}">Excel</a></li>
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
					<th>Role</th>
					<th>Email</th>
					<th class="text-center">Actions</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="u" items="${users}" varStatus="status">
					<tr>
						<td class="text-nowrap">${status.getCount()}</td>
						<td class="text-nowrap">${u.getId()}</td>
						<td class="text-nowrap">${u.getName()}</td>
						<td class="text-nowrap">
							<c:choose>
								<c:when test="${u.getRole() eq 'ADMIN'}">
									<i title="${u.getRole()}" class="fa-solid fa-user-shield fs-5 text-success"></i>
								</c:when>
								<c:when test="${u.getRole() eq 'USER'}">
									<i title="${u.getRole()}" class="fa-solid fa-user fs-5 text-secondary"></i>
								</c:when>
							</c:choose>
						</td>
						<td class="text-nowrap">${u.getEmail()}</td>
						<td class="text-center text-nowrap">
							
							<secu:authorize access="hasAuthority('ADMIN')">
								<c:url var="editUserUrl" value="/admin/user/edit">
									<c:param name="id">${u.getId()}</c:param>
								</c:url>
								<a href="${editUserUrl}" class="btn btn-primary">
									<i class="fa-solid fa-pen-to-square"></i>
								</a>
							</secu:authorize>
							
							
							<c:url var="userDetailUrl" value="/user/user/detail">
								<c:param name="id">${u.getId()}</c:param>
							</c:url>
							<a href="${userDetailUrl}" class="btn btn-secondary">
								<i class="fa-solid fa-circle-info"></i>
							</a>
							
							<secu:authorize access="hasAuthority('ADMIN')">
								<c:url var="deleteUserUrl" value="/admin/user/delete">
									<c:param name="id">${u.getId()}</c:param>
								</c:url>
								<form class="d-inline-block" action="${deleteUserUrl}" method="post">
									<button class="btn btn-danger" onclick="return confirm('Are you sure to delete this user.')">
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
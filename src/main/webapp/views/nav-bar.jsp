<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="secu" uri="http://www.springframework.org/security/tags" %>
<nav class="bg-primary d-flex justify-content-between align-items-center">
	<div class="fs-4 fw-bold text-white p-2 ms-4">
		Student Management
	</div>
	
	<ul class="nav-item-wrapper fw-bold">
		<li class="nav-item ${menu eq 'home' ? 'active' : ''}">
			<c:url var="homeUrl" value="/"></c:url>
			<a href="${homeUrl}" class="nav-link">
				<i class="fa-solid fa-house me-2"></i>
				Home
			</a>
		</li>
		<li class="nav-item ${menu eq 'users' ? 'active' : ''}">
			<c:url var="userSearchUrl" value="/user/user/search"></c:url>
			<a href="${userSearchUrl}" class="nav-link">
				<i class="fa-solid fa-user me-2"></i>
				Users
				<secu:authorize access="hasAuthority('ADMIN')">
					<c:if test="${userRequestCount gt 0}">
						<span class="badge bg-danger ms-2">${userRequestCount}</span>
					</c:if>
				</secu:authorize>
			</a>
		</li>
		<li class="nav-item ${menu eq 'courses' ? 'active' : ''}">
			<c:url var="courseSearchUrl" value="/user/course/search"></c:url>
			<a href="${courseSearchUrl}" class="nav-link">
				<i class="fa-solid fa-folder-open me-2"></i>
				Courses
			</a>
		</li>
		<li class="nav-item ${menu eq 'students' ? 'active' : ''}">
			<c:url var="studentSearchUrl" value="/user/student/search"></c:url>
			<a href="${studentSearchUrl}" class="nav-link">
				<i class="fa-solid fa-user-graduate me-2"></i>
				Students
			</a>
		</li>
		<li class="nav-item">
			<c:url var="logoutUrl" value="/logout"></c:url>
			<a href="${logoutUrl}" class="nav-link">
				<i class="fa-solid fa-power-off me-2"></i>
				Logout
			</a>
		</li>
	</ul>
</nav>
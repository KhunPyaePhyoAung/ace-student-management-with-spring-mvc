<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
		<div class="row">
			<div class="col-6 offset-3">
				<span class="fs-4 fw-bold">${title}</span>
				<c:if test="${not empty studentFormErrorMessage}">
					<div class="mt-4">
						<label class="text-danger">${studentFormErrorMessage}</label>
					</div>
				</c:if>
				
				<c:url var="studentSaveUrl" value="/admin/student/save"></c:url>
				<form:form cssClass="mt-4" action="${studentSaveUrl}" modelAttribute="student">
					<form:hidden path="idPrefix"/>
					<form:hidden path="idCode"/>
					<form:hidden path="id"/>
					
					<div class="form-group">
						<form:label path="name" cssClass="form-label">Name</form:label>
						<form:input path="name" cssClass="form-control"/>
						<form:errors path="name" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="dateOfBirth" cssClass="form-label">Date Of Birth</form:label>
						<form:input path="dateOfBirth" cssClass="form-control" type="date"/>
						<form:errors path="dateOfBirth" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="row mt-3">
						<form:label path="gender" cssClass="col-auto form-label">Gender</form:label>
						<div class="col-auto form-check">
							<form:radiobutton id="genderMale" path="gender" cssClass="form-check-input" value="MALE"/>
							<form:label for="genderMale" path="gender">Male</form:label>
						</div>
						<div class="col-auto form-check">
							<form:radiobutton id="genderFemale" path="gender" cssClass="form-check-input" value="FEMALE"/>
							<form:label for="genderFemale" path="gender">Female</form:label>
						</div>
					</div>
					<form:errors path="gender" cssClass="text-danger"></form:errors>
					
					<div class="form-group mt-3">
						<form:label path="phone" cssClass="form-label">Phone</form:label>
						<form:input path="phone" cssClass="form-control" type="tel"/>
						<form:errors path="phone" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="education" cssClass="form-label">Education</form:label>
						<form:select path="education" cssClass="form-select">
							<form:option value="">-- select education --</form:option>
							<form:options items="${educations}"/>
						</form:select>
						<form:errors path="education" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="courses" cssClass="form-label">Attend</form:label>
						
						<div class="d-flex flex-wrap align-content-between justify-content-start mt-2">
							<c:forEach var="course" items="${courses}" varStatus="status">
								<div class="form-check me-3">
									<form:checkbox id="course${status.getCount()}" path="courses" value="${course.getId()}" cssClass="form-check-input"/>
									<p title="${course.getId()} : ${course.getName()}">
										<label for="course${status.getCount()}" class="form-check-label">
											${course.getShortName()}
										</label>
									</p>
								</div>
							</c:forEach>
						</div>
						<form:errors path="courses" cssClass="text-danger"></form:errors>
					</div>
					
					<button class="form-control btn btn-primary mt-4" type="submit">
						<i class="fa-solid fa-floppy-disk me-2"></i>
						Save
					</button>
					
					<c:url var="cancelUrl" value="/user/student/search"></c:url>
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
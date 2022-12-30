<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="cus" uri="/WEB-INF/custom-tag.tld" %>
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
		<div class="row">
			<div class="col-6 offset-3">
				<span class="fs-4 fw-bold">${title}</span>
				
				<c:if test="${not empty courseFormErrorMessage}">
					<div class="mt-4">
						<label class="text-danger">${courseFormErrorMessage}</label>
					</div>
				</c:if>
				
				<c:url var="saveCourseUrl" value="/admin/course/save"></c:url>
				
				<form:form action="${saveCourseUrl}" cssClass="mt-4" modelAttribute="course">
					<form:hidden path="id"/>
					<form:hidden path="idPrefix"/>
					<form:hidden path="idCode"/>
					
					<div class="form-group">
						<form:label path="name" cssClass="form-label">Name</form:label>
						<form:input path="name" cssClass="form-control"/>
						<form:errors path="name" cssClass="text-danger"></form:errors>
					</div>
					
					<div class="form-group mt-3">
						<form:label path="shortName" cssClass="form-label">Short Name</form:label>
						<form:input path="shortName" cssClass="form-control"/>
						<form:errors path="shortName" cssClass="text-danger"></form:errors>
					</div>
					
					<button class="form-control btn btn-primary mt-4" type="submit">
						<i class="fa-solid fa-floppy-disk me-2"></i>
						Save
					</button>
					
					<c:url var="cancelUrl" value="/user/course/search"></c:url>
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${not empty alert}">
	<c:choose>
		<c:when test="${alert.getType() eq 'SUCCESS'}">
			<c:set var="alertType" value="alert-success"></c:set>
		</c:when>
		<c:when test="${alert.getType() eq 'ERROR'}">
			<c:set var="alertType" value="alert-danger"></c:set>
		</c:when>
		<c:when test="${alert.getType() eq 'WARNING'}">
			<c:set var="alertType" value="alert-warning"></c:set>
		</c:when>
		<c:when test="${alert.getType() eq 'INFO'}">
			<c:set var="alertType" value="alert-info"></c:set>
		</c:when>
		<c:otherwise>
			<c:set var="alertType" value="alert-secondary"></c:set>
		</c:otherwise>
	</c:choose>
	<div class="alert ${alertType} alert-dismissible fade show mt-4">
		${alert.getMessage()}
		<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
	</div>
</c:if>
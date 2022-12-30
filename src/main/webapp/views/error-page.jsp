<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<jsp:include page="/views/resources.jsp"></jsp:include>
</head>
<body>
	<div class="container pt-5">
		<div class="card text-white bg-danger">
			<div class="card-header fs-2">${title}</div>
			<div class="card-body">${message}</div>
		</div>
	</div>
</body>
</html>
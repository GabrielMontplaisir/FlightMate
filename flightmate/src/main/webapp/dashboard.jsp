<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body class="background">
	<jsp:include page='./components/header.jsp' />
	<main>
		<h1 class="subtitle">Dashboard</h1>
		<p>Hello ${user.getFirstName()}</p>
		<p>Your role: ${user.getRole().toString()}</p>
	</main>
</body>
</html>
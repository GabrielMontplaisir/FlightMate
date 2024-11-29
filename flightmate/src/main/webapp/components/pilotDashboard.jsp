<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pilotDashboard.css">
</head>
<body>
    <jsp:include page='./components/header.jsp' />
    <main>
        <h1>Welcome to the Pilot Dashboard</h1>
        <p>Hello, ${user.getFirstName()}!</p>
        <p>Your role: ${user.getRole().toString()}</p>

        <nav>
            <ul>
                <li><a href="flight.jsp">Manage Flights</a></li>
                <li><a href="upload.jsp">Upload Documents</a></li>
                <li><a href="feedback.jsp">Submit Feedback</a></li>
            </ul>
        </nav>
    </main>
</body>
</html>

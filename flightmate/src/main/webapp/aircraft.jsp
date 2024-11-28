<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>FlightMate | Aircrafts</title>
</head>

<body class="background">
    <jsp:include page='./components/header.jsp' />

    <main>
        <section class="container">
            <h1 class="center">Add New Aircraft</h1>
            
            <c:if test="${not empty success}">
                <div class="alert success center">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert error center">${error}</div>
            </c:if>
            
            <form action="aircraft" method="post" class="my-2">
                <div class="form-group space-evenly">
                    <label for="model" class="form-label">Aircraft Model:</label>
                    <input type="text" id="model" name="model" class="form-input flex-2 border-2 rounded" required>
                </div>

                <div class="form-group space-evenly ">
                    <label for="airport" class="form-label">Assign to Airport:</label>
                    <select id="airport" name="airport" class="form-input border-2 rounded">
                        <c:forEach var="airport" items="${airports}">
                            <option value="${airport.getAirportId()}">${airport.getAirportName()}</option>
                        </c:forEach>
                    </select>
                    <!-- Button to Add Airport -->
                    <a href="${pageContext.request.contextPath}/airport" class="btn ml-2">Add Airport</a>
                </div>

                <div class="form-group space-evenly">
                    <label for="details" class="form-label">Aircraft Details:</label>
                    <textarea id="details" name="details" class="form-input flex-2 border-2 rounded"></textarea>
                </div>

                <div class="center my-2">
                    <button type="submit" class="btn success">Submit</button>
                </div>
            </form>
            
        </section>
    </main>
</body>
</html>

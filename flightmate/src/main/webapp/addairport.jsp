<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>FlightMate | Login</title>
	<jsp:include page="./components/head.jsp" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
</head>
<body class="background">
    <main>	
    	<header>
       		<h1 class="center">Add Airport</h1>
    	</header>
    <form action="AddAirportServlet" method="post" class="login_form">
        <label for="airportName" class="login_form_label">Airport Name:</label>
        <input type="text" id="airportName" name="airportName" class="login_form_input" required><br>

        <label for="airportCode" class="login_form_label">Airport Code (3-letter):</label>
        <input type="text" id="airportCode" name="airportCode" maxlength="3" class="login_form_input" required><br>

        <label for="city" class="login_form_label">City:</label>
        <input type="text" id="city" name="city" class="login_form_input" required><br>

        <label for="country" class="login_form_label">Country:</label>
        <input type="text" id="country" name="country" class="login_form_input" required><br>

        <label for="runways" class="login_form_label">Number of Runways:</label>
        <input type="number" id="runways" name="runways" min="1" class="login_form_input" required><br>

        <button type="submit" class="login_form_btn">Add Airport</button>
    </form>
    </main>
    <footer class="center">
		<p>Created by Biqi, Dennis, Emily, Gabriel &amp; Jianxiang</p>
		<p>for CST8319 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>

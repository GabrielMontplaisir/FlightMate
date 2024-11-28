<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.flightmate.beans.Airport" %>
<%@ page import="com.flightmate.dao.AirportDao" %>
<!DOCTYPE html>
<html lang="en">
<head>    
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>List of Airports</title>
</head>
<body class="background">
<main class="center">
    <h1>List of Airports</h1>
    <table class="temptable">
        <tr>
            <th>ID</th>
            <th>Airport Name</th>
            <th>Code</th>
            <th>City</th>
            <th>Country</th>
            <th>Runways</th>
            <th>Actions</th>
        </tr>
        <%
            AirportDao dao = AirportDao.getDao();
            List<Airport> airports = dao.getAllAirports();
            for (Airport airport : airports) {
        %>
        <form method="post" action="UpdateDeleteAirportServlet">
        <tr>
            <td><%= airport.getId() %><input type="hidden" name="id" value="<%= airport.getId() %>" /></td>
            <td><input type="text" name="airport_name" value="<%= airport.getAirport_name() %>" /></td>
            <td><input type="text" name="airport_code" value="<%= airport.getAirport_code() %>" /></td>
            <td><input type="text" name="city" value="<%= airport.getCity() %>" /></td>
            <td><input type="text" name="country" value="<%= airport.getCountry() %>" /></td>
            <td><input type="number" name="runways" value="<%= airport.getRunways() %>" /></td>
            <td>
                <input type="submit" name="action" value="Update" />
                <input type="submit" name="action" value="Delete" onclick="return confirm('Are you sure you want to delete this airport?')" />
            </td>
        </tr>
        </form>
        <%
            }
        %>
    </table>
    </main>
    <footer class="center">
        <p>Created by Biqi, Dennis, Emily, Gabriel &amp; Jianxiang</p>
        <p>for CST8319 -- Algonquin College (2024)</p>
    </footer>
</body>
</html>

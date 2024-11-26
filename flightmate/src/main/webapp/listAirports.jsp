<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
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
        </tr>
        <%
            try {
            	 String dbURL = "jdbc:mysql://localhost:3306/flightmate";
                 String dbUser = "root";
                 String dbPassword = "12345678"; 
                 
                     try {
                         Class.forName("com.mysql.cj.jdbc.Driver");
                     } catch (ClassNotFoundException e) {
                         throw new RuntimeException("Failed to load MySQL driver", e);
                     }  
                
                Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                String query = "SELECT * FROM airports";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
        %>
        <tr>
            <td><%= rs.getInt("id") %></td>
            <td><%= rs.getString("airport_name") %></td>
            <td><%= rs.getString("airport_code") %></td>
            <td><%= rs.getString("city") %></td>
            <td><%= rs.getString("country") %></td>
            <td><%= rs.getInt("runways") %></td>
        </tr>
        <%
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
        %>
        <tr>
            <td colspan="6">Error retrieving airport data.</td>
        </tr>
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

package com.flightmate.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.flightmate.beans.Airport;
import com.flightmate.dao.AirportDao;
import com.flightmate.libs.Validation;
import com.flightmate.libs.services.SessionService;



@WebServlet("/AddAirportServlet")
public class AddAirportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get form data
        String airportName = request.getParameter("airportName").trim();
        String airportCode = request.getParameter("airportCode").trim();
        String city = request.getParameter("city").trim();
        String country = request.getParameter("country").trim();
        String runwaysStr = request.getParameter("runways").trim();

        // Validate input
        if (airportName.isEmpty() || airportCode.isEmpty() || city.isEmpty() || 
            country.isEmpty() || runwaysStr.isEmpty()) {
            response.getWriter().write("Error: All fields are required.");
            return;
        }

        if (!airportCode.matches("[A-Z]{3}")) {
            response.getWriter().write("Error: Airport Code must be exactly 3 uppercase letters.");
            return;
        }

        int runways;
        try {
            runways = Integer.parseInt(runwaysStr);
            if (runways <= 0) {
                response.getWriter().write("Error: Number of Runways must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("Error: Invalid number of runways.");
            return;
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Database connection
            String dbURL = "jdbc:mysql://localhost:3306/flightmate";
            String dbUser = "root";
            String dbPassword = "12345678"; 
            
             
            
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException("Failed to load MySQL driver", e);
                }          
         

            
            

            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            // Insert data into the database
            String sql = "INSERT INTO airports (airport_name, airport_code, city, country, runways) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, airportName);
            ps.setString(2, airportCode);
            ps.setString(3, city);
            ps.setString(4, country);
            ps.setInt(5, runways);

            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                //response.getWriter().write("Airport added successfully!");
            	response.sendRedirect("listAirports.jsp");
            } else {
                response.getWriter().write("Failed to add airport.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

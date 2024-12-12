package com.flightmate.servlets.airport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flightmate.beans.Aircraft;
import com.flightmate.beans.Airport;
import com.flightmate.beans.User;
import com.flightmate.dao.AircraftDao;
import com.flightmate.dao.AirportDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

@WebServlet("/airport")
public class AirportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}
		
		if (user.getRole().equals(Role.PILOT)) {
			req.setAttribute("disabledInput", "disabled");
		}
		
		String action = req.getParameter("action");
        if ("viewAircraft".equals(action)) {
            try {
				viewAircraft(req, resp);
			} catch (ClassNotFoundException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
        List<Airport> airports = AirportDao.getDao().getAllAirports();
        
        req.setAttribute("airports", airports);		
		
						
		req.getRequestDispatcher(Route.AIRPORT).forward(req, resp);
	}
	 private void viewAircraft(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException {
	        int airportId = Integer.parseInt(request.getParameter("airportId"));
	        List<Aircraft> aircraftList = AircraftDao.getDao().getAircraftByAirportId(airportId);
	        request.setAttribute("aircraftList", aircraftList);  
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/aircraft_list.jsp");
	        dispatcher.forward(request, response);
	    }
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect(Route.LOGIN);
			return;
		}

        // Get form data
        String airportName = req.getParameter("airportName").trim();
        String airportCode = req.getParameter("airportCode").trim().toUpperCase();
        String city = req.getParameter("city").trim();
        String country = req.getParameter("country").trim();
        String runwaysStr = req.getParameter("runways").trim();

        // Validate input
        if (airportName.isEmpty() || airportCode.isEmpty() || city.isEmpty() || 
            country.isEmpty() || runwaysStr.isEmpty()) {
            resp.getWriter().write("Error: All fields are required.");
            return;
        }

        if (!airportCode.matches("[A-Z]{3}")) {
            resp.getWriter().write("Error: Airport Code must be exactly 3 uppercase letters.");
            return;
        }

        int runways;
        try {
            runways = Integer.parseInt(runwaysStr);
            if (runways <= 0) {
                resp.getWriter().write("Error: Number of Runways must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            resp.getWriter().write("Error: Invalid number of runways.");
            return;
        }       
        
        
     // Create an airport object
        Airport airport = new Airport(0, airportName, airportCode, city, country, runways, LocalDateTime.now());
        AirportDao dao = AirportDao.getDao();
        
        try {
            dao.createAirport(airport);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("Error: " + e.getMessage());
        }        
		
		doGet(req, resp);
	}

}

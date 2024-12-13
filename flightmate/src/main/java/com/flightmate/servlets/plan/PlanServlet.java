package com.flightmate.servlets.plan;

import com.flightmate.beans.Airport;
import com.flightmate.beans.Plan;
import com.flightmate.beans.User;
import com.flightmate.dao.AirportDao;
import com.flightmate.dao.PlanDao;
import com.flightmate.libs.Role;
import com.flightmate.libs.Route;
import com.flightmate.libs.services.SessionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/planServlet")
public class PlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));

        PlanDao dao = PlanDao.getDao();
        if ("Add".equals(action)) {
            String aircraft_model = request.getParameter("aircraftModel");
            String departure_datetime = request.getParameter("departureDatetime");
            String departure_airport_code = request.getParameter("departureAirportCode");
            String arrival_datetime = request.getParameter("arrivalDatetime");
            String arrival_airport_code = request.getParameter("arrivalAirportCode");
            String planned_flight_duration = request.getParameter("plannedFlightDuration");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date departureTime = null, arrivalTime = null;
            Double actualFlightDuration = null;
            if (departure_datetime != null) {
                try {
                    departureTime = format.parse(departure_datetime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            if (arrival_datetime != null) {
                try {
                    arrivalTime = format.parse(arrival_datetime);
                    if (departure_datetime != null) {
                        actualFlightDuration = (arrivalTime.getTime() - departureTime.getTime()) / 1000d / 60;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            Plan plan = new Plan(id, aircraft_model, departureTime, departure_airport_code, arrivalTime, arrival_airport_code, planned_flight_duration == null ? null : Double.parseDouble(planned_flight_duration), actualFlightDuration, "WAIF");
            try {
                dao.createPlan(plan);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Create to update plan.");
            }
            response.sendRedirect("/planServlet?flag=Add");
        } else if ("Update".equals(action)) {
            String aircraft_model = request.getParameter("aircraftModel");
            String departure_datetime = request.getParameter("departureDatetime");
            String departure_airport_code = request.getParameter("departureAirportCode");
            String arrival_datetime = request.getParameter("arrivalDatetime");
            String arrival_airport_code = request.getParameter("arrivalAirportCode");
            String planned_flight_duration = request.getParameter("plannedFlightDuration");
            String status = request.getParameter("status");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
            Date departureTime = null, arrivalTime = null;
            Double actualFlightDuration = null;
            if (departure_datetime != null) {
                try {
                    departureTime = format.parse(departure_datetime);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            if (arrival_datetime != null) {
                try {
                    arrivalTime = format.parse(arrival_datetime);
                    if (departure_datetime != null) {
                        actualFlightDuration = (arrivalTime.getTime() - departureTime.getTime()) / 1000d / 60;
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            Plan plan = new Plan(id, aircraft_model, departureTime, departure_airport_code, arrivalTime, arrival_airport_code, planned_flight_duration == null ? null : Double.parseDouble(planned_flight_duration), actualFlightDuration, status);
            try {
                dao.updatePlan(plan);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to update plan.");
            }
            response.sendRedirect("/planServlet?flag=Update");
        } else if ("Delete".equals(action)) {
            try {
                dao.deletePlan(id);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete plan.");
            }
            response.sendRedirect("/planServlet?flag=Delete");
        } else if ("Verify".equals(action)) {
            try {
                String status = request.getParameter("status");
                Plan plan = dao.getPlanById(id);
                plan.setStatus(status);
                dao.updatePlan(plan);
                response.sendRedirect("/planServlet?flag=Verify");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete plan.");
            }
        } else {
            try {
                AirportDao airportDao = AirportDao.getDao();
                List<Airport> airports = airportDao.getAllAirports();
                request.setAttribute("airports", airports);

                List<Plan> list = dao.getAllPlan();
                request.setAttribute("list", list);
                request.getRequestDispatcher("/plan.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to delete plan.");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);

        if (user == null) {
            resp.sendRedirect(Route.LOGIN);
            return;
        }

        if (user.getRole().equals(Role.ADMINISTRATOR)) {
            req.setAttribute("disabledInput", "disabled");
        }
        this.doPost(req, resp);
    }
}
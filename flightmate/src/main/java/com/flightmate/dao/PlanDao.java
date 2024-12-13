package com.flightmate.dao;

import com.flightmate.beans.Plan;
import com.flightmate.libs.builders.PlanBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
    private static PlanDao dao;

    private PlanDao() {
    }

    public static synchronized PlanDao getDao() {
        if (dao == null) dao = new PlanDao();
        return dao;
    }

    public void createPlan(Plan plan) {
        String sql = "INSERT INTO plan (aircraft_model, departure_datetime, departure_airport_code, arrival_datetime, arrival_airport_code,planned_flight_duration,actual_flight_duration,status) VALUES (?, ?, ?, ?, ?,?,?,?)";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plan.getAircraftModel());
            ps.setTimestamp(2, new Timestamp(plan.getDepartureDatetime().getTime()));
            ps.setString(3, plan.getDepartureAirportCode());
            ps.setTimestamp(4, new Timestamp(plan.getArrivalDatetime().getTime()));
            ps.setString(5, plan.getArrivalAirportCode());
            ps.setDouble(6, plan.getPlannedFlightDuration());
            ps.setDouble(7, plan.getActualFlightDuration());
            ps.setString(8, plan.getStatus());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted == 0) {
                throw new SQLException("Failed to add plan.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get All Plan
    public List<Plan> getAllPlan() {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM plan";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                plans.add(new PlanBuilder()
                        .setId(rs.getInt("id"))
                        .setAircraftModel(rs.getString("aircraft_model"))
                        .setDepartureDatetime(rs.getTimestamp("departure_datetime"))
                        .setDepartureAirportCode(rs.getString("departure_airport_code"))
                        .setArrivalDatetime(rs.getTimestamp("arrival_datetime"))
                        .setArrivalAirportCode(rs.getString("arrival_airport_code"))
                        .setPlannedFlightDuration(rs.getDouble("planned_flight_duration"))
                        .setActualFlightDuration(rs.getDouble("actual_flight_duration"))
                        .setStatus(rs.getString("status"))
                        .create());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return plans;
    }

    public void updatePlan(Plan plan) {
        String sql = "UPDATE plan SET aircraft_model = ?, departure_datetime = ?, departure_airport_code = ?, arrival_datetime = ?, arrival_airport_code = ?,planned_flight_duration=?,actual_flight_duration=?,status=? WHERE id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, plan.getAircraftModel());
            stmt.setTimestamp(2, new Timestamp(plan.getDepartureDatetime().getTime()));
            stmt.setString(3, plan.getDepartureAirportCode());
            stmt.setTimestamp(4, new Timestamp(plan.getArrivalDatetime().getTime()));
            stmt.setString(5, plan.getArrivalAirportCode());
            stmt.setDouble(6, plan.getPlannedFlightDuration());
            stmt.setDouble(7, plan.getActualFlightDuration());
            stmt.setString(8, plan.getStatus());
            stmt.setInt(9, plan.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating plan failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Handle exceptions appropriately in production
        }
    }

    public void deletePlan(int planId) {
        String sql = "DELETE FROM plan WHERE id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, planId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting plan failed, no rows affected.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  // Handle exceptions appropriately in production
        }
    }

    public Plan getPlanById(int planId) {
        Plan plan = null;
        String sql = "SELECT * FROM plan WHERE id = ?";
        try (
                Connection conn = DBConnection.getDBInstance();
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, planId);
            ResultSet rs = stmt.executeQuery();

            if (rs != null && rs.next()) {
                plan = new PlanBuilder()
                        .setId(rs.getInt("id"))
                        .setAircraftModel(rs.getString("aircraft_model"))
                        .setDepartureDatetime(rs.getTimestamp("departure_datetime"))
                        .setDepartureAirportCode(rs.getString("departure_airport_code"))
                        .setArrivalDatetime(rs.getTimestamp("arrival_datetime"))
                        .setArrivalAirportCode(rs.getString("arrival_airport_code"))
                        .setPlannedFlightDuration(rs.getDouble("planned_flight_duration"))
                        .setActualFlightDuration(rs.getDouble("actual_flight_duration"))
                        .setStatus(rs.getString("status"))
                        .create();
            }

            if (rs != null) rs.close();
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        }
        return plan;
    }
}
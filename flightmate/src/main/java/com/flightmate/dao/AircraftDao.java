package com.flightmate.dao;

import com.flightmate.beans.Aircraft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AircraftDao {
	private static AircraftDao dao;
	private AircraftDao() {}
	
	public static synchronized AircraftDao getDao() {
		if (dao == null) dao = new AircraftDao();
		return dao;
	}

    public static boolean addAircraft(Aircraft aircraft) {
        String sql = "INSERT INTO Aircrafts (aircraft_model, manufacture_date, last_maintenance_date, next_maintenance_date, aircraft_notes, created_at, administrator_id, airport_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aircraft.getAircraftModel());
            stmt.setDate(2, java.sql.Date.valueOf(aircraft.getManufactureDate()));
            stmt.setDate(3, java.sql.Date.valueOf(aircraft.getLastMaintenanceDate()));
            stmt.setDate(4, java.sql.Date.valueOf(aircraft.getNextMaintenanceDate()));
            stmt.setString(5, aircraft.getAircraftNotes());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(aircraft.getCreatedAt()));
            stmt.setInt(7, aircraft.getAdministratorId());
            stmt.setInt(8, aircraft.getAirportId());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Aircraft> getAircraftByAirportId(int airportId) throws ClassNotFoundException {
        List<Aircraft> aircraftList = new ArrayList<>();
        System.out.println("Connecting to database...");
        String sql = "SELECT * FROM Aircrafts WHERE airport_id = ?";
        try (Connection conn = DBConnection.getDBInstance();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, airportId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Aircraft aircraft = new Aircraft(rs.getInt("aircraft_id"), 
                		                         rs.getString("aircraft_model"), 
                		                         rs.getDate("manufacture_date").toLocalDate(), 
                		                         rs.getDate("last_maintenance_date").toLocalDate(),
                		                         rs.getDate("next_maintenance_date").toLocalDate(), 
                		                         rs.getString("aircraft_notes"), 
                		                         rs.getTimestamp("created_at").toLocalDateTime(), 
                		                         rs.getInt("administrator_id"), 
                		                         rs.getInt("airport_id"));
                // Add more fields as necessary
                aircraftList.add(aircraft);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions or throw them as needed
        }
        return aircraftList;
    }

 
}

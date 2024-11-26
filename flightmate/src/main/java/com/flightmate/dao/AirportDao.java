package com.flightmate.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.flightmate.beans.Airport;
import com.flightmate.libs.Role;
import com.flightmate.libs.builders.AirportBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AirportDao {
	private static AirportDao dao;
	public static final String Airport_ID = "id";
	public static final String AIRPORT_NAME = "airport_name";
	public static final String AIRPORT_CODE = "airport_code";
	public static final String CITY = "city";
	public static final String COUNTRY = "country";
	public static final String RUNWAYS = "runways";
	
	
	private AirportDao() {}
	
	public static synchronized AirportDao getDao() {
		if (dao == null) dao = new AirportDao();
		return dao;
	}
	
	public void createAirport(HttpServletRequest req) {
		String airport_name = req.getParameter("airport_name");
		String airport_code = req.getParameter("airport_code");
		String city = req.getParameter("city");
		String country = req.getParameter("country");
		int runways = Role.toRoleId(req.getParameter("runways"));
		
		String sql = "INSERT INTO "+ApplicationDao.AIRPORTS_TABLE+" ("+AIRPORT_NAME+","+AIRPORT_CODE+","+CITY+","+COUNTRY+","+RUNWAYS+") VALUES (?, ?, ?, ?, ?)";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, airport_name);
    		stmt.setString(2, airport_code);
    		stmt.setString(3, city);
    		stmt.setString(4, country);
    		stmt.setInt(5, runways);
    		
    		stmt.executeUpdate();
    		
    		ResultSet rs = stmt.getGeneratedKeys();
    		
    		if (rs != null && rs.next()) {
    			HttpSession session = req.getSession();
    			session.setAttribute("Airport", new AirportBuilder()
    					.setAirportId(rs.getInt(1))
    					.setName(airport_name)
    					.setCode(airport_code)
    					.setCity(city)
    					.setCountry(country)
    					.setRunways(runways)
    					.create());
    		}
    		
    		if (rs != null) rs.close();
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateAirport(String airport_name, String airport_code, String city, String country, int runways,  int AirportId) {
		boolean updated = false;
		String sql = "UPDATE "+ApplicationDao.AIRPORTS_TABLE+" SET "
				+AIRPORT_NAME+" = ?, "
				+AIRPORT_CODE+" = ?, "
				+CITY+" = ?, "
				+COUNTRY+" = ?, "
				+RUNWAYS+" = ? "
				+ " WHERE "+Airport_ID+" = ?";
		
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
    		stmt.setString(1, airport_name);
    		stmt.setString(2, airport_code);
    		stmt.setString(3, city);
    		stmt.setString(4, country);
    		stmt.setInt(5, runways);
    		stmt.setInt(6, AirportId);
    		
    		updated = stmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return updated;
	}
		
	public Airport getAirportById(int AirportId) {
		Airport Airport = null;
		String sql = "SELECT "+Airport_ID+", "+AIRPORT_NAME+", "+AIRPORT_CODE+", "+CITY+", "+COUNTRY + ", " + RUNWAYS +" FROM " +ApplicationDao.AIRPORTS_TABLE+" WHERE " + Airport_ID + " = ?";
		try (
				Connection conn = DBConnection.getDBInstance();
				PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, AirportId);
			ResultSet rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				Airport = new AirportBuilder()
						.setAirportId(AirportId)
						.setName(rs.getString(AIRPORT_NAME))
						.setCode(rs.getString(AIRPORT_CODE))
						.setCity(rs.getString(CITY))
						.setCountry(rs.getString(COUNTRY))
						.setRunways(rs.getInt(RUNWAYS))
						.create();
				}
						
			if (rs != null) rs.close();
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		return Airport;
	}	
	
	
}

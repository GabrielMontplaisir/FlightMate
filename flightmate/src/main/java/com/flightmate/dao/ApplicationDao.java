package com.flightmate.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationDao {
    private static ApplicationDao dao;
    public static final String DB_NAME = "flightmate";
    public static final String USERS_TABLE = "users";
    public static final String ROLES_TABLE = "roles";
    public static final String AIRCRAFT_TABLE = "aircrafts";
    public static final String AIRPORT_TABLE = "airports";
    
    private ApplicationDao() {}

    public static synchronized ApplicationDao getDao() {
        if (dao == null) dao = new ApplicationDao();
        return dao;
    }

    public static void createDatabase() {
        try (
                Connection conn = DBConnection.getDBInstance();
                ResultSet resultSet = conn.getMetaData().getCatalogs();
                Statement stmt = conn.createStatement();
            ) {
            if (!dbExists(DB_NAME, resultSet)) {
                System.out.print("Creating DB...");
                String sql = "CREATE DATABASE IF NOT EXISTS "+ DB_NAME +" DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci";
                stmt.executeUpdate(sql);
                System.out.println("Created DB");
            }
            DBUtil.setConnStr();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createRolesTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
            if (!tableExists(conn, ROLES_TABLE)) {
                System.out.print("Creating Roles Table...");
                String sql = "CREATE TABLE IF NOT EXISTS "+ ROLES_TABLE +" ("
                        + "role_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "role_name VARCHAR(25) NOT NULL, "
                        + "role_description VARCHAR(240) NOT NULL);";
                stmt.executeUpdate(sql);
                
                sql = "INSERT INTO " + ROLES_TABLE + "(role_name, role_description) VALUES "
                		+ "('PILOT', 'Default user. Can log flight hours and schedule own flights.'),"
                		+ "('ADMINISTRATOR', 'Can approve/reject flight hours, manage aircrafts and airports.')";
                
                stmt.executeUpdate(sql);
                
                System.out.println("Created Roles Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createUserTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
            ) {
            if (!tableExists(conn, USERS_TABLE)) {
                System.out.print("Creating User Table...");
                String sql = "CREATE TABLE IF NOT EXISTS "+ USERS_TABLE +" ("
                        + "user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "role_id INT NOT NULL, "
                        + "email_address VARCHAR(128) NOT NULL UNIQUE, "
                        + "first_name VARCHAR(25) NOT NULL, "
                        + "last_name VARCHAR(25) NOT NULL, "
                        + "password VARCHAR(64) NOT NULL, "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(), "
                        + "updated_at TIMESTAMP, "
                        + "deleted_at TIMESTAMP, "
                        + "FOREIGN KEY (role_id) REFERENCES " + ROLES_TABLE + "(role_id));";
                stmt.executeUpdate(sql);
                System.out.println("Created User Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createAirportTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, AIRPORT_TABLE)) {
                System.out.print("Creating Airports Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + AIRPORT_TABLE + " ("
                        + "airport_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "airport_code VARCHAR(10) NOT NULL UNIQUE, "
                        + "airport_name VARCHAR(100) NOT NULL, "
                        + "city VARCHAR(50) NOT NULL, "
                        + "country VARCHAR(50) NOT NULL, "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP());";
                stmt.executeUpdate(sql);
                System.out.println("Created Airports Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createAircraftTable() {
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, AIRCRAFT_TABLE)) {
                System.out.print("Creating Aircrafts Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + AIRCRAFT_TABLE + " ("
                        + "aircraft_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "aircraft_model VARCHAR(50) NOT NULL, "
                        + "manufacture_date DATE NOT NULL, "
                        + "last_maintenance_date DATE NOT NULL, "
                        + "next_maintenance_date DATE NOT NULL, "
                        + "aircraft_notes TEXT, "
                        + "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP(), "
                        + "administrator_id INT NOT NULL, "
                        + "airport_id INT NOT NULL, "
                        + "FOREIGN KEY (administrator_id) REFERENCES " + USERS_TABLE + "(user_id), "
                        + "FOREIGN KEY (airport_id) REFERENCES " + AIRPORT_TABLE + "(airport_id));";
                stmt.executeUpdate(sql);
                System.out.println("Created Aircrafts Table");
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
 
    private static boolean dbExists(String dbName, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(dbName)) return true;
        }
        return false;
    }

    public boolean tableExists(Connection conn, String tableName) throws SQLException {
        return conn.getMetaData().getTables(DB_NAME, null, tableName, new String[] {"TABLE"}).next();
    }
    
    
 // Sample data for developing purpose
    
    public void insertSampleUsers() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO " + USERS_TABLE + " (role_id, email_address, first_name, last_name, password) VALUES "
                + "(1, 'pilot1@example.com', 'John', 'Doe', 'password123'), "
                + "(2, 'admin1@example.com', 'Jane', 'Smith', 'password123');";
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Sample users inserted.");
        }
    }

    public void insertSampleAirports() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO " + AIRPORT_TABLE + " (airport_code, airport_name, city, country) VALUES "
                + "('JFK', 'John F. Kennedy International Airport', 'New York', 'USA'), "
                + "('LHR', 'Heathrow Airport', 'London', 'UK'), "
                + "('DXB', 'Dubai International Airport', 'Dubai', 'UAE');";
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Sample airports inserted.");
        }
    }

    public void insertSampleAircrafts() throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO " + AIRCRAFT_TABLE + " (aircraft_model, manufacture_date, last_maintenance_date, "
                + "next_maintenance_date, aircraft_notes, administrator_id, airport_id) VALUES "
                + "('Boeing 747', '2000-01-01', '2024-01-01', '2025-01-01', 'Flagship aircraft.', 2, 1), "
                + "('Airbus A380', '2010-01-01', '2023-06-15', '2024-06-15', 'Double-decker aircraft.', 2, 2);";
        try (
                Connection conn = DBConnection.getDBInstance();
                Statement stmt = conn.createStatement();
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Sample aircrafts inserted.");
        }
    }
}

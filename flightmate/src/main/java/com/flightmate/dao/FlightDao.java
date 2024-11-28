package com.flightmate.dao;

package com.flightmate.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.flightmate.dao.DBConnection;

public class FlightDao {
    public void updateFlightStatus(int flightId, String newStatus) throws SQLException, ClassNotFoundException {
        String query = "UPDATE flights SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, flightId);
            stmt.executeUpdate();
        }
    }
}
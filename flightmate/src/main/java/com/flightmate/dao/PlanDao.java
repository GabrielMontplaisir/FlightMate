//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.flightmate.dao;

import com.flightmate.beans.Plan;
import com.flightmate.libs.builders.PlanBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlanDao {
	private static PlanDao dao;

	private PlanDao() {
	}

	public static synchronized PlanDao getDao() {
		if (dao == null) {
			dao = new PlanDao();
		}

		return dao;
	}

	public void createPlan(Plan plan) {
		String sql = "INSERT INTO plan (aircraft_model, departure_datetime, departure_airport_code, arrival_datetime, arrival_airport_code,planned_flight_duration,actual_flight_duration,status) VALUES (?, ?, ?, ?, ?,?,?,?)";

		try {
			Throwable e = null;
			Object var4 = null;

			try {
				Connection conn = DBConnection.getDBInstance();

				try {
					PreparedStatement ps = conn.prepareStatement(sql);

					try {
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
					} finally {
						if (ps != null) {
							ps.close();
						}

					}
				} catch (Throwable var21) {
					if (e == null) {
						e = var21;
					} else if (e != var21) {
						e.addSuppressed(var21);
					}

					if (conn != null) {
						conn.close();
					}

					throw e;
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Throwable var22) {
				if (e == null) {
					e = var22;
				} else if (e != var22) {
					e.addSuppressed(var22);
				}

				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Plan> getAllPlan() {
		List<Plan> plans = new ArrayList();
		String sql = "SELECT * FROM plan";

		try {
			Throwable e = null;
			Object var4 = null;

			try {
				Connection conn = DBConnection.getDBInstance();

				try {
					PreparedStatement stmt = conn.prepareStatement(sql);

					try {
						ResultSet rs = stmt.executeQuery();

						try {
							while(rs.next()) {
								plans.add((new PlanBuilder()).setId(rs.getInt("id")).setAircraftModel(rs.getString("aircraft_model")).setDepartureDatetime(rs.getTimestamp("departure_datetime")).setDepartureAirportCode(rs.getString("departure_airport_code")).setArrivalDatetime(rs.getTimestamp("arrival_datetime")).setArrivalAirportCode(rs.getString("arrival_airport_code")).setPlannedFlightDuration(rs.getDouble("planned_flight_duration")).setActualFlightDuration(rs.getDouble("actual_flight_duration")).setStatus(rs.getString("status")).create());
							}
						} finally {
							if (rs != null) {
								rs.close();
							}

						}
					} catch (Throwable var29) {
						if (e == null) {
							e = var29;
						} else if (e != var29) {
							e.addSuppressed(var29);
						}

						if (stmt != null) {
							stmt.close();
						}

						throw e;
					}

					if (stmt != null) {
						stmt.close();
					}
				} catch (Throwable var30) {
					if (e == null) {
						e = var30;
					} else if (e != var30) {
						e.addSuppressed(var30);
					}

					if (conn != null) {
						conn.close();
					}

					throw e;
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Throwable var31) {
				if (e == null) {
					e = var31;
				} else if (e != var31) {
					e.addSuppressed(var31);
				}

				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return plans;
	}

	public void updatePlan(Plan plan) {
		String sql = "UPDATE plan SET aircraft_model = ?, departure_datetime = ?, departure_airport_code = ?, arrival_datetime = ?, arrival_airport_code = ?,planned_flight_duration=?,actual_flight_duration=?,status=? WHERE id = ?";

		try {
			Throwable e = null;
			Object var4 = null;

			try {
				Connection conn = DBConnection.getDBInstance();

				try {
					PreparedStatement stmt = conn.prepareStatement(sql);

					try {
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
					} finally {
						if (stmt != null) {
							stmt.close();
						}

					}
				} catch (Throwable var21) {
					if (e == null) {
						e = var21;
					} else if (e != var21) {
						e.addSuppressed(var21);
					}

					if (conn != null) {
						conn.close();
					}

					throw e;
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Throwable var22) {
				if (e == null) {
					e = var22;
				} else if (e != var22) {
					e.addSuppressed(var22);
				}

				throw e;
			}
		} catch (ClassNotFoundException | SQLException e) {
			((Exception)e).printStackTrace();
		}

	}

	public void deletePlan(int planId) {
		String sql = "DELETE FROM plan WHERE id = ?";

		try {
			Throwable e = null;
			Object var4 = null;

			try {
				Connection conn = DBConnection.getDBInstance();

				try {
					PreparedStatement stmt = conn.prepareStatement(sql);

					try {
						stmt.setInt(1, planId);
						int affectedRows = stmt.executeUpdate();
						if (affectedRows == 0) {
							throw new SQLException("Deleting plan failed, no rows affected.");
						}
					} finally {
						if (stmt != null) {
							stmt.close();
						}

					}
				} catch (Throwable var21) {
					if (e == null) {
						e = var21;
					} else if (e != var21) {
						e.addSuppressed(var21);
					}

					if (conn != null) {
						conn.close();
					}

					throw e;
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Throwable var22) {
				if (e == null) {
					e = var22;
				} else if (e != var22) {
					e.addSuppressed(var22);
				}

				throw e;
			}
		} catch (ClassNotFoundException | SQLException e) {
			((Exception)e).printStackTrace();
		}

	}

	public Plan getPlanById(int planId) {
		Plan plan = null;
		String sql = "SELECT * FROM plan WHERE id = ?";

		try {
			Throwable e = null;
			Object var5 = null;

			try {
				Connection conn = DBConnection.getDBInstance();

				try {
					PreparedStatement stmt = conn.prepareStatement(sql);

					try {
						stmt.setInt(1, planId);
						ResultSet rs = stmt.executeQuery();
						if (rs != null && rs.next()) {
							plan = (new PlanBuilder()).setId(rs.getInt("id")).setAircraftModel(rs.getString("aircraft_model")).setDepartureDatetime(rs.getTimestamp("departure_datetime")).setDepartureAirportCode(rs.getString("departure_airport_code")).setArrivalDatetime(rs.getTimestamp("arrival_datetime")).setArrivalAirportCode(rs.getString("arrival_airport_code")).setPlannedFlightDuration(rs.getDouble("planned_flight_duration")).setActualFlightDuration(rs.getDouble("actual_flight_duration")).setStatus(rs.getString("status")).create();
						}

						if (rs != null) {
							rs.close();
						}
					} finally {
						if (stmt != null) {
							stmt.close();
						}

					}
				} catch (Throwable var25) {
					if (e == null) {
						e = var25;
					} else if (e != var25) {
						e.addSuppressed(var25);
					}

					if (conn != null) {
						conn.close();
					}

					throw e;
				}

				if (conn != null) {
					conn.close();
				}
			} catch (Throwable var26) {
				if (e == null) {
					e = var26;
				} else if (e != var26) {
					e.addSuppressed(var26);
				}

				throw e;
			}
		} catch (SQLException e) {
			DBUtil.processException(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return plan;
	}
}

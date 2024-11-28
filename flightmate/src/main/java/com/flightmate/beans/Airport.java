package com.flightmate.beans;

public class Airport {
    private int airportId;
	private String airportName, city, country;
	private String airportCode;
	private int runways;	
	
	// Constructor	
	public Airport(int airportId, String airportName, String airportCode, String city, String country, int runways) {
		this.airportId = airportId;
		this.airportName = airportName;
		this.airportCode = airportCode;
		this.city = city;
		this.country = country;
		this.runways = runways;
	}
	
	// Getter / Setter Methods
	
	public int getAirportId() {return airportId;}
		
	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public int getRunways() {
		return runways;
	}

	public void setRunways(int runways) {
		this.runways = runways;
	}	
}
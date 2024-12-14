package com.flightmate.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Plan {

    private int id;
    private String aircraftModel;
    private Date departureDatetime;
    private String departureAirportCode;
    private Date arrivalDatetime;
    private String arrivalAirportCode;
    private Double plannedFlightDuration;
    private Double actualFlightDuration;
    private String status;

    public Plan(int id,String aircraftModel, Date departureDatetime, String departureAirportCode,
                Date arrivalDatetime, String arrivalAirportCode,
                Double plannedFlightDuration, Double actualFlightDuration, String status) {
        this.id=id;
        this.aircraftModel = aircraftModel;
        this.departureDatetime = departureDatetime;
        this.departureAirportCode = departureAirportCode;
        this.arrivalDatetime = arrivalDatetime;
        this.arrivalAirportCode = arrivalAirportCode;
        this.plannedFlightDuration = plannedFlightDuration;
        this.actualFlightDuration = actualFlightDuration;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public Date getDepartureDatetime() {
        return departureDatetime;
    }

    public String getDepartureDatetimeStr() {
        Date departureDatetime = getDepartureDatetime();
        if(departureDatetime==null){
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String formattedDate = (departureDatetime != null) ? sdf.format(departureDatetime) : "";
        return formattedDate;
    }

    public void setDepartureDatetime(Date departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public Date getArrivalDatetime() {
        return arrivalDatetime;
    }

    public String getArrivalDatetimeStr() {
        Date arrivalDatetime = getArrivalDatetime();
        if(arrivalDatetime==null){
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String formattedDate = (arrivalDatetime != null) ? sdf.format(arrivalDatetime) : "";
        return formattedDate;
    }

    public void setArrivalDatetime(Date arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public Double getPlannedFlightDuration() {
        return plannedFlightDuration;
    }

    public void setPlannedFlightDuration(Double plannedFlightDuration) {
        this.plannedFlightDuration = plannedFlightDuration;
    }

    public Double getActualFlightDuration() {
        return actualFlightDuration;
    }

    public void setActualFlightDuration(Double actualFlightDuration) {
        this.actualFlightDuration = actualFlightDuration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

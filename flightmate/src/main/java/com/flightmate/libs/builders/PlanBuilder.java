//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.flightmate.libs.builders;

import com.flightmate.beans.Plan;
import java.util.Date;

public class PlanBuilder {
    private int id;
    private String aircraftModel;
    private Date departureDatetime;
    private String departureAirportCode;
    private Date arrivalDatetime;
    private String arrivalAirportCode;
    private Double plannedFlightDuration;
    private Double actualFlightDuration;
    private String status;

    public PlanBuilder() {
    }

    public PlanBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PlanBuilder setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    public PlanBuilder setDepartureDatetime(Date departureDatetime) {
        this.departureDatetime = departureDatetime;
        return this;
    }

    public PlanBuilder setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
        return this;
    }

    public PlanBuilder setArrivalDatetime(Date arrivalDatetime) {
        this.arrivalDatetime = arrivalDatetime;
        return this;
    }

    public PlanBuilder setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
        return this;
    }

    public PlanBuilder setPlannedFlightDuration(Double plannedFlightDuration) {
        this.plannedFlightDuration = plannedFlightDuration;
        return this;
    }

    public PlanBuilder setActualFlightDuration(Double actualFlightDuration) {
        this.actualFlightDuration = actualFlightDuration;
        return this;
    }

    public PlanBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Plan create() {
        return new Plan(this.id, this.aircraftModel, this.departureDatetime, this.departureAirportCode, this.arrivalDatetime, this.arrivalAirportCode, this.plannedFlightDuration, this.actualFlightDuration, this.status);
    }
}

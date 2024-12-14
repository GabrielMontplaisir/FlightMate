<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="./components/head.jsp" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
    <title>FlightMate | List of Plans</title>
</head>
<body class="background">
<jsp:include page='./components/header.jsp' />
<main class="center">
    <h1>Plan Management</h1>
    <c:if test="${user.getRole().equals(roles['PILOT'])}">
        <section class="container">
            <h2 class="section-title">Add an Plan</h2>
            <form action="/planServlet?action=Add" method="POST" onsubmit="return validateForm()">
                <fieldset class="form-group mt-2">
                    <label for="aircraftModel" class="form-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Aircraft Model:</label>
                    <input type="text" id="aircraftModel" name="aircraftModel" class="form-input" required><br>
                </fieldset>
                <fieldset class="form-group mt-2">
                    <label for="departureDatetime" class="form-label">Departure Date and Time:</label>
                    <input type="datetime-local" id="departureDatetime" name="departureDatetime" maxlength="3" class="form-input" required><br>

                    <label for="departureAirportCode" class="form-label">Departure Airport:</label>
                    <select id="departureAirportCode" name="departureAirportCode" required class="form-input">
                        <c:forEach var="item" items="${airports}">
                            <option value="${item.airportCode}">${item.airportName}</option>
                        </c:forEach>
                    </select><br>
                </fieldset>
                <fieldset class="form-group mt-2">
                    <label for="arrivalDatetime" class="form-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Arrival Date Time:</label>
                    <input type="datetime-local" id="arrivalDatetime" name="arrivalDatetime" class="form-input" required><br>

                    <label for="arrivalAirportCode" class="form-label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Arrival Airport:</label>
                    <select id="arrivalAirportCode" name="arrivalAirportCode" required class="form-input">
                        <c:forEach var="item" items="${airports}">
                            <option value="${item.airportCode}">${item.airportName}</option>
                        </c:forEach>
                    </select><br>
                </fieldset>

                <fieldset class="form-group mt-2">
                    <label for="plannedFlightDuration" class="form-label">&nbsp;&nbsp;Planned Flight Duration:</label>
                    <input type="number" id="plannedFlightDuration" name="plannedFlightDuration" min="1" class="form-input" required>Min<br>
                </fieldset>
                <button type="submit" class="form-btn mt-2">Add Plan</button>
            </form>
        </section>
    </c:if>
    <h2 class="section-title mt-2">List of Plans</h2>
    <table class="dashboard-table w-full border-2 rounded mt-2">
        <thead>
        <tr>
            <th>ID</th>
            <th>Aircraft Model</th>
            <th>Departure Date and Time</th>
            <th>Departure Airport Code</th>
            <th>Arrival Date and Time</th>
            <th>Arrival Airport Code</th>
            <th>Planned Flight Duration</th>
            <th>Actual Flight Duration</th>
            <th>Approval Status by Admin</th>
            <c:if test="${user.getRole().equals(roles['PILOT'])}">
                <th>Actions</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="plan" items="${list}">
            <tr>
                <form method="post" action="/planServlet?action=Update">
                    <td>${plan.getId()}<input type="hidden" name="id" value="${plan.getId()}" ${disabledInput}/></td>
                    <td><input type="text" class="form-input w-full" name="aircraftModel" value="${plan.getAircraftModel()}" ${disabledInput}/></td>
                    <td><input type="datetime-local" class="form-input w-full" name="departureDatetime" value="${plan.getDepartureDatetimeStr()}" ${disabledInput}/></td>
                    <td>
                        <select name="departureAirportCode" required class="form-input" ${disabledInput}>
                            <c:forEach var="item" items="${airports}">
                                <c:choose>
                                    <c:when test="${item.airportCode==plan.getDepartureAirportCode()}">
                                        <option value="${item.airportCode}" selected>${item.airportName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${item.airportCode}">${item.airportName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="datetime-local" class="form-input w-full" name="arrivalDatetime" value="${plan.getArrivalDatetimeStr()}" ${disabledInput}/></td>
                    <td>
                        <select name="arrivalAirportCode" required class="form-input" ${disabledInput}>
                            <c:forEach var="item" items="${airports}">
                                <c:choose>
                                    <c:when test="${item.airportCode==plan.getArrivalAirportCode()}">
                                        <option value="${item.airportCode}" selected>${item.airportName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${item.airportCode}">${item.airportName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="number" class="form-input w-full" name="plannedFlightDuration" value="${plan.getPlannedFlightDuration()}" ${disabledInput}/></td>
                    <td><input type="number" class="form-input w-full" name="actualFlightDuration" value="${plan.getActualFlightDuration()}" ${disabledInput}/></td>
                    <td>
                        <c:if test="${user.getRole().equals(roles['PILOT'])}">
                            <input type="text" class="form-input w-full" name="status" value="${plan.getStatus()}" readonly/>
                        </c:if>
                        <c:if test="${user.getRole().equals(roles['ADMINISTRATOR'])}">
                            <select id="status" name="status" onchange="verify(${plan.getId()},this.value)">
                                <option value="SUCCESS" ${'SUCCESS'==plan.getStatus()?'selected':""}>SUCCESS</option>
                                <option value="WAIT" ${'WAIT'==plan.getStatus()?'selected':""}>WAIT</option>
                            </select>
                        </c:if>
                    </td>
                    <c:if test="${user.getRole().equals(roles['PILOT'])}">
                        <td>
                            <input type="submit" name="action" value="Update" class="btn w-full" />
                            <input type="button" value="Delete" class="btn error w-full mt-2" onclick="deletePlan(${plan.getId()})" />
                        </td>
                    </c:if>
                </form>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
</body>
<script>
    function validateForm(){
        var departureDatetime=document.getElementById("departureDatetime").value;
        var arrivalDatetime=document.getElementById("arrivalDatetime").value;
        var departureDatetimeStamp=Date.parse(departureDatetime);
        var arrivalDatetimeStamp=Date.parse(arrivalDatetime);
        if(arrivalDatetimeStamp-departureDatetimeStamp<=0){
            alert("Arrival Date time must be greater than Departure Date time.")
            return false;
        }
        return true;
    }

    function deletePlan(id){
        if(confirm('Are you sure you want to delete this plan?')){
            window.location.href="/planServlet?action=Delete&id="+id;
        }
    }
    function verify(id,status){
        window.location.href="/planServlet?action=Verify&id="+id+"&status="+status;
    }
    var flag="${param.get("flag")}"
    switch (flag) {
        case "Add":
            alert("Plan add success!");
            break;
        case "Update":
            alert("Plan update success!");
            break;
        case "Delete":
            alert("Plan delete success!");
            break;
        case "Verify":
            alert("Plan verify success!");
            break;
    }
</script>
</html>
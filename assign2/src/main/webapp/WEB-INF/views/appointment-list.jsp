<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Appointment Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1, h2 {
            color: #333;
        }
        .nav {
            margin-bottom: 20px;
        }
        .nav a {
            margin-right: 15px;
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
        .nav a:hover {
            text-decoration: underline;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .btn {
            padding: 8px 12px;
            margin-right: 5px;
            text-decoration: none;
            color: white;
            border-radius: 4px;
            display: inline-block;
        }
        .btn-primary {
            background-color: #007bff;
        }
        .btn-cancel {
            background-color: #dc3545;
        }
        .btn:hover {
            opacity: 0.8;
        }
        .patient-card {
            background-color: white;
            padding: 15px;
            margin-bottom: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .patient-info {
            font-weight: bold;
            color: #007bff;
            margin-bottom: 10px;
        }
        .doctor-list {
            margin-left: 20px;
        }
    </style>
</head>
<body>
    <h1>Appointment Management System</h1>
    
    <div class="nav">
        <a href="${pageContext.request.contextPath}/patients">Patients</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/appointments">Appointments</a>
    </div>
    
    <a href="${pageContext.request.contextPath}/appointments?action=new" class="btn btn-primary">Book New Appointment</a>
    
    <h2>Appointments List</h2>
    
    <c:forEach var="patient" items="${patients}">
        <c:if test="${not empty patient.doctors}">
            <div class="patient-card">
                <div class="patient-info">
                    Patient: ${patient.name} (ID: ${patient.patientId})
                </div>
                <div class="doctor-list">
                    <strong>Consulting Doctors:</strong>
                    <table>
                        <thead>
                            <tr>
                                <th>Doctor Name</th>
                                <th>Specialization</th>
                                <th>Email</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="doctor" items="${patient.doctors}">
                                <tr>
                                    <td>${doctor.name}</td>
                                    <td>${doctor.specialization}</td>
                                    <td>${doctor.email}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/appointments?action=cancel&patientId=${patient.patientId}&doctorId=${doctor.doctorId}" 
                                           class="btn btn-cancel"
                                           onclick="return confirm('Are you sure you want to cancel this appointment?')">Cancel</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </c:forEach>
    
    <c:if test="${empty patients or patients.stream().allMatch(p -> empty p.doctors)}">
        <p>No appointments scheduled yet.</p>
    </c:if>
</body>
</html>

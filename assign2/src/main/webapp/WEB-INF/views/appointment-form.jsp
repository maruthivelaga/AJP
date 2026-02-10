<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Appointment</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1 {
            color: #333;
        }
        .form-container {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            max-width: 500px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            padding: 10px 20px;
            margin-right: 10px;
            text-decoration: none;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn-submit {
            background-color: #007bff;
        }
        .btn-cancel {
            background-color: #6c757d;
        }
        .btn:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <h1>Book New Appointment</h1>
    
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/appointments" method="post">
            <div class="form-group">
                <label for="patientId">Select Patient:</label>
                <select id="patientId" name="patientId" required>
                    <option value="">-- Select a Patient --</option>
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient.patientId}">${patient.name} (ID: ${patient.patientId})</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="doctorId">Select Doctor:</label>
                <select id="doctorId" name="doctorId" required>
                    <option value="">-- Select a Doctor --</option>
                    <c:forEach var="doctor" items="${doctors}">
                        <option value="${doctor.doctorId}">${doctor.name} - ${doctor.specialization}</option>
                    </c:forEach>
                </select>
            </div>
            
            <button type="submit" class="btn btn-submit">Book Appointment</button>
            <a href="${pageContext.request.contextPath}/appointments" class="btn btn-cancel">Cancel</a>
        </form>
    </div>
</body>
</html>

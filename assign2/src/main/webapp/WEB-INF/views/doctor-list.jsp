<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Doctor Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h1 {
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
        .btn-edit {
            background-color: #28a745;
        }
        .btn-delete {
            background-color: #dc3545;
        }
        .btn:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
    <h1>Doctor Management System</h1>
    
    <div class="nav">
        <a href="${pageContext.request.contextPath}/patients">Patients</a>
        <a href="${pageContext.request.contextPath}/doctors">Doctors</a>
        <a href="${pageContext.request.contextPath}/appointments">Appointments</a>
    </div>
    
    <a href="${pageContext.request.contextPath}/doctors?action=new" class="btn btn-primary">Add New Doctor</a>
    
    <h2>Doctor List</h2>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Specialization</th>
                <th>Email</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="doctor" items="${doctors}">
                <tr>
                    <td>${doctor.doctorId}</td>
                    <td>${doctor.name}</td>
                    <td>${doctor.specialization}</td>
                    <td>${doctor.email}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/doctors?action=edit&id=${doctor.doctorId}" class="btn btn-edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/doctors?action=delete&id=${doctor.doctorId}" 
                           class="btn btn-delete" 
                           onclick="return confirm('Are you sure you want to delete this doctor?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>

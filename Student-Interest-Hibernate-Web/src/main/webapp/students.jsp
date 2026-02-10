<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Students List</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 20px; }
        .actions { margin-bottom: 20px; display: flex; justify-content: space-between; align-items: center; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-success { background: #27ae60; color: white; }
        .btn-info { background: #3498db; color: white; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #667eea; color: white; }
        tr:hover { background: #f5f5f5; }
        .message { padding: 10px; background: #d4edda; color: #155724; border-radius: 5px; margin-bottom: 20px; }
        .interests-tag { display: inline-block; background: #e8f4f8; color: #2980b9; padding: 4px 8px; border-radius: 3px; margin: 2px; font-size: 12px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>👨‍🎓 Students</h1>
        
        <c:if test="${message != null}">
            <div class="message">${message}</div>
        </c:if>
        
        <div class="actions">
            <a href="student?action=add" class="btn btn-primary">+ Add New Student</a>
            <a href="index.jsp" class="btn btn-info">🏠 Home</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Interests</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.studentId}</td>
                        <td>${student.name}</td>
                        <td>${student.age}</td>
                        <td>${student.email}</td>
                        <td>
                            <c:forEach var="interest" items="${student.interests}">
                                <span class="interests-tag">${interest.title}</span>
                            </c:forEach>
                            <c:if test="${empty student.interests}">
                                <span style="color: #999;">No interests</span>
                            </c:if>
                        </td>
                        <td>
                            <a href="student?action=manageInterests&id=${student.studentId}" class="btn btn-success" style="font-size: 12px; padding: 5px 10px;">Manage Interests</a>
                            <a href="student?action=edit&id=${student.studentId}" class="btn btn-info" style="font-size: 12px; padding: 5px 10px;">Edit</a>
                            <a href="student?action=delete&id=${student.studentId}" class="btn btn-danger" style="font-size: 12px; padding: 5px 10px;" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #999;">No students found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>

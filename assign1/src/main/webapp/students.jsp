<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Students List</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }
        
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        
        .action-bar {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            flex-wrap: wrap;
            gap: 10px;
        }
        
        .btn {
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 1em;
        }
        
        .btn:hover {
            opacity: 0.9;
        }
        
        .btn-danger {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
        }
        
        .btn-edit {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        tr:hover {
            background-color: #f5f5f5;
        }
        
        .interests-list {
            font-size: 0.9em;
            color: #666;
        }
        
        .interest-tag {
            display: inline-block;
            background: #e0e7ff;
            color: #4338ca;
            padding: 3px 8px;
            border-radius: 3px;
            margin: 2px;
            font-size: 0.85em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Students Management</h1>
        
        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/student?action=new" class="btn">+ Add New Student</a>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn">🏠 Home</a>
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
                        <td class="interests-list">
                            <c:forEach var="interest" items="${student.interests}">
                                <span class="interest-tag">${interest.title}</span>
                            </c:forEach>
                            <c:if test="${empty student.interests}">
                                <span style="color: #999;">No interests</span>
                            </c:if>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/student?action=edit&id=${student.studentId}" class="btn btn-edit">Edit</a>
                            <a href="${pageContext.request.contextPath}/student?action=delete&id=${student.studentId}" 
                               class="btn btn-danger" 
                               onclick="return confirm('Are you sure you want to delete this student?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #999;">No students found. Click "Add New Student" to get started.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>

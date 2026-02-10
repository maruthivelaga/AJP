<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Interests List</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 20px; }
        .actions { margin-bottom: 20px; display: flex; justify-content: space-between; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-info { background: #3498db; color: white; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #667eea; color: white; }
        tr:hover { background: #f5f5f5; }
        .message { padding: 10px; background: #d4edda; color: #155724; border-radius: 5px; margin-bottom: 20px; }
        .category-badge { display: inline-block; background: #667eea; color: white; padding: 4px 10px; border-radius: 3px; font-size: 12px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>⭐ Interests</h1>
        
        <c:if test="${message != null}">
            <div class="message">${message}</div>
        </c:if>
        
        <div class="actions">
            <a href="interest?action=add" class="btn btn-primary">+ Add New Interest</a>
            <a href="index.jsp" class="btn btn-info">🏠 Home</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Description</th>
                    <th>Category</th>
                    <th>Enrolled Students</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="interest" items="${interests}">
                    <tr>
                        <td>${interest.interestId}</td>
                        <td>${interest.title}</td>
                        <td>${interest.description}</td>
                        <td><span class="category-badge">${interest.category}</span></td>
                        <td>${interest.studentCount}</td>
                        <td>
                            <a href="interest?action=edit&id=${interest.interestId}" class="btn btn-info" style="font-size: 12px; padding: 5px 10px;">Edit</a>
                            <a href="interest?action=delete&id=${interest.interestId}" class="btn btn-danger" style="font-size: 12px; padding: 5px 10px;" onclick="return confirm('Are you sure?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty interests}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #999;">No interests found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${student != null ? 'Edit Student' : 'Add Student'}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #333; font-weight: bold; }
        input[type="text"], input[type="email"], input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-secondary { background: #95a5a6; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>${student != null ? '✏️ Edit Student' : '➕ Add New Student'}</h1>
        
        <form method="post" action="student">
            <input type="hidden" name="action" value="${student != null ? 'update' : 'add'}">
            <c:if test="${student != null}">
                <input type="hidden" name="studentId" value="${student.studentId}">
            </c:if>
            
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${student != null ? student.name : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="age">Age:</label>
                <input type="number" id="age" name="age" value="${student != null ? student.age : ''}" min="1" max="100" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${student != null ? student.email : ''}" required>
            </div>
            
            <button type="submit" class="btn btn-primary">${student != null ? 'Update' : 'Add'} Student</button>
            <a href="student?action=list" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${student != null ? 'Edit' : 'Add'} Student</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 600px;
            width: 100%;
        }
        
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: 500;
        }
        
        input[type="text"],
        input[type="email"],
        input[type="number"] {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 5px;
            font-size: 1em;
            transition: border-color 0.3s;
        }
        
        input:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .interests-group {
            border: 2px solid #ddd;
            padding: 15px;
            border-radius: 5px;
            max-height: 200px;
            overflow-y: auto;
        }
        
        .checkbox-item {
            display: flex;
            align-items: center;
            padding: 8px;
            margin: 5px 0;
            border-radius: 3px;
            transition: background 0.2s;
        }
        
        .checkbox-item:hover {
            background: #f5f5f5;
        }
        
        .checkbox-item input[type="checkbox"] {
            margin-right: 10px;
            width: 18px;
            height: 18px;
        }
        
        .checkbox-item label {
            margin: 0;
            flex: 1;
            cursor: pointer;
        }
        
        .interest-category {
            font-size: 0.85em;
            color: #999;
        }
        
        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        
        .btn {
            flex: 1;
            padding: 12px 20px;
            border: none;
            border-radius: 5px;
            font-size: 1em;
            cursor: pointer;
            text-decoration: none;
            text-align: center;
            transition: opacity 0.3s;
        }
        
        .btn:hover {
            opacity: 0.9;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
        }
        
        .no-interests {
            text-align: center;
            color: #999;
            padding: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>${student != null ? 'Edit Student' : 'Add New Student'}</h1>
        
        <form action="${pageContext.request.contextPath}/student" method="post">
            <input type="hidden" name="action" value="save">
            <c:if test="${student != null}">
                <input type="hidden" name="id" value="${student.studentId}">
            </c:if>
            
            <div class="form-group">
                <label for="name">Name *</label>
                <input type="text" id="name" name="name" value="${student.name}" required>
            </div>
            
            <div class="form-group">
                <label for="age">Age *</label>
                <input type="number" id="age" name="age" value="${student.age}" min="1" max="150" required>
            </div>
            
            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" value="${student.email}" required>
            </div>
            
            <div class="form-group">
                <label>Select Interests (Part B: Multiple Selection Allowed)</label>
                <div class="interests-group">
                    <c:if test="${empty interests}">
                        <div class="no-interests">
                            No interests available. Please add interests first.
                        </div>
                    </c:if>
                    <c:forEach var="interest" items="${interests}">
                        <div class="checkbox-item">
                            <input type="checkbox" 
                                   id="interest_${interest.interestId}" 
                                   name="interestIds" 
                                   value="${interest.interestId}"
                                   <c:if test="${student != null}">
                                       <c:forEach var="studentInterest" items="${student.interests}">
                                           <c:if test="${studentInterest.interestId == interest.interestId}">
                                               checked
                                           </c:if>
                                       </c:forEach>
                                   </c:if>>
                            <label for="interest_${interest.interestId}">
                                <strong>${interest.title}</strong>
                                <span class="interest-category"> - ${interest.category}</span>
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
            <div class="btn-group">
                <button type="submit" class="btn btn-primary">
                    ${student != null ? 'Update' : 'Save'} Student
                </button>
                <a href="${pageContext.request.contextPath}/student?action=list" class="btn btn-secondary">
                    Cancel
                </a>
            </div>
        </form>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${doctor != null ? 'Edit Doctor' : 'Add Doctor'}</title>
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
        input[type="text"],
        input[type="email"] {
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
    <h1>${doctor != null ? 'Edit Doctor' : 'Add New Doctor'}</h1>
    
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/doctors" method="post">
            <c:if test="${doctor != null}">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="id" value="${doctor.doctorId}"/>
            </c:if>
            
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${doctor != null ? doctor.name : ''}" required/>
            </div>
            
            <div class="form-group">
                <label for="specialization">Specialization:</label>
                <input type="text" id="specialization" name="specialization" value="${doctor != null ? doctor.specialization : ''}" required/>
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${doctor != null ? doctor.email : ''}" required/>
            </div>
            
            <button type="submit" class="btn btn-submit">Save</button>
            <a href="${pageContext.request.contextPath}/doctors" class="btn btn-cancel">Cancel</a>
        </form>
    </div>
</body>
</html>

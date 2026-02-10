<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Interest Report</title>
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
            max-width: 900px;
            margin: 0 auto;
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
        }
        
        h1 {
            color: #333;
            margin-bottom: 10px;
            text-align: center;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
            font-size: 1.1em;
        }
        
        .action-bar {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .btn {
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
        }
        
        .btn:hover {
            opacity: 0.9;
        }
        
        .report-card {
            background: #f8f9fa;
            border-left: 5px solid #667eea;
            padding: 20px;
            margin: 15px 0;
            border-radius: 5px;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .report-card:hover {
            transform: translateX(5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        .category-name {
            font-size: 1.4em;
            color: #333;
            font-weight: 600;
            margin-bottom: 10px;
        }
        
        .student-count {
            font-size: 2em;
            color: #667eea;
            font-weight: bold;
        }
        
        .count-label {
            color: #666;
            font-size: 0.9em;
        }
        
        .no-data {
            text-align: center;
            color: #999;
            padding: 40px;
            font-size: 1.2em;
        }
        
        .total-summary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 25px;
            border-radius: 10px;
            margin-bottom: 30px;
            text-align: center;
        }
        
        .total-summary h2 {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        
        .total-summary .total-number {
            font-size: 3em;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📊 Student Interest Report</h1>
        <p class="subtitle">Part B: Students Enrolled by Interest Category</p>
        
        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn">🏠 Back to Home</a>
        </div>
        
        <c:set var="totalStudents" value="0" />
        <c:forEach var="entry" items="${categoryReport}">
            <c:set var="totalStudents" value="${totalStudents + entry.value}" />
        </c:forEach>
        
        <div class="total-summary">
            <h2>Total Unique Students</h2>
            <div class="total-number">${totalStudents}</div>
        </div>
        
        <c:if test="${empty categoryReport}">
            <div class="no-data">
                No data available. Please add interests and students to view the report.
            </div>
        </c:if>
        
        <c:forEach var="entry" items="${categoryReport}">
            <div class="report-card">
                <div class="category-name">📚 ${entry.key}</div>
                <div>
                    <span class="student-count">${entry.value}</span>
                    <span class="count-label">student(s) enrolled</span>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
</html>

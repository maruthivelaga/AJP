<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Enrollment Report</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 30px; }
        .report-card { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; text-align: center; }
        .report-card h2 { margin-bottom: 10px; }
        .report-card .number { font-size: 48px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 15px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #667eea; color: white; }
        tr:hover { background: #f5f5f5; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; background: #3498db; color: white; margin-top: 20px; }
        .btn:hover { opacity: 0.9; }
        .empty-state { text-align: center; padding: 40px; color: #999; }
    </style>
</head>
<body>
    <div class="container">
        <h1>📊 Student Enrollment Report by Category</h1>
        
        <c:set var="totalStudents" value="0"/>
        <c:forEach var="entry" items="${categoryReport}">
            <c:set var="totalStudents" value="${totalStudents + entry.value}"/>
        </c:forEach>
        
        <div class="report-card">
            <h2>Total Enrolled Students</h2>
            <div class="number">${totalStudents}</div>
        </div>
        
        <c:if test="${not empty categoryReport}">
            <table>
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Number of Students</th>
                        <th>Percentage</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${categoryReport}">
                        <tr>
                            <td><strong>${entry.key}</strong></td>
                            <td>${entry.value} students</td>
                            <td>
                                <c:choose>
                                    <c:when test="${totalStudents > 0}">
                                        ${String.format("%.1f", (entry.value * 100.0 / totalStudents))}%
                                    </c:when>
                                    <c:otherwise>0%</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
        <c:if test="${empty categoryReport}">
            <div class="empty-state">
                <h3>No enrollment data available</h3>
                <p>Add students and interests to see the report.</p>
            </div>
        </c:if>
        
        <a href="index.jsp" class="btn">🏠 Back to Home</a>
    </div>
</body>
</html>

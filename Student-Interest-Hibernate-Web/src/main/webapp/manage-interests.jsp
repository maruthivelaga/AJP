<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Interests - ${student.name}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 900px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1, h2 { color: #667eea; }
        h2 { margin-top: 30px; margin-bottom: 15px; font-size: 20px; }
        .student-info { background: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .interests-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 15px; margin-bottom: 20px; }
        .interest-card { background: white; border: 2px solid #ddd; padding: 15px; border-radius: 5px; }
        .interest-card.enrolled { border-color: #27ae60; background: #d4edda; }
        .interest-card h3 { color: #333; margin-bottom: 8px; font-size: 16px; }
        .interest-card p { color: #666; font-size: 14px; margin-bottom: 10px; }
        .category { display: inline-block; background: #667eea; color: white; padding: 3px 8px; border-radius: 3px; font-size: 12px; margin-bottom: 10px; }
        .btn { padding: 8px 15px; border: none; border-radius: 5px; cursor: pointer; font-size: 14px; text-decoration: none; display: inline-block; }
        .btn-success { background: #27ae60; color: white; }
        .btn-danger { background: #e74c3c; color: white; }
        .btn-secondary { background: #95a5a6; color: white; }
        .btn:hover { opacity: 0.9; }
        form { display: inline; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🎯 Manage Interests for ${student.name}</h1>
        
        <div class="student-info">
            <strong>Student ID:</strong> ${student.studentId} | 
            <strong>Age:</strong> ${student.age} | 
            <strong>Email:</strong> ${student.email}
        </div>
        
        <h2>Currently Enrolled Interests (${student.interests.size()})</h2>
        <c:if test="${not empty student.interests}">
            <div class="interests-grid">
                <c:forEach var="interest" items="${student.interests}">
                    <div class="interest-card enrolled">
                        <span class="category">${interest.category}</span>
                        <h3>${interest.title}</h3>
                        <p>${interest.description}</p>
                        <form method="post" action="student">
                            <input type="hidden" name="action" value="unlinkInterest">
                            <input type="hidden" name="studentId" value="${student.studentId}">
                            <input type="hidden" name="interestId" value="${interest.interestId}">
                            <button type="submit" class="btn btn-danger">Remove</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty student.interests}">
            <p style="color: #999; margin-bottom: 20px;">No interests enrolled yet.</p>
        </c:if>
        
        <h2>Available Interests</h2>
        <div class="interests-grid">
            <c:forEach var="interest" items="${allInterests}">
                <c:set var="isEnrolled" value="false"/>
                <c:forEach var="studentInterest" items="${student.interests}">
                    <c:if test="${studentInterest.interestId == interest.interestId}">
                        <c:set var="isEnrolled" value="true"/>
                    </c:if>
                </c:forEach>
                
                <c:if test="${!isEnrolled}">
                    <div class="interest-card">
                        <span class="category">${interest.category}</span>
                        <h3>${interest.title}</h3>
                        <p>${interest.description}</p>
                        <form method="post" action="student">
                            <input type="hidden" name="action" value="linkInterest">
                            <input type="hidden" name="studentId" value="${student.studentId}">
                            <input type="hidden" name="interestId" value="${interest.interestId}">
                            <button type="submit" class="btn btn-success">Enroll</button>
                        </form>
                    </div>
                </c:if>
            </c:forEach>
        </div>
        
        <div style="margin-top: 30px;">
            <a href="student?action=list" class="btn btn-secondary">← Back to Students</a>
        </div>
    </div>
</body>
</html>

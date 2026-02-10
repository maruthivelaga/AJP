<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Issue Book</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #1e3c72; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #333; font-weight: bold; }
        select, input[type="date"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px; text-decoration: none; display: inline-block; }
        .btn-primary { background: #1e3c72; color: white; }
        .btn-secondary { background: #95a5a6; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>📤 Issue Book</h1>
        
        <form method="post" action="borrow">
            <input type="hidden" name="action" value="issue">
            
            <div class="form-group">
                <label for="memberId">Select Member:</label>
                <select id="memberId" name="memberId" required>
                    <option value="">-- Select a Member --</option>
                    <c:forEach var="member" items="${members}">
                        <option value="${member.memberId}">${member.name} (${member.email})</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="bookId">Select Book:</label>
                <select id="bookId" name="bookId" required>
                    <option value="">-- Select a Book --</option>
                    <c:forEach var="book" items="${books}">
                        <option value="${book.bookId}">${book.title} by ${book.author}</option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="issueDate">Issue Date:</label>
                <input type="date" id="issueDate" name="issueDate" required>
            </div>
            
            <div class="form-group">
                <label for="dueDate">Due Date:</label>
                <input type="date" id="dueDate" name="dueDate" required>
            </div>
            
            <button type="submit" class="btn btn-primary">Issue Book</button>
            <a href="index.jsp" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
    
    <script>
        // Set default issue date to today
        document.getElementById('issueDate').valueAsDate = new Date();
        // Set default due date to 14 days from today
        var dueDate = new Date();
        dueDate.setDate(dueDate.getDate() + 14);
        document.getElementById('dueDate').valueAsDate = dueDate;
    </script>
</body>
</html>

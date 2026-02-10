<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Overdue Borrows</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #e74c3c; margin-bottom: 20px; }
        .stats { background: #ffebee; padding: 15px; border-radius: 5px; margin-bottom: 20px; color: #c0392b; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; margin-bottom: 20px; }
        .btn-success { background: #27ae60; color: white; }
        .btn-info { background: #3498db; color: white; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #e74c3c; color: white; }
        tr { background: #ffebee; }
        tr:hover { background: #ffcdd2; }
        .days-overdue { color: #c0392b; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h1>⚠️ Overdue Borrows</h1>
        
        <div class="stats">
            <strong>Total Overdue Items:</strong> ${overdueBorrows.size()}
        </div>
        
        <a href="index.jsp" class="btn btn-info">🏠 Back to Home</a>
        
        <table>
            <thead>
                <tr>
                    <th>Borrow ID</th>
                    <th>Member</th>
                    <th>Contact</th>
                    <th>Book</th>
                    <th>Due Date</th>
                    <th>Days Overdue</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="borrow" items="${overdueBorrows}">
                    <tr>
                        <td>${borrow.borrowId}</td>
                        <td>${borrow.member.name}</td>
                        <td>${borrow.member.email}<br>${borrow.member.phone}</td>
                        <td>${borrow.book.title}</td>
                        <td>${borrow.dueDate}</td>
                        <td class="days-overdue">${borrow.daysOverdue} days</td>
                        <td>
                            <a href="borrow?action=return&id=${borrow.borrowId}" class="btn btn-success" style="font-size: 12px; padding: 5px 10px;">Mark Returned</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty overdueBorrows}">
                    <tr>
                        <td colspan="7" style="text-align: center; background: white; color: #27ae60;">
                            ✓ No overdue borrows! All books are on time or returned.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>

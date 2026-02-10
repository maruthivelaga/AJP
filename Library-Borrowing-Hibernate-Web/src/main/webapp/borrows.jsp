<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Borrowing Transactions</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 1300px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #1e3c72; margin-bottom: 20px; }
        .actions { margin-bottom: 20px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; margin-right: 10px; }
        .btn-primary { background: #1e3c72; color: white; }
        .btn-success { background: #27ae60; color: white; }
        .btn-info { background: #3498db; color: white; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #1e3c72; color: white; }
        tr:hover { background: #f5f5f5; }
        .status-issued { color: #e67e22; font-weight: bold; }
        .status-returned { color: #27ae60; font-weight: bold; }
        .overdue { background: #ffebee !important; }
        .message { padding: 10px; background: #d4edda; color: #155724; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>📋 Borrowing Transactions</h1>
        
        <c:if test="${message != null}">
            <div class="message">${message}</div>
        </c:if>
        
        <div class="actions">
            <a href="borrow?action=issue" class="btn btn-primary">📤 Issue New Book</a>
            <a href="borrow?action=overdue" class="btn btn-info">⚠️ View Overdue</a>
            <a href="index.jsp" class="btn btn-info">🏠 Home</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>Borrow ID</th>
                    <th>Member</th>
                    <th>Book</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="borrow" items="${borrows}">
                    <tr class="${borrow.overdue ? 'overdue' : ''}">
                        <td>${borrow.borrowId}</td>
                        <td>${borrow.member.name}</td>
                        <td>${borrow.book.title}</td>
                        <td>${borrow.issueDate}</td>
                        <td>${borrow.dueDate}</td>
                        <td class="${borrow.status == 'ISSUED' ? 'status-issued' : 'status-returned'}">
                            ${borrow.status}
                            <c:if test="${borrow.overdue}">
                                <br><small>(${borrow.daysOverdue} days overdue)</small>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${borrow.status == 'ISSUED'}">
                                <a href="borrow?action=return&id=${borrow.borrowId}" class="btn btn-success" style="font-size: 12px; padding: 5px 10px;">Return</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty borrows}">
                    <tr>
                        <td colspan="7" style="text-align: center; color: #999;">No borrowing records found</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Member Borrowing History</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 1100px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1, h2 { color: #1e3c72; }
        h2 { margin-top: 30px; margin-bottom: 15px; font-size: 20px; }
        .member-info { background: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; margin-bottom: 20px; }
        .btn-info { background: #3498db; color: white; }
        .btn-success { background: #27ae60; color: white; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 30px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #1e3c72; color: white; }
        tr:hover { background: #f5f5f5; }
        .status-issued { color: #e67e22; font-weight: bold; }
        .status-returned { color: #27ae60; font-weight: bold; }
        .overdue { background: #ffebee !important; }
    </style>
</head>
<body>
    <div class="container">
        <h1>📚 Borrowing History - ${member.name}</h1>
        
        <div class="member-info">
            <strong>Member ID:</strong> ${member.memberId} | 
            <strong>Email:</strong> ${member.email} | 
            <strong>Phone:</strong> ${member.phone}
        </div>
        
        <a href="member?action=list" class="btn btn-info">← Back to Members</a>
        
        <h2>Currently Borrowed Books (${currentBorrows.size()})</h2>
        <table>
            <thead>
                <tr>
                    <th>Book Title</th>
                    <th>Author</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="borrow" items="${currentBorrows}">
                    <tr class="${borrow.overdue ? 'overdue' : ''}">
                        <td>${borrow.book.title}</td>
                        <td>${borrow.book.author}</td>
                        <td>${borrow.issueDate}</td>
                        <td>${borrow.dueDate}</td>
                        <td class="status-issued">
                            ${borrow.status}
                            <c:if test="${borrow.overdue}">
                                <br><small>(${borrow.daysOverdue} days overdue)</small>
                            </c:if>
                        </td>
                        <td>
                            <a href="borrow?action=return&id=${borrow.borrowId}" class="btn btn-success" style="font-size: 12px; padding: 5px 10px;">Return</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty currentBorrows}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: #999;">No books currently borrowed</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <h2>Complete Borrowing History</h2>
        <table>
            <thead>
                <tr>
                    <th>Book Title</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="borrow" items="${allBorrows}">
                    <tr>
                        <td>${borrow.book.title}</td>
                        <td>${borrow.issueDate}</td>
                        <td>${borrow.dueDate}</td>
                        <td class="${borrow.status == 'ISSUED' ? 'status-issued' : 'status-returned'}">
                            ${borrow.status}
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

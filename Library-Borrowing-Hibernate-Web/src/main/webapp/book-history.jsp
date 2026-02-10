<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Borrowing History</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #1e3c72; margin-bottom: 20px; }
        .book-info { background: #f8f9fa; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; text-decoration: none; display: inline-block; margin-bottom: 20px; }
        .btn-info { background: #3498db; color: white; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background: #1e3c72; color: white; }
        tr:hover { background: #f5f5f5; }
        .status-issued { color: #e67e22; font-weight: bold; }
        .status-returned { color: #27ae60; font-weight: bold; }
    </style>
</head>
<body>
    <div class="container">
        <h1>📖 Borrowing History - ${book.title}</h1>
        
        <div class="book-info">
            <strong>Author:</strong> ${book.author} | 
            <strong>Category:</strong> ${book.category} | 
            <strong>ISBN:</strong> ${book.isbn}
        </div>
        
        <a href="book?action=list" class="btn btn-info">← Back to Books</a>
        
        <table>
            <thead>
                <tr>
                    <th>Borrow ID</th>
                    <th>Member</th>
                    <th>Issue Date</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="borrow" items="${history}">
                    <tr>
                        <td>${borrow.borrowId}</td>
                        <td>${borrow.member.name} (${borrow.member.email})</td>
                        <td>${borrow.issueDate}</td>
                        <td>${borrow.dueDate}</td>
                        <td class="${borrow.status == 'ISSUED' ? 'status-issued' : 'status-returned'}">
                            ${borrow.status}
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty history}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: #999;">No borrowing history for this book</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>

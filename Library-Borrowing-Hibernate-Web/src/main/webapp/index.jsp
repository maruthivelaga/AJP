<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Borrowing System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); min-height: 100vh; padding: 20px; }
        .container { max-width: 1000px; margin: 0 auto; background: white; border-radius: 10px; padding: 40px; box-shadow: 0 10px 30px rgba(0,0,0,0.3); }
        h1 { color: #1e3c72; text-align: center; margin-bottom: 10px; }
        .subtitle { text-align: center; color: #666; margin-bottom: 40px; }
        .menu { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; }
        .menu-item { background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%); color: white; padding: 30px; border-radius: 8px; text-align: center; text-decoration: none; transition: all 0.3s; }
        .menu-item:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(30, 60, 114, 0.4); }
        .menu-item h3 { margin-bottom: 10px; font-size: 20px; }
        .menu-item p { font-size: 14px; opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>📚 Library Borrowing Management System</h1>
        <p class="subtitle">Manage library members, books, and borrowing transactions</p>
        
        <div class="menu">
            <a href="member?action=list" class="menu-item">
                <h3>👥 Members</h3>
                <p>Manage library members</p>
            </a>
            
            <a href="book?action=list" class="menu-item">
                <h3>📖 Books</h3>
                <p>Manage book catalog</p>
            </a>
            
            <a href="borrow?action=issue" class="menu-item">
                <h3>📤 Issue Book</h3>
                <p>Issue book to a member</p>
            </a>
            
            <a href="borrow?action=list" class="menu-item">
                <h3>📋 All Transactions</h3>
                <p>View all borrowing records</p>
            </a>
            
            <a href="borrow?action=overdue" class="menu-item">
                <h3>⚠️ Overdue List</h3>
                <p>View overdue borrowings</p>
            </a>
        </div>
    </div>
</body>
</html>

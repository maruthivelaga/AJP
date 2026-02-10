<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Interest Management System</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .container {
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            max-width: 600px;
            width: 90%;
        }
        
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            font-size: 2.5em;
        }
        
        .menu {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        
        .menu-btn {
            display: block;
            padding: 15px 25px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 8px;
            text-align: center;
            font-size: 1.1em;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .menu-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }
        
        .description {
            text-align: center;
            color: #666;
            margin-bottom: 30px;
            font-size: 1.1em;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Student Interest Management</h1>
        <p class="description">Manage students and their interests</p>
        
        <div class="menu">
            <a href="${pageContext.request.contextPath}/student?action=list" class="menu-btn">
                📚 Manage Students
            </a>
            <a href="${pageContext.request.contextPath}/interest?action=list" class="menu-btn">
                🎯 Manage Interests
            </a>
            <a href="${pageContext.request.contextPath}/report" class="menu-btn">
                📊 View Reports
            </a>
        </div>
    </div>
</body>
</html>

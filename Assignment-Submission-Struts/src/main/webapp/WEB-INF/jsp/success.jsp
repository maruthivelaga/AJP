<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submission Successful</title>
    <style>
        body {
            font-family: Georgia, serif;
            background: linear-gradient(160deg, #e5f5e0 0%, #d9ebf8 100%);
            margin: 0;
            padding: 24px;
        }
        .card {
            max-width: 760px;
            margin: 60px auto;
            background: #fff;
            border-radius: 10px;
            border: 1px solid #d0dfd0;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.07);
            padding: 28px;
            text-align: center;
        }
        h1 {
            color: #1e6b3a;
            margin-top: 0;
        }
        .msg {
            font-size: 18px;
            color: #213;
            line-height: 1.5;
        }
        .link {
            margin-top: 20px;
            display: inline-block;
            background: #1f5f90;
            color: #fff;
            text-decoration: none;
            padding: 10px 16px;
            border-radius: 8px;
        }
        .link-alt {
            background: #2e6d45;
            margin-left: 8px;
        }
    </style>
</head>
<body>
<div class="card">
    <h1>Submission Recorded</h1>
    <p class="msg">
        Thank you, ${studentName}. Your assignment titled <strong>${assignmentTitle}</strong>
        has been successfully submitted.
    </p>
    <a class="link" href="assignmentForm.do">Submit Another Assignment</a>
    <a class="link link-alt" href="viewSubmissions.do">View All Submissions</a>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html>
<head>
    <title>Assignment Submission</title>
    <style>
        body {
            font-family: Georgia, serif;
            background: linear-gradient(135deg, #f7f2e9 0%, #d7e5f2 100%);
            margin: 0;
            padding: 24px;
        }
        .container {
            max-width: 840px;
            margin: 0 auto;
            background: #ffffff;
            border: 1px solid #d8d8d8;
            border-radius: 10px;
            box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
            padding: 28px;
        }
        h1 {
            margin-top: 0;
            color: #1f3a5f;
            font-size: 30px;
        }
        .sub {
            color: #444;
            margin-bottom: 20px;
        }
        .error {
            color: #b00020;
            background: #ffe9ee;
            border: 1px solid #f5b6c5;
            border-radius: 8px;
            padding: 10px 12px;
            margin-bottom: 14px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #223;
        }
        .field {
            margin-bottom: 14px;
        }
        input[type=text], textarea {
            width: 100%;
            box-sizing: border-box;
            border: 1px solid #b5bfd1;
            border-radius: 8px;
            padding: 10px;
            font-size: 15px;
            background: #fcfdff;
        }
        textarea {
            min-height: 240px;
            resize: vertical;
            line-height: 1.45;
        }
        .hint {
            font-size: 13px;
            color: #555;
            margin-top: 5px;
        }
        .btn {
            background: #204e7a;
            color: #fff;
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-size: 15px;
            cursor: pointer;
        }
        .btn:hover {
            background: #193f63;
        }
        .secondary-link {
            margin-left: 10px;
            color: #1f5f90;
            text-decoration: none;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>University Assignment Submission</h1>
    <p class="sub">Enter all details and submit your assignment as plain text content.</p>

    <html:errors/>

    <html:form action="/submitAssignment" method="post">
        <div class="field">
            <label for="studentName">Student Name</label>
            <html:text property="studentName" styleId="studentName"/>
        </div>

        <div class="field">
            <label for="studentId">Student ID</label>
            <html:text property="studentId" styleId="studentId"/>
        </div>

        <div class="field">
            <label for="courseName">Course Name</label>
            <html:text property="courseName" styleId="courseName"/>
        </div>

        <div class="field">
            <label for="assignmentTitle">Assignment Title</label>
            <html:text property="assignmentTitle" styleId="assignmentTitle"/>
        </div>

        <div class="field">
            <label for="content">Assignment Content</label>
            <html:textarea property="content" styleId="content"/>
        </div>

        <button class="btn" type="submit">Submit Assignment</button>
        <a class="secondary-link" href="viewSubmissions.do">View Submissions</a>
    </html:form>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submitted Assignments</title>
    <style>
        body {
            font-family: Georgia, serif;
            background: linear-gradient(145deg, #f0f6ff 0%, #eef5ed 100%);
            margin: 0;
            padding: 24px;
        }
        .container {
            max-width: 1080px;
            margin: 0 auto;
            background: #fff;
            border: 1px solid #d4dce8;
            border-radius: 10px;
            box-shadow: 0 10px 24px rgba(0, 0, 0, 0.08);
            padding: 24px;
        }
        h1 {
            margin: 0 0 14px;
            color: #1f3a5f;
        }
        .topbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 18px;
            gap: 10px;
            flex-wrap: wrap;
        }
        .btn {
            display: inline-block;
            background: #1f5f90;
            color: #fff;
            text-decoration: none;
            padding: 10px 15px;
            border-radius: 8px;
            font-size: 14px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }
        th, td {
            border: 1px solid #d9dfeb;
            padding: 10px;
            vertical-align: top;
            text-align: left;
        }
        th {
            background: #ecf2fb;
            color: #1c3451;
        }
        .empty {
            border: 1px dashed #c8cfdd;
            border-radius: 8px;
            padding: 14px;
            color: #43526a;
            background: #f7f9fc;
        }
        .content {
            max-width: 460px;
            white-space: pre-wrap;
            line-height: 1.35;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="topbar">
        <h1>Submitted Assignments</h1>
        <a class="btn" href="assignmentForm.do">New Submission</a>
    </div>

    <logic:empty name="submissions">
        <div class="empty">No submissions available yet.</div>
    </logic:empty>

    <logic:notEmpty name="submissions">
        <table>
            <thead>
            <tr>
                <th>Student Name</th>
                <th>Student ID</th>
                <th>Course Name</th>
                <th>Assignment Title</th>
                <th>Content</th>
            </tr>
            </thead>
            <tbody>
            <logic:iterate id="submission" name="submissions" type="com.university.assignment.model.Assignment">
                <tr>
                    <td><bean:write name="submission" property="studentName"/></td>
                    <td><bean:write name="submission" property="studentId"/></td>
                    <td><bean:write name="submission" property="courseName"/></td>
                    <td><bean:write name="submission" property="assignmentTitle"/></td>
                    <td class="content"><bean:write name="submission" property="content" filter="false"/></td>
                </tr>
            </logic:iterate>
            </tbody>
        </table>
    </logic:notEmpty>
</div>
</body>
</html>

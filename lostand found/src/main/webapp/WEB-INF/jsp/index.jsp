<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Lost &amp; Found</title>
    <link rel="stylesheet" href="assets/styles.css"/>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Campus Lost &amp; Found Management</h1>
        <p>Report items, find matches, and coordinate recovery.</p>
        <div class="nav">
            <a href="reportForm">Report Item</a>
            <a href="searchItems">Search</a>
            <a href="notifications">Matching &amp; Notifications</a>
            <a href="adminDashboard">Admin Dashboard</a>
        </div>
    </div>

    <div class="card">
        <h2>All Item Reports</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Description</th>
                <th>Location</th>
                <th>Student</th>
                <th>Status</th>
                <th>Verified</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="reports">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="type"/></td>
                    <td><s:property value="description"/></td>
                    <td><s:property value="location"/></td>
                    <td><s:property value="studentName"/></td>
                    <td><span class="badge"><s:property value="status"/></span></td>
                    <td><s:property value="verified"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </div>

    <div class="card">
        <h2>Notification Count</h2>
        <p><strong><s:property value="notifications.size()"/></strong> match notifications generated.</p>
    </div>
</div>
</body>
</html>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Matching & Notifications</title>
    <link rel="stylesheet" href="assets/styles.css"/>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Matching and Notification Center</h1>
        <div class="nav">
            <a href="home">Home</a>
            <a href="reportForm">Report Item</a>
            <a href="searchItems">Search</a>
            <a href="adminDashboard">Admin</a>
        </div>
    </div>

    <div class="card">
        <s:actionmessage cssClass="alert-success"/>
        <form action="runMatching" method="post">
            <button type="submit">Run Matching Engine</button>
        </form>
    </div>

    <div class="card">
        <h2>Generated Notifications</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Lost Item ID</th>
                <th>Found Item ID</th>
                <th>Student Email</th>
                <th>Message</th>
                <th>Meeting Suggestion</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="notifications">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="lostItemId"/></td>
                    <td><s:property value="foundItemId"/></td>
                    <td><s:property value="lostStudentEmail"/></td>
                    <td><s:property value="message"/></td>
                    <td><s:property value="meetingSuggestion"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

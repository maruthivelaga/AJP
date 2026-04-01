<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report Item</title>
    <link rel="stylesheet" href="assets/styles.css"/>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Report Lost or Found Item</h1>
        <div class="nav">
            <a href="home">Home</a>
            <a href="searchItems">Search</a>
            <a href="notifications">Notifications</a>
            <a href="adminDashboard">Admin</a>
        </div>
    </div>

    <div class="card">
        <s:actionerror cssClass="alert-error"/>
        <s:actionmessage cssClass="alert-success"/>

        <s:form action="submitReport" method="post" theme="simple">
            <label>Type</label>
            <s:select name="type" list="# {'LOST':'LOST','FOUND':'FOUND'}"/>

            <label>Student Name</label>
            <s:textfield name="studentName"/>

            <label>Email</label>
            <s:textfield name="email"/>

            <label>Item Description</label>
            <s:textarea name="description" rows="4"/>

            <label>Location</label>
            <s:textfield name="location"/>

            <button type="submit">Submit Report</button>
        </s:form>
    </div>
</div>
</body>
</html>

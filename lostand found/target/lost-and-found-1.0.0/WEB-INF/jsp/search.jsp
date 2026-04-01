<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Items</title>
    <link rel="stylesheet" href="assets/styles.css"/>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Search by Description</h1>
        <div class="nav">
            <a href="home">Home</a>
            <a href="reportForm">Report Item</a>
            <a href="notifications">Notifications</a>
            <a href="adminDashboard">Admin</a>
        </div>
    </div>

    <div class="card">
        <s:form action="searchItems" method="get" theme="simple">
            <label>Search Text</label>
            <s:textfield name="query"/>
            <button type="submit">Search</button>
        </s:form>
    </div>

    <div class="card">
        <h2>Search Results</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Type</th>
                <th>Description</th>
                <th>Location</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="results">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="type"/></td>
                    <td><s:property value="description"/></td>
                    <td><s:property value="location"/></td>
                    <td><s:property value="status"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

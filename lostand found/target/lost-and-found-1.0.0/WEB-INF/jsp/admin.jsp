<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="assets/styles.css"/>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Admin Features</h1>
        <div class="nav">
            <a href="home">Home</a>
            <a href="reportForm">Report Item</a>
            <a href="searchItems">Search</a>
            <a href="notifications">Notifications</a>
        </div>
    </div>

    <div class="card">
        <s:actionerror cssClass="alert-error"/>
        <s:actionmessage cssClass="alert-success"/>

        <h2>Verify Found Item</h2>
        <form action="verifyFoundItem" method="post">
            <label>Found Item ID</label>
            <input type="number" name="itemId" required/>
            <button type="submit">Verify</button>
        </form>
    </div>

    <div class="card">
        <h2>Open Dispute</h2>
        <form action="openDispute" method="post">
            <label>Item ID</label>
            <input type="number" name="itemId" required/>

            <label>Reason</label>
            <textarea name="reason" rows="3" required></textarea>

            <button type="submit">Open Dispute</button>
        </form>
    </div>

    <div class="card">
        <h2>Resolve Dispute</h2>
        <form action="resolveDispute" method="post">
            <label>Dispute ID</label>
            <input type="number" name="disputeId" required/>

            <label>Resolution</label>
            <textarea name="resolution" rows="3" required></textarea>

            <button type="submit">Resolve</button>
        </form>
    </div>

    <div class="card">
        <h2>Pending Found Item Verification</h2>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Description</th>
                <th>Location</th>
                <th>Student</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="pendingFoundItems">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="description"/></td>
                    <td><s:property value="location"/></td>
                    <td><s:property value="studentName"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </div>

    <div class="card">
        <h2>Dispute Cases</h2>
        <table>
            <thead>
            <tr>
                <th>Dispute ID</th>
                <th>Item ID</th>
                <th>Reason</th>
                <th>Status</th>
                <th>Resolution</th>
            </tr>
            </thead>
            <tbody>
            <s:iterator value="disputes">
                <tr>
                    <td><s:property value="id"/></td>
                    <td><s:property value="itemId"/></td>
                    <td><s:property value="reason"/></td>
                    <td><s:property value="status"/></td>
                    <td><s:property value="resolution"/></td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

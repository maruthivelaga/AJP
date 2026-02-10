<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${interest != null ? 'Edit Interest' : 'Add Interest'}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #333; font-weight: bold; }
        input[type="text"], textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; font-family: Arial, sans-serif; }
        textarea { resize: vertical; min-height: 100px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-secondary { background: #95a5a6; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>${interest != null ? '✏️ Edit Interest' : '➕ Add New Interest'}</h1>
        
        <form method="post" action="interest">
            <input type="hidden" name="action" value="${interest != null ? 'update' : 'add'}">
            <c:if test="${interest != null}">
                <input type="hidden" name="interestId" value="${interest.interestId}">
            </c:if>
            
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${interest != null ? interest.title : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required>${interest != null ? interest.description : ''}</textarea>
            </div>
            
            <div class="form-group">
                <label for="category">Category:</label>
                <input type="text" id="category" name="category" value="${interest != null ? interest.category : ''}" placeholder="e.g., Sports, Arts, Technology, Music" required>
            </div>
            
            <button type="submit" class="btn btn-primary">${interest != null ? 'Update' : 'Add'} Interest</button>
            <a href="interest?action=list" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>

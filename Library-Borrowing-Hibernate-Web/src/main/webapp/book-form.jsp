<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${book != null ? 'Edit Book' : 'Add Book'}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .container { max-width: 600px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #1e3c72; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; color: #333; font-weight: bold; }
        input[type="text"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; font-size: 14px; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; margin-right: 10px; text-decoration: none; display: inline-block; }
        .btn-primary { background: #1e3c72; color: white; }
        .btn-secondary { background: #95a5a6; color: white; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="container">
        <h1>${book != null ? '✏️ Edit Book' : '➕ Add New Book'}</h1>
        
        <form method="post" action="book">
            <input type="hidden" name="action" value="${book != null ? 'update' : 'add'}">
            <c:if test="${book != null}">
                <input type="hidden" name="bookId" value="${book.bookId}">
            </c:if>
            
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${book != null ? book.title : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="author">Author:</label>
                <input type="text" id="author" name="author" value="${book != null ? book.author : ''}" required>
            </div>
            
            <div class="form-group">
                <label for="category">Category:</label>
                <input type="text" id="category" name="category" value="${book != null ? book.category : ''}" placeholder="e.g., Fiction, Science, History" required>
            </div>
            
            <div class="form-group">
                <label for="isbn">ISBN:</label>
                <input type="text" id="isbn" name="isbn" value="${book != null ? book.isbn : ''}" required>
            </div>
            
            <button type="submit" class="btn btn-primary">${book != null ? 'Update' : 'Add'} Book</button>
            <a href="book?action=list" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>

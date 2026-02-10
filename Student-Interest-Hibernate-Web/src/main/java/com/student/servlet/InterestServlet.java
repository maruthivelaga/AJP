package com.student.servlet;

import com.student.dao.InterestDAO;
import com.student.entity.Interest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Interest operations
 */
@WebServlet("/interest")
public class InterestServlet extends HttpServlet {
    private InterestDAO interestDAO = new InterestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listInterests(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteInterest(request, response);
                break;
            default:
                listInterests(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addInterest(request, response);
                break;
            case "update":
                updateInterest(request, response);
                break;
            default:
                listInterests(request, response);
        }
    }

    private void listInterests(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Interest> interests = interestDAO.getAllInterests();
        request.setAttribute("interests", interests);
        request.getRequestDispatcher("/interests.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/interest-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long interestId = Long.parseLong(request.getParameter("id"));
        Interest interest = interestDAO.getInterestById(interestId);
        request.setAttribute("interest", interest);
        request.getRequestDispatcher("/interest-form.jsp").forward(request, response);
    }

    private void addInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        
        Interest interest = new Interest(title, description, category);
        boolean success = interestDAO.addInterest(interest);
        
        request.setAttribute("message", success ? "Interest added successfully!" : "Error adding interest.");
        listInterests(request, response);
    }

    private void updateInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long interestId = Long.parseLong(request.getParameter("interestId"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        
        Interest interest = interestDAO.getInterestById(interestId);
        interest.setTitle(title);
        interest.setDescription(description);
        interest.setCategory(category);
        
        boolean success = interestDAO.updateInterest(interest);
        request.setAttribute("message", success ? "Interest updated successfully!" : "Error updating interest.");
        listInterests(request, response);
    }

    private void deleteInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long interestId = Long.parseLong(request.getParameter("id"));
        boolean success = interestDAO.deleteInterest(interestId);
        
        request.setAttribute("message", success ? "Interest deleted successfully!" : "Error deleting interest.");
        listInterests(request, response);
    }
}

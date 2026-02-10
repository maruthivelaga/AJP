package com.studentinterest.servlet;

import com.studentinterest.dao.InterestDAO;
import com.studentinterest.entity.Interest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InterestServlet extends HttpServlet {
    
    private InterestDAO interestDAO = new InterestDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "list":
                listInterests(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteInterest(request, response);
                break;
            default:
                listInterests(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveInterest(request, response);
        }
    }
    
    private void listInterests(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Interest> interests = interestDAO.getAll();
        request.setAttribute("interests", interests);
        request.getRequestDispatcher("/interests.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/interest-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Interest interest = interestDAO.getById(id);
        request.setAttribute("interest", interest);
        request.getRequestDispatcher("/interest-form.jsp").forward(request, response);
    }
    
    private void saveInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        
        Interest interest;
        if (idParam != null && !idParam.isEmpty()) {
            // Edit existing interest
            Long id = Long.parseLong(idParam);
            interest = interestDAO.getById(id);
            interest.setTitle(title);
            interest.setDescription(description);
            interest.setCategory(category);
        } else {
            // Create new interest
            interest = new Interest(title, description, category);
        }
        
        interestDAO.saveOrUpdate(interest);
        response.sendRedirect(request.getContextPath() + "/interest?action=list");
    }
    
    private void deleteInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        interestDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/interest?action=list");
    }
}

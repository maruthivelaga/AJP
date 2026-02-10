package com.student.servlet;

import com.student.dao.InterestDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Servlet for generating reports
 */
@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private InterestDAO interestDAO = new InterestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Long> categoryReport = interestDAO.getStudentCountByCategory();
        request.setAttribute("categoryReport", categoryReport);
        request.getRequestDispatcher("/report.jsp").forward(request, response);
    }
}

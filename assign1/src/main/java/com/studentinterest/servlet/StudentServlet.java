package com.studentinterest.servlet;

import com.studentinterest.dao.InterestDAO;
import com.studentinterest.dao.StudentDAO;
import com.studentinterest.entity.Interest;
import com.studentinterest.entity.Student;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StudentServlet extends HttpServlet {
    
    private StudentDAO studentDAO = new StudentDAO();
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
                listStudents(request, response);
                break;
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            default:
                listStudents(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveStudent(request, response);
        }
    }
    
    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAll();
        request.setAttribute("students", students);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Interest> interests = interestDAO.getAll();
        request.setAttribute("interests", interests);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Student student = studentDAO.getById(id);
        List<Interest> allInterests = interestDAO.getAll();
        
        request.setAttribute("student", student);
        request.setAttribute("interests", allInterests);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }
    
    private void saveStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String ageParam = request.getParameter("age");
        String email = request.getParameter("email");
        String[] interestIds = request.getParameterValues("interestIds");
        
        Student student;
        if (idParam != null && !idParam.isEmpty()) {
            // Edit existing student
            Long id = Long.parseLong(idParam);
            student = studentDAO.getById(id);
            student.setName(name);
            student.setAge(Integer.parseInt(ageParam));
            student.setEmail(email);
            
            // Clear existing interests
            student.getInterests().clear();
        } else {
            // Create new student
            student = new Student(name, Integer.parseInt(ageParam), email);
        }
        
        // Add selected interests
        if (interestIds != null) {
            Set<Interest> interests = new HashSet<>();
            for (String interestId : interestIds) {
                Interest interest = interestDAO.getById(Long.parseLong(interestId));
                if (interest != null) {
                    interests.add(interest);
                }
            }
            student.setInterests(interests);
        }
        
        studentDAO.saveOrUpdate(student);
        response.sendRedirect(request.getContextPath() + "/student?action=list");
    }
    
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        studentDAO.delete(id);
        response.sendRedirect(request.getContextPath() + "/student?action=list");
    }
}

package com.student.servlet;

import com.student.dao.StudentDAO;
import com.student.dao.InterestDAO;
import com.student.entity.Student;
import com.student.entity.Interest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling Student operations
 */
@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO = new StudentDAO();
    private InterestDAO interestDAO = new InterestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) action = "list";
        
        switch (action) {
            case "list":
                listStudents(request, response);
                break;
            case "add":
                showAddForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "manageInterests":
                showManageInterests(request, response);
                break;
            default:
                listStudents(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        switch (action) {
            case "add":
                addStudent(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
            case "linkInterest":
                linkInterest(request, response);
                break;
            case "unlinkInterest":
                unlinkInterest(request, response);
                break;
            default:
                listStudents(request, response);
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Student> students = studentDAO.getAllStudents();
        request.setAttribute("students", students);
        request.getRequestDispatcher("/students.jsp").forward(request, response);
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentId = Long.parseLong(request.getParameter("id"));
        Student student = studentDAO.getStudentById(studentId);
        request.setAttribute("student", student);
        request.getRequestDispatcher("/student-form.jsp").forward(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String email = request.getParameter("email");
        
        Student student = new Student(name, age, email);
        boolean success = studentDAO.addStudent(student);
        
        request.setAttribute("message", success ? "Student added successfully!" : "Error adding student.");
        listStudents(request, response);
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentId = Long.parseLong(request.getParameter("studentId"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String email = request.getParameter("email");
        
        Student student = studentDAO.getStudentById(studentId);
        student.setName(name);
        student.setAge(age);
        student.setEmail(email);
        
        boolean success = studentDAO.updateStudent(student);
        request.setAttribute("message", success ? "Student updated successfully!" : "Error updating student.");
        listStudents(request, response);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentId = Long.parseLong(request.getParameter("id"));
        boolean success = studentDAO.deleteStudent(studentId);
        
        request.setAttribute("message", success ? "Student deleted successfully!" : "Error deleting student.");
        listStudents(request, response);
    }

    private void showManageInterests(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentId = Long.parseLong(request.getParameter("id"));
        Student student = studentDAO.getStudentById(studentId);
        List<Interest> allInterests = interestDAO.getAllInterests();
        
        request.setAttribute("student", student);
        request.setAttribute("allInterests", allInterests);
        request.getRequestDispatcher("/manage-interests.jsp").forward(request, response);
    }

    private void linkInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentId = Long.parseLong(request.getParameter("studentId"));
        Long interestId = Long.parseLong(request.getParameter("interestId"));
        
        studentDAO.linkStudentToInterest(studentId, interestId);
        response.sendRedirect("student?action=manageInterests&id=" + studentId);
    }

    private void unlinkInterest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long studentId = Long.parseLong(request.getParameter("studentId"));
        Long interestId = Long.parseLong(request.getParameter("interestId"));
        
        studentDAO.unlinkStudentFromInterest(studentId, interestId);
        response.sendRedirect("student?action=manageInterests&id=" + studentId);
    }
}

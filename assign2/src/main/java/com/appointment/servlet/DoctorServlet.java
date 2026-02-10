package com.appointment.servlet;

import com.appointment.dao.DoctorDAO;
import com.appointment.model.Doctor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/doctors")
public class DoctorServlet extends HttpServlet {
    
    private DoctorDAO doctorDAO;
    
    @Override
    public void init() {
        doctorDAO = new DoctorDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteDoctor(request, response);
                break;
            default:
                listDoctors(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "insert";
        }
        
        if ("update".equals(action)) {
            updateDoctor(request, response);
        } else {
            insertDoctor(request, response);
        }
    }
    
    private void listDoctors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        request.setAttribute("doctors", doctors);
        request.getRequestDispatcher("/WEB-INF/views/doctor-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/doctor-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Doctor doctor = doctorDAO.getDoctorById(id);
        request.setAttribute("doctor", doctor);
        request.getRequestDispatcher("/WEB-INF/views/doctor-form.jsp").forward(request, response);
    }
    
    private void insertDoctor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        String specialization = request.getParameter("specialization");
        String email = request.getParameter("email");
        
        Doctor doctor = new Doctor(name, specialization, email);
        doctorDAO.saveDoctor(doctor);
        
        response.sendRedirect(request.getContextPath() + "/doctors");
    }
    
    private void updateDoctor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String specialization = request.getParameter("specialization");
        String email = request.getParameter("email");
        
        Doctor doctor = doctorDAO.getDoctorById(id);
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setEmail(email);
        
        doctorDAO.updateDoctor(doctor);
        
        response.sendRedirect(request.getContextPath() + "/doctors");
    }
    
    private void deleteDoctor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        doctorDAO.deleteDoctor(id);
        response.sendRedirect(request.getContextPath() + "/doctors");
    }
}

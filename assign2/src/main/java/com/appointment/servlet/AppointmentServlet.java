package com.appointment.servlet;

import com.appointment.dao.DoctorDAO;
import com.appointment.dao.PatientDAO;
import com.appointment.model.Doctor;
import com.appointment.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/appointments")
public class AppointmentServlet extends HttpServlet {
    
    private PatientDAO patientDAO;
    private DoctorDAO doctorDAO;
    
    @Override
    public void init() {
        patientDAO = new PatientDAO();
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
            case "cancel":
                cancelAppointment(request, response);
                break;
            default:
                listAppointments(request, response);
                break;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        bookAppointment(request, response);
    }
    
    private void listAppointments(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Patient> patients = patientDAO.getAllPatients();
        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/WEB-INF/views/appointment-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Patient> patients = patientDAO.getAllPatients();
        List<Doctor> doctors = doctorDAO.getAllDoctors();
        
        request.setAttribute("patients", patients);
        request.setAttribute("doctors", doctors);
        request.getRequestDispatcher("/WEB-INF/views/appointment-form.jsp").forward(request, response);
    }
    
    private void bookAppointment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long patientId = Long.parseLong(request.getParameter("patientId"));
        Long doctorId = Long.parseLong(request.getParameter("doctorId"));
        
        patientDAO.bookAppointment(patientId, doctorId);
        
        response.sendRedirect(request.getContextPath() + "/appointments");
    }
    
    private void cancelAppointment(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long patientId = Long.parseLong(request.getParameter("patientId"));
        Long doctorId = Long.parseLong(request.getParameter("doctorId"));
        
        patientDAO.cancelAppointment(patientId, doctorId);
        
        response.sendRedirect(request.getContextPath() + "/appointments");
    }
}

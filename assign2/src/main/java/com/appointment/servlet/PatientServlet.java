package com.appointment.servlet;

import com.appointment.dao.PatientDAO;
import com.appointment.model.Patient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {
    
    private PatientDAO patientDAO;
    
    @Override
    public void init() {
        patientDAO = new PatientDAO();
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
                deletePatient(request, response);
                break;
            default:
                listPatients(request, response);
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
            updatePatient(request, response);
        } else {
            insertPatient(request, response);
        }
    }
    
    private void listPatients(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Patient> patients = patientDAO.getAllPatients();
        request.setAttribute("patients", patients);
        request.getRequestDispatcher("/WEB-INF/views/patient-list.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/patient-form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Patient patient = patientDAO.getPatientById(id);
        request.setAttribute("patient", patient);
        request.getRequestDispatcher("/WEB-INF/views/patient-form.jsp").forward(request, response);
    }
    
    private void insertPatient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        Patient patient = new Patient(name, age, phone, email);
        patientDAO.savePatient(patient);
        
        response.sendRedirect(request.getContextPath() + "/patients");
    }
    
    private void updatePatient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        
        Patient patient = patientDAO.getPatientById(id);
        patient.setName(name);
        patient.setAge(age);
        patient.setPhone(phone);
        patient.setEmail(email);
        
        patientDAO.updatePatient(patient);
        
        response.sendRedirect(request.getContextPath() + "/patients");
    }
    
    private void deletePatient(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        patientDAO.deletePatient(id);
        response.sendRedirect(request.getContextPath() + "/patients");
    }
}

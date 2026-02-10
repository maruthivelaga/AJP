package com.appointment.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "specialization", nullable = false, length = 100)
    private String specialization;
    
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    
    // Many-to-Many relationship with Patient
    @ManyToMany(mappedBy = "doctors")
    private Set<Patient> patients = new HashSet<>();
    
    // Constructors
    public Doctor() {
    }
    
    public Doctor(String name, String specialization, String email) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getDoctorId() {
        return doctorId;
    }
    
    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Set<Patient> getPatients() {
        return patients;
    }
    
    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "doctorId=" + doctorId +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

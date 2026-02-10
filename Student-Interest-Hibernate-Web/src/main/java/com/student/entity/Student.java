package com.student.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Student Entity with many-to-many relationship with Interest
 */
@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "age", nullable = false)
    private int age;
    
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    
    // Many-to-Many relationship with Interest
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "student_interests",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    private Set<Interest> interests = new HashSet<>();

    // Constructors
    public Student() {
    }

    public Student(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Interest> getInterests() {
        return interests;
    }

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    // Helper methods
    public void addInterest(Interest interest) {
        this.interests.add(interest);
        interest.getStudents().add(this);
    }

    public void removeInterest(Interest interest) {
        this.interests.remove(interest);
        interest.getStudents().remove(this);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}

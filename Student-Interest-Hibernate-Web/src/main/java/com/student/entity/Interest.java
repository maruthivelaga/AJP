package com.student.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Interest Entity with many-to-many relationship with Student
 */
@Entity
@Table(name = "interests")
public class Interest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    private Long interestId;
    
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "category", nullable = false, length = 50)
    private String category;
    
    // Many-to-Many relationship with Student
    @ManyToMany(mappedBy = "interests", fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    // Constructors
    public Interest() {
    }

    public Interest(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
    }

    // Getters and Setters
    public Long getInterestId() {
        return interestId;
    }

    public void setInterestId(Long interestId) {
        this.interestId = interestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    /**
     * Get count of students enrolled in this interest
     */
    public int getStudentCount() {
        return students.size();
    }

    @Override
    public String toString() {
        return "Interest{" +
                "interestId=" + interestId +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}

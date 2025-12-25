package com.department.research.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 100)
    private String department;

    @Column(length = 50)
    private String designation;

    @Enumerated(EnumType.STRING)
    private AuthorType type;

    @Column(length = 100)
    private String affiliation;

    @Column(nullable = false)
    private Boolean isInternal = true;

    @Column(length = 100)
    private String orcid;

    @Column(length = 100)
    private String scopusId;

    @Column(length = 100)
    private String googleScholarId;

    @ManyToMany(mappedBy = "authors")
    private Set<Publication> publications = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum AuthorType {
        FACULTY,
        STUDENT,
        EXTERNAL
    }
}

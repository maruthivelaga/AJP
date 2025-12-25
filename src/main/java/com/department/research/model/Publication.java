package com.department.research.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "publications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationType type;

    @Column(nullable = false)
    private Integer year;

    @Column(length = 300)
    private String journal;

    @Column(length = 300)
    private String conference;

    @Column(length = 100)
    private String doi;

    @Column(length = 100)
    private String isbn;

    @Column(length = 100)
    private String issn;

    @Enumerated(EnumType.STRING)
    private IndexType indexType;

    @Column(length = 50)
    private String volume;

    @Column(length = 50)
    private String issue;

    @Column(length = 50)
    private String pages;

    @Column(length = 200)
    private String publisher;

    @Column(length = 200)
    private String bookTitle;

    @Column(length = 100)
    private String patentNumber;

    @Column
    private LocalDate publicationDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 500)
    private String keywords;

    @Column(length = 500)
    private String url;

    @Column
    private Double impactFactor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "added_by_id")
    private User addedBy;

    // Approval Workflow Fields (STEP 1.1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submitted_by_id")
    private User submittedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rejected_by_id")
    private User rejectedBy;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column
    private LocalDateTime submittedAt;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private LocalDateTime rejectedAt;

    @ManyToMany
    @JoinTable(
        name = "publication_authors",
        joinColumns = @JoinColumn(name = "publication_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum PublicationType {
        JOURNAL,
        CONFERENCE,
        PATENT,
        BOOK_CHAPTER,
        FUNDED_PROJECT
    }

    public enum IndexType {
        SCOPUS,
        WEB_OF_SCIENCE,
        UGC_CARE,
        SCI,
        SCIE,
        ESCI,
        NOT_INDEXED
    }

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    public void addAuthor(Author author) {
        authors.add(author);
        author.getPublications().add(this);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getPublications().remove(this);
    }
}

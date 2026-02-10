package com.department.research.repository;

import com.department.research.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    Optional<Author> findByEmail(String email);
    
    List<Author> findByNameContainingIgnoreCase(String name);
    
    List<Author> findByDepartment(String department);
    
    List<Author> findByType(Author.AuthorType type);
    
    List<Author> findByIsInternalTrue();
    
    List<Author> findByIsInternalFalse();
    
    boolean existsByEmail(String email);
    
    @Query("SELECT a FROM Author a JOIN a.publications p WHERE p.id = :publicationId")
    List<Author> findByPublicationId(@Param("publicationId") Long publicationId);
    
    @Query("SELECT a, COUNT(p) as pubCount FROM Author a " +
           "JOIN a.publications p " +
           "WHERE a.isInternal = true " +
           "GROUP BY a " +
           "ORDER BY pubCount DESC")
    List<Object[]> findTopAuthors();
}

package com.department.research.repository;

import com.department.research.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    
    List<Publication> findByYear(Integer year);
    
    List<Publication> findByType(Publication.PublicationType type);
    
    List<Publication> findByStatus(Publication.Status status);
    
    List<Publication> findByIndexType(Publication.IndexType indexType);
    
    List<Publication> findByYearBetween(Integer startYear, Integer endYear);
    
    List<Publication> findByTitleContainingIgnoreCase(String title);
    
    @Query("SELECT p FROM Publication p JOIN p.authors a WHERE a.id = :authorId")
    List<Publication> findByAuthorId(@Param("authorId") Long authorId);
    
    @Query("SELECT p FROM Publication p JOIN p.authors a WHERE a.name LIKE %:authorName%")
    List<Publication> findByAuthorNameContaining(@Param("authorName") String authorName);
    
    @Query("SELECT p FROM Publication p WHERE p.year = :year AND p.status = :status")
    List<Publication> findByYearAndStatus(@Param("year") Integer year, @Param("status") Publication.Status status);
    
    @Query("SELECT COUNT(p) FROM Publication p WHERE p.year = :year")
    Long countByYear(@Param("year") Integer year);
    
    @Query("SELECT COUNT(p) FROM Publication p WHERE p.type = :type")
    Long countByType(@Param("type") Publication.PublicationType type);
    
    @Query("SELECT p.year, COUNT(p) FROM Publication p WHERE p.status = 'APPROVED' GROUP BY p.year ORDER BY p.year DESC")
    List<Object[]> countPublicationsByYear();
    
    @Query("SELECT p FROM Publication p WHERE p.addedBy.id = :userId ORDER BY p.year DESC")
    List<Publication> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Publication p WHERE " +
           "(:year IS NULL OR p.year = :year) AND " +
           "(:type IS NULL OR p.type = :type) AND " +
           "(:indexType IS NULL OR p.indexType = :indexType) AND " +
           "(:status IS NULL OR p.status = :status)")
    List<Publication> findByFilters(
        @Param("year") Integer year,
        @Param("type") Publication.PublicationType type,
        @Param("indexType") Publication.IndexType indexType,
        @Param("status") Publication.Status status
    );
    
    // Approval Workflow Queries
    @Query("SELECT p FROM Publication p WHERE p.status = :status AND p.addedBy.department = :department")
    List<Publication> findByStatusAndAddedByDepartment(
        @Param("status") Publication.Status status, 
        @Param("department") String department
    );
    
    @Query("SELECT p FROM Publication p WHERE p.status = :status AND p.addedBy.mentor.id = :mentorId")
    List<Publication> findByStatusAndAddedByMentorId(
        @Param("status") Publication.Status status, 
        @Param("mentorId") Long mentorId
    );
}

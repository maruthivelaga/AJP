package com.department.research.service;

import com.department.research.model.Author;
import com.department.research.model.Publication;
import com.department.research.model.User;
import com.department.research.repository.AuthorRepository;
import com.department.research.repository.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public Publication createPublication(Publication publication, List<Long> authorIds, User addedBy) {
        publication.setAddedBy(addedBy);
        publication.setStatus(Publication.Status.PENDING);

        if (authorIds != null && !authorIds.isEmpty()) {
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));
            publication.setAuthors(authors);
        }

        return publicationRepository.save(publication);
    }

    @Transactional
    public Publication updatePublication(Long id, Publication publicationDetails, List<Long> authorIds) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));

        publication.setTitle(publicationDetails.getTitle());
        publication.setType(publicationDetails.getType());
        publication.setYear(publicationDetails.getYear());
        publication.setJournal(publicationDetails.getJournal());
        publication.setConference(publicationDetails.getConference());
        publication.setDoi(publicationDetails.getDoi());
        publication.setIsbn(publicationDetails.getIsbn());
        publication.setIssn(publicationDetails.getIssn());
        publication.setIndexType(publicationDetails.getIndexType());
        publication.setVolume(publicationDetails.getVolume());
        publication.setIssue(publicationDetails.getIssue());
        publication.setPages(publicationDetails.getPages());
        publication.setPublisher(publicationDetails.getPublisher());
        publication.setBookTitle(publicationDetails.getBookTitle());
        publication.setPatentNumber(publicationDetails.getPatentNumber());
        publication.setPublicationDate(publicationDetails.getPublicationDate());
        publication.setDescription(publicationDetails.getDescription());
        publication.setKeywords(publicationDetails.getKeywords());
        publication.setUrl(publicationDetails.getUrl());
        publication.setImpactFactor(publicationDetails.getImpactFactor());

        if (authorIds != null) {
            publication.getAuthors().clear();
            Set<Author> authors = new HashSet<>(authorRepository.findAllById(authorIds));
            publication.setAuthors(authors);
        }

        return publicationRepository.save(publication);
    }

    @Transactional
    public void deletePublication(Long id) {
        publicationRepository.deleteById(id);
    }

    @Transactional
    public Publication approvePublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        publication.setStatus(Publication.Status.APPROVED);
        return publicationRepository.save(publication);
    }

    @Transactional
    public Publication rejectPublication(Long id) {
        Publication publication = publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication not found with id: " + id));
        publication.setStatus(Publication.Status.REJECTED);
        return publicationRepository.save(publication);
    }

    public Optional<Publication> getPublicationById(Long id) {
        return publicationRepository.findById(id);
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public List<Publication> getPublicationsByYear(Integer year) {
        return publicationRepository.findByYear(year);
    }

    public List<Publication> getPublicationsByType(Publication.PublicationType type) {
        return publicationRepository.findByType(type);
    }

    public List<Publication> getPublicationsByStatus(Publication.Status status) {
        return publicationRepository.findByStatus(status);
    }

    public List<Publication> getPublicationsByAuthor(Long authorId) {
        return publicationRepository.findByAuthorId(authorId);
    }

    public List<Publication> getPublicationsByUser(Long userId) {
        return publicationRepository.findByUserId(userId);
    }

    public List<Publication> searchPublications(String title) {
        return publicationRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Publication> filterPublications(Integer year, Publication.PublicationType type,
                                                Publication.IndexType indexType, Publication.Status status) {
        return publicationRepository.findByFilters(year, type, indexType, status);
    }

    public Map<Integer, Long> getPublicationCountByYear() {
        List<Object[]> results = publicationRepository.countPublicationsByYear();
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (Integer) result[0],
                        result -> (Long) result[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    public Map<String, Long> getPublicationCountByType() {
        Map<String, Long> counts = new HashMap<>();
        for (Publication.PublicationType type : Publication.PublicationType.values()) {
            counts.put(type.name(), publicationRepository.countByType(type));
        }
        return counts;
    }

    public Long getTotalPublications() {
        return publicationRepository.count();
    }

    public Long getApprovedPublications() {
        return (long) publicationRepository.findByStatus(Publication.Status.APPROVED).size();
    }

    public Long getPendingPublications() {
        return (long) publicationRepository.findByStatus(Publication.Status.PENDING).size();
    }
}

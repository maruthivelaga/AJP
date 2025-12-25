package com.department.research.service;

import com.department.research.model.Author;
import com.department.research.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public Author createAuthor(Author author) {
        if (author.getEmail() != null && authorRepository.existsByEmail(author.getEmail())) {
            throw new RuntimeException("Author with email already exists: " + author.getEmail());
        }
        return authorRepository.save(author);
    }

    @Transactional
    public Author updateAuthor(Long id, Author authorDetails) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());
        author.setDepartment(authorDetails.getDepartment());
        author.setDesignation(authorDetails.getDesignation());
        author.setType(authorDetails.getType());
        author.setAffiliation(authorDetails.getAffiliation());
        author.setIsInternal(authorDetails.getIsInternal());
        author.setOrcid(authorDetails.getOrcid());
        author.setScopusId(authorDetails.getScopusId());
        author.setGoogleScholarId(authorDetails.getGoogleScholarId());

        return authorRepository.save(author);
    }

    @Transactional
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> searchAuthors(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Author> getInternalAuthors() {
        return authorRepository.findByIsInternalTrue();
    }

    public List<Author> getExternalAuthors() {
        return authorRepository.findByIsInternalFalse();
    }

    public List<Author> getAuthorsByDepartment(String department) {
        return authorRepository.findByDepartment(department);
    }

    public List<Author> getAuthorsByType(Author.AuthorType type) {
        return authorRepository.findByType(type);
    }

    public List<Author> getAuthorsByPublication(Long publicationId) {
        return authorRepository.findByPublicationId(publicationId);
    }

    public List<Object[]> getTopAuthors() {
        return authorRepository.findTopAuthors();
    }
}

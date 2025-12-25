package com.department.research.config;

import com.department.research.model.Author;
import com.department.research.model.Publication;
import com.department.research.model.User;
import com.department.research.repository.AuthorRepository;
import com.department.research.repository.PublicationRepository;
import com.department.research.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final PublicationRepository publicationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (userRepository.count() > 0) {
            return;
        }

        // Create Admin User
        User admin = new User();
        admin.setName("System Administrator");
        admin.setEmail("admin@dept.edu");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        admin.setDepartment("Computer Science");
        admin.setDesignation("Administrator");
        admin.setPhone("1234567890");
        admin.setActive(true);
        userRepository.save(admin);

        // Create Faculty User
        User faculty = new User();
        faculty.setName("Dr. John Smith");
        faculty.setEmail("faculty@dept.edu");
        faculty.setPassword(passwordEncoder.encode("faculty123"));
        faculty.setRole(User.Role.FACULTY);
        faculty.setDepartment("Computer Science");
        faculty.setDesignation("Associate Professor");
        faculty.setPhone("9876543210");
        faculty.setActive(true);
        faculty = userRepository.save(faculty);

        // Create HOD User (STEP 1.2)
        User hod = new User();
        hod.setName("Dr. Sarah Williams");
        hod.setEmail("hod@dept.edu");
        hod.setPassword(passwordEncoder.encode("hod123"));
        hod.setRole(User.Role.HOD);
        hod.setDepartment("Computer Science");
        hod.setDesignation("Head of Department");
        hod.setPhone("1112223333");
        hod.setActive(true);
        hod = userRepository.save(hod);

        // Create Student User with Mentor (STEP 1.3)
        User student = new User();
        student.setName("Alice Johnson");
        student.setEmail("student@dept.edu");
        student.setPassword(passwordEncoder.encode("student123"));
        student.setRole(User.Role.STUDENT);
        student.setDepartment("Computer Science");
        student.setDesignation("PhD Scholar");
        student.setPhone("5555555555");
        student.setActive(true);
        student.setMentor(faculty);  // Student mentored by faculty
        student = userRepository.save(student);

        // Create and save Authors first (they must be managed entities)
        Author author1 = new Author();
        author1.setName("Dr. John Smith");
        author1.setEmail("faculty@dept.edu");
        author1.setDepartment("Computer Science");
        author1.setDesignation("Associate Professor");
        author1.setType(Author.AuthorType.FACULTY);
        author1.setIsInternal(true);
        author1.setAffiliation("XYZ University");
        author1 = authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Alice Johnson");
        author2.setEmail("student@dept.edu");
        author2.setDepartment("Computer Science");
        author2.setDesignation("PhD Scholar");
        author2.setType(Author.AuthorType.STUDENT);
        author2.setIsInternal(true);
        author2.setAffiliation("XYZ University");
        author2 = authorRepository.save(author2);

        Author author3 = new Author();
        author3.setName("Dr. Robert Brown");
        author3.setEmail("robert@external.edu");
        author3.setDepartment("Information Technology");
        author3.setDesignation("Professor");
        author3.setType(Author.AuthorType.EXTERNAL);
        author3.setIsInternal(false);
        author3.setAffiliation("ABC Institute");
        author3 = authorRepository.save(author3);

        // Create Sample Publications
        Publication pub1 = new Publication();
        pub1.setTitle("Machine Learning Approaches for Big Data Analytics");
        pub1.setType(Publication.PublicationType.JOURNAL);
        pub1.setYear(2024);
        pub1.setJournal("International Journal of Computer Science");
        pub1.setDoi("10.1234/ijcs.2024.001");
        pub1.setIssn("1234-5678");
        pub1.setIndexType(Publication.IndexType.SCOPUS);
        pub1.setVolume("45");
        pub1.setIssue("3");
        pub1.setPages("123-145");
        pub1.setPublicationDate(LocalDate.of(2024, 3, 15));
        pub1.setDescription("This paper presents novel machine learning approaches for analyzing big data.");
        pub1.setKeywords("Machine Learning, Big Data, Analytics");
        pub1.setImpactFactor(3.5);
        pub1.setStatus(Publication.Status.APPROVED);
        pub1.setAddedBy(faculty);
        // Link managed author entities
        pub1.getAuthors().add(author1);
        pub1.getAuthors().add(author2);
        publicationRepository.save(pub1);

        Publication pub2 = new Publication();
        pub2.setTitle("Cloud Computing Security: Challenges and Solutions");
        pub2.setType(Publication.PublicationType.CONFERENCE);
        pub2.setYear(2023);
        pub2.setConference("International Conference on Cloud Computing (ICCC 2023)");
        pub2.setDoi("10.1109/ICCC.2023.123456");
        pub2.setIndexType(Publication.IndexType.WEB_OF_SCIENCE);
        pub2.setPages("45-52");
        pub2.setPublisher("IEEE");
        pub2.setPublicationDate(LocalDate.of(2023, 9, 20));
        pub2.setDescription("A comprehensive study on cloud computing security challenges.");
        pub2.setKeywords("Cloud Computing, Security, Cybersecurity");
        pub2.setStatus(Publication.Status.APPROVED);
        pub2.setAddedBy(faculty);
        // Link managed author entities
        pub2.getAuthors().add(author1);
        pub2.getAuthors().add(author3);
        publicationRepository.save(pub2);

        Publication pub3 = new Publication();
        pub3.setTitle("AI-Powered Healthcare Management System");
        pub3.setType(Publication.PublicationType.BOOK_CHAPTER);
        pub3.setYear(2024);
        pub3.setBookTitle("Advances in Healthcare Technology");
        pub3.setIsbn("978-1-234567-89-0");
        pub3.setPublisher("Springer");
        pub3.setPages("201-225");
        pub3.setPublicationDate(LocalDate.of(2024, 1, 10));
        pub3.setDescription("A book chapter discussing AI applications in healthcare management.");
        pub3.setKeywords("Artificial Intelligence, Healthcare, Management Systems");
        pub3.setStatus(Publication.Status.PENDING);
        pub3.setAddedBy(faculty);
        // Link managed author entities
        pub3.getAuthors().add(author1);
        pub3.getAuthors().add(author2);
        publicationRepository.save(pub3);

        System.out.println("Sample data initialized successfully!");
        System.out.println("Admin Login: admin@dept.edu / admin123");
        System.out.println("HOD Login: hod@dept.edu / hod123");
        System.out.println("Faculty Login: faculty@dept.edu / faculty123");
        System.out.println("Student Login: student@dept.edu / student123");
        System.out.println("Student's Mentor: Dr. John Smith (Faculty)");
    }
}

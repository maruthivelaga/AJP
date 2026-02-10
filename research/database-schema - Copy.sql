-- PostgreSQL Database Schema for Research Publications Management System

-- Drop tables if they exist (for clean setup)
DROP TABLE IF EXISTS publication_authors CASCADE;
DROP TABLE IF EXISTS publications CASCADE;
DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Users Table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'FACULTY', 'STUDENT')),
    department VARCHAR(100),
    designation VARCHAR(50),
    phone VARCHAR(20),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Authors Table
CREATE TABLE authors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    department VARCHAR(100),
    designation VARCHAR(50),
    type VARCHAR(20) CHECK (type IN ('FACULTY', 'STUDENT', 'EXTERNAL')),
    affiliation VARCHAR(100),
    is_internal BOOLEAN NOT NULL DEFAULT TRUE,
    orcid VARCHAR(100),
    scopus_id VARCHAR(100),
    google_scholar_id VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Publications Table
CREATE TABLE publications (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('JOURNAL', 'CONFERENCE', 'PATENT', 'BOOK_CHAPTER', 'FUNDED_PROJECT')),
    year INTEGER NOT NULL,
    journal VARCHAR(300),
    conference VARCHAR(300),
    doi VARCHAR(100),
    isbn VARCHAR(100),
    issn VARCHAR(100),
    index_type VARCHAR(50) CHECK (index_type IN ('SCOPUS', 'WEB_OF_SCIENCE', 'UGC_CARE', 'SCI', 'SCIE', 'ESCI', 'NOT_INDEXED')),
    volume VARCHAR(50),
    issue VARCHAR(50),
    pages VARCHAR(50),
    publisher VARCHAR(200),
    book_title VARCHAR(200),
    patent_number VARCHAR(100),
    publication_date DATE,
    description TEXT,
    keywords VARCHAR(500),
    url VARCHAR(500),
    impact_factor DECIMAL(5,2),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING' CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
    added_by_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Publication-Authors Junction Table (Many-to-Many)
CREATE TABLE publication_authors (
    publication_id BIGINT NOT NULL REFERENCES publications(id) ON DELETE CASCADE,
    author_id BIGINT NOT NULL REFERENCES authors(id) ON DELETE CASCADE,
    PRIMARY KEY (publication_id, author_id)
);

-- Create Indexes for Better Performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_authors_name ON authors(name);
CREATE INDEX idx_authors_email ON authors(email);
CREATE INDEX idx_publications_year ON publications(year);
CREATE INDEX idx_publications_type ON publications(type);
CREATE INDEX idx_publications_status ON publications(status);
CREATE INDEX idx_publications_index_type ON publications(index_type);
CREATE INDEX idx_publication_authors_publication ON publication_authors(publication_id);
CREATE INDEX idx_publication_authors_author ON publication_authors(author_id);

-- Comments for Documentation
COMMENT ON TABLE users IS 'Stores system users with roles: ADMIN, FACULTY, STUDENT';
COMMENT ON TABLE authors IS 'Stores author information for publications';
COMMENT ON TABLE publications IS 'Stores all types of research publications';
COMMENT ON TABLE publication_authors IS 'Maps publications to their authors (many-to-many)';

-- Sample Data Initialization (Optional - can be removed if using DataInitializer.java)
-- INSERT INTO users (name, email, password, role, department, designation, phone) VALUES
-- ('Admin', 'admin@dept.edu', '$2a$10$...', 'ADMIN', 'Computer Science', 'Administrator', '1234567890');

-- Library Management System Database Initialization Script
-- This script creates the database and initializes sample data

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS lms_db;
USE lms_db;

-- Drop tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS borrowing_transactions;
DROP TABLE IF EXISTS book_authors;
DROP TABLE IF EXISTS book_categories;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS publishers;
DROP TABLE IF EXISTS languages;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

-- Create roles table
CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE
);

-- Create users table
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

-- Create members table
CREATE TABLE members (
    member_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    membership_date DATE
);

-- Create languages table
CREATE TABLE languages (
    language_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Create publishers table
CREATE TABLE publishers (
    publisher_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL UNIQUE,
    address TEXT,
    contact_info VARCHAR(255)
);

-- Create categories table
CREATE TABLE categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- Create authors table
CREATE TABLE authors (
    author_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    bio TEXT
);

-- Create books table
CREATE TABLE books (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    edition VARCHAR(10),
    publication_year INT,
    summary TEXT,
    cover_image VARCHAR(255),
    publisher_id BIGINT,
    language_id BIGINT,
    FOREIGN KEY (publisher_id) REFERENCES publishers(publisher_id),
    FOREIGN KEY (language_id) REFERENCES languages(language_id)
);

-- Create book_authors junction table
CREATE TABLE book_authors (
    book_id BIGINT,
    author_id BIGINT,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE
);

-- Create book_categories junction table
CREATE TABLE book_categories (
    book_id BIGINT,
    category_id BIGINT,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE CASCADE
);

-- Create borrowing_transactions table
CREATE TABLE borrowing_transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    book_id BIGINT,
    member_id BIGINT,
    borrow_date DATE,
    due_date DATE,
    return_date DATE,
    status ENUM('BORROWED', 'RETURNED', 'LATE') NOT NULL,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);

-- Insert sample roles
INSERT INTO roles (role_name) VALUES 
('ADMIN'),
('LIBRARIAN'),
('STAFF');

-- Insert sample users (passwords are hashed with BCrypt)
-- Default passwords: admin123, librarian123, staff123
INSERT INTO users (username, password_hash, email, role_id) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'admin@lms.com', 1),
('librarian', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'librarian@lms.com', 2),
('staff', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'staff@lms.com', 3);

-- Insert sample languages
INSERT INTO languages (name) VALUES 
('English'),
('Spanish'),
('French'),
('German'),
('Italian');

-- Insert sample publishers
INSERT INTO publishers (name, address, contact_info) VALUES 
('Bloomsbury Publishing', '50 Bedford Square, London WC1B 3DP, UK', '+44 20 7631 5600'),
('Secker & Warburg', '14 Carlisle Street, London W1D 3BN, UK', '+44 20 7631 5600'),
('J.B. Lippincott & Co.', 'Philadelphia, PA, USA', '+1 215 238 4200'),
('Penguin Random House', '1745 Broadway, New York, NY 10019, USA', '+1 212 782 9000'),
('HarperCollins', '195 Broadway, New York, NY 10007, USA', '+1 212 207 7000');

-- Insert sample categories
INSERT INTO categories (name) VALUES 
('Fiction'),
('Fantasy'),
('Dystopian'),
('Classic'),
('Science Fiction'),
('Mystery'),
('Romance'),
('Biography'),
('History'),
('Self-Help');

-- Insert sample authors
INSERT INTO authors (first_name, last_name, bio) VALUES 
('J.K.', 'Rowling', 'British author, best known for the Harry Potter series'),
('George', 'Orwell', 'English novelist, essayist, journalist, and critic'),
('Harper', 'Lee', 'American novelist best known for To Kill a Mockingbird'),
('F. Scott', 'Fitzgerald', 'American novelist and short story writer'),
('Ernest', 'Hemingway', 'American novelist, short story writer, and journalist'),
('Jane', 'Austen', 'English novelist known for her social commentary'),
('Charles', 'Dickens', 'English writer and social critic'),
('Mark', 'Twain', 'American writer, humorist, entrepreneur, publisher, and lecturer');

-- Insert sample books
INSERT INTO books (title, isbn, edition, publication_year, summary, cover_image, publisher_id, language_id) VALUES 
('Harry Potter and the Philosopher\'s Stone', '978-0747532699', '1st', 1997, 'The first book in the Harry Potter series', 'harry_potter_1.jpg', 1, 1),
('1984', '978-0451524935', '1st', 1949, 'A dystopian social science fiction novel', '1984.jpg', 2, 1),
('To Kill a Mockingbird', '978-0061120084', '1st', 1960, 'A novel about racial injustice and childhood innocence', 'mockingbird.jpg', 3, 1),
('The Great Gatsby', '978-0743273565', '1st', 1925, 'A classic American novel about the Jazz Age', 'great_gatsby.jpg', 4, 1),
('Pride and Prejudice', '978-0141439518', '1st', 1813, 'A romantic novel of manners', 'pride_prejudice.jpg', 5, 1),
('The Catcher in the Rye', '978-0316769174', '1st', 1951, 'A coming-of-age story', 'catcher_rye.jpg', 4, 1);

-- Insert book-author relationships
INSERT INTO book_authors (book_id, author_id) VALUES 
(1, 1), -- Harry Potter - J.K. Rowling
(2, 2), -- 1984 - George Orwell
(3, 3), -- To Kill a Mockingbird - Harper Lee
(4, 4), -- The Great Gatsby - F. Scott Fitzgerald
(5, 6), -- Pride and Prejudice - Jane Austen
(6, 4); -- The Catcher in the Rye - F. Scott Fitzgerald

-- Insert book-category relationships
INSERT INTO book_categories (book_id, category_id) VALUES 
(1, 1), (1, 2), -- Harry Potter - Fiction, Fantasy
(2, 1), (2, 3), (2, 4), -- 1984 - Fiction, Dystopian, Classic
(3, 1), (3, 4), -- To Kill a Mockingbird - Fiction, Classic
(4, 1), (4, 4), -- The Great Gatsby - Fiction, Classic
(5, 1), (5, 4), (5, 7), -- Pride and Prejudice - Fiction, Classic, Romance
(6, 1), (6, 4); -- The Catcher in the Rye - Fiction, Classic

-- Insert sample members
INSERT INTO members (first_name, last_name, email, phone, address, membership_date) VALUES 
('John', 'Doe', 'john.doe@email.com', '+1 555 123 4567', '123 Main St, Anytown, USA', '2024-01-01'),
('Jane', 'Smith', 'jane.smith@email.com', '+1 555 987 6543', '456 Oak Ave, Somewhere, USA', '2024-01-15'),
('Bob', 'Johnson', 'bob.johnson@email.com', '+1 555 456 7890', '789 Pine St, Elsewhere, USA', '2024-02-01'),
('Alice', 'Williams', 'alice.williams@email.com', '+1 555 321 0987', '321 Elm St, Nowhere, USA', '2024-02-15'),
('Charlie', 'Brown', 'charlie.brown@email.com', '+1 555 654 3210', '654 Maple Ave, Anywhere, USA', '2024-03-01');

-- Insert sample borrowing transactions
INSERT INTO borrowing_transactions (book_id, member_id, borrow_date, due_date, return_date, status) VALUES 
(1, 1, '2024-01-10', '2024-01-24', '2024-01-20', 'RETURNED'),
(2, 2, '2024-01-15', '2024-01-29', NULL, 'BORROWED'),
(3, 3, '2024-01-20', '2024-02-03', '2024-02-01', 'RETURNED'),
(4, 1, '2024-02-01', '2024-02-15', NULL, 'BORROWED'),
(5, 4, '2024-02-10', '2024-02-24', NULL, 'BORROWED');

-- Create indexes for better performance
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_members_email ON members(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_transactions_status ON borrowing_transactions(status);
CREATE INDEX idx_transactions_due_date ON borrowing_transactions(due_date);
CREATE INDEX idx_transactions_member ON borrowing_transactions(member_id);
CREATE INDEX idx_transactions_book ON borrowing_transactions(book_id);

-- Create views for common queries
CREATE VIEW active_borrowings AS
SELECT 
    bt.transaction_id,
    b.title as book_title,
    b.isbn,
    CONCAT(m.first_name, ' ', m.last_name) as member_name,
    m.email as member_email,
    bt.borrow_date,
    bt.due_date,
    bt.status
FROM borrowing_transactions bt
JOIN books b ON bt.book_id = b.book_id
JOIN members m ON bt.member_id = m.member_id
WHERE bt.status = 'BORROWED';

CREATE VIEW overdue_books AS
SELECT 
    bt.transaction_id,
    b.title as book_title,
    b.isbn,
    CONCAT(m.first_name, ' ', m.last_name) as member_name,
    m.email as member_email,
    m.phone as member_phone,
    bt.borrow_date,
    bt.due_date,
    DATEDIFF(CURDATE(), bt.due_date) as days_overdue
FROM borrowing_transactions bt
JOIN books b ON bt.book_id = b.book_id
JOIN members m ON bt.member_id = m.member_id
WHERE bt.status = 'BORROWED' AND bt.due_date < CURDATE();

CREATE VIEW book_availability AS
SELECT 
    b.book_id,
    b.title,
    b.isbn,
    CASE 
        WHEN bt.status = 'BORROWED' THEN 'NOT_AVAILABLE'
        ELSE 'AVAILABLE'
    END as availability_status,
    bt.due_date as next_available_date
FROM books b
LEFT JOIN borrowing_transactions bt ON b.book_id = bt.book_id AND bt.status = 'BORROWED';

-- Grant permissions (adjust as needed for your environment)
-- GRANT ALL PRIVILEGES ON lms_db.* TO 'lms_user'@'localhost' IDENTIFIED BY 'lms_password';
-- FLUSH PRIVILEGES;

-- Display summary
SELECT 'Database initialization completed successfully!' as status;
SELECT COUNT(*) as total_roles FROM roles;
SELECT COUNT(*) as total_users FROM users;
SELECT COUNT(*) as total_members FROM members;
SELECT COUNT(*) as total_books FROM books;
SELECT COUNT(*) as total_authors FROM authors;
SELECT COUNT(*) as total_categories FROM categories;
SELECT COUNT(*) as total_publishers FROM publishers;
SELECT COUNT(*) as total_languages FROM languages;
SELECT COUNT(*) as total_transactions FROM borrowing_transactions;



# Library Management System - Entity Relationship Diagram

## Database Schema Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      ROLES      │    │      USERS      │    │    MEMBERS      │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ role_id (PK)    │◄───┤ user_id (PK)    │    │ member_id (PK)  │
│ role_name       │    │ username        │    │ first_name      │
└─────────────────┘    │ password_hash   │    │ last_name       │
                       │ email           │    │ email           │
                       │ role_id (FK)    │    │ phone           │
                       └─────────────────┘    │ address         │
                                              │ membership_date │
                                              └─────────────────┘
                                                       │
                                                       │ 1:N
                                                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│     BOOKS       │    │   BORROWING_    │    │   TRANSACTIONS  │
├─────────────────┤    │   TRANSACTIONS  │    ├─────────────────┤
│ book_id (PK)    │◄───┤ transaction_id  │    │ transaction_id  │
│ title           │    │ (PK)            │    │ (PK)            │
│ isbn            │    │ book_id (FK)    │    │ book_id (FK)    │
│ edition         │    │ member_id (FK)  │    │ member_id (FK)  │
│ publication_year│    │ borrow_date     │    │ borrow_date     │
│ summary         │    │ due_date        │    │ due_date        │
│ cover_image     │    │ return_date     │    │ return_date     │
│ publisher_id(FK)│    │ status          │    │ status          │
│ language_id(FK) │    └─────────────────┘    └─────────────────┘
└─────────────────┘
         │
         │ 1:N
         ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   PUBLISHERS    │    │   LANGUAGES     │    │    AUTHORS      │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ publisher_id(PK)│    │ language_id(PK) │    │ author_id (PK)  │
│ name            │    │ name            │    │ first_name      │
│ address         │    └─────────────────┘    │ last_name       │
│ contact_info    │                           │ bio             │
└─────────────────┘                           └─────────────────┘
                                                       │
                                                       │ M:N
                                                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  BOOK_AUTHORS   │    │ BOOK_CATEGORIES │    │   CATEGORIES    │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ book_id (FK)    │    │ book_id (FK)    │    │ category_id(PK) │
│ author_id (FK)  │    │ category_id(FK) │    │ name            │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## Entity Relationships

### One-to-Many Relationships
1. **ROLES → USERS**: One role can have many users
2. **PUBLISHERS → BOOKS**: One publisher can publish many books
3. **LANGUAGES → BOOKS**: One language can be used for many books
4. **MEMBERS → BORROWING_TRANSACTIONS**: One member can have many borrowing transactions
5. **BOOKS → BORROWING_TRANSACTIONS**: One book can have many borrowing transactions

### Many-to-Many Relationships
1. **BOOKS ↔ AUTHORS**: Books can have multiple authors, authors can write multiple books
   - Junction table: `book_authors`
2. **BOOKS ↔ CATEGORIES**: Books can belong to multiple categories, categories can contain multiple books
   - Junction table: `book_categories`

## Key Constraints and Business Rules

### Primary Keys
- All entities have auto-incrementing primary keys
- Composite primary keys for junction tables

### Foreign Key Constraints
- All foreign keys have proper referential integrity
- Cascade delete for junction tables
- Restrict delete for main entities

### Unique Constraints
- Usernames and emails are unique across users
- Member emails are unique
- Book ISBNs are unique
- Role names are unique
- Publisher names are unique
- Category names are unique
- Language names are unique

### Business Rules
1. **Borrowing Rules**:
   - Maximum 5 books per member at a time
   - 14-day borrowing period
   - Books can be marked as BORROWED, RETURNED, or LATE

2. **User Management**:
   - Three roles: ADMIN, LIBRARIAN, STAFF
   - Passwords are hashed using BCrypt
   - Role-based access control

3. **Data Integrity**:
   - All required fields are NOT NULL
   - Email format validation
   - Date validation for borrowing periods

## Indexes for Performance

### Primary Indexes
- All primary keys are automatically indexed

### Secondary Indexes
- `idx_books_title`: For book title searches
- `idx_books_isbn`: For ISBN lookups
- `idx_members_email`: For member email lookups
- `idx_users_username`: For username authentication
- `idx_users_email`: For email lookups
- `idx_transactions_status`: For filtering by transaction status
- `idx_transactions_due_date`: For overdue book queries
- `idx_transactions_member`: For member transaction history
- `idx_transactions_book`: For book borrowing history

## Views for Common Queries

### 1. active_borrowings
Shows all currently borrowed books with member and book details.

### 2. overdue_books
Shows all overdue books with member contact information and days overdue.

### 3. book_availability
Shows the availability status of all books and when they'll be available next.

## Sample Data

The database is initialized with:
- 3 roles (ADMIN, LIBRARIAN, STAFF)
- 3 default users with hashed passwords
- 5 languages
- 5 publishers
- 10 categories
- 8 authors
- 6 sample books
- 5 library members
- 5 sample borrowing transactions

## Database Configuration

- **Engine**: MySQL 8.0+
- **Character Set**: UTF-8
- **Collation**: utf8mb4_unicode_ci
- **Storage Engine**: InnoDB (for foreign key support)
- **Auto-increment**: Starting from 1

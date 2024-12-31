# Library Management System

This project is a full-stack application developed using **Spring Boot** for the backend and **ReactJS** for the frontend. It provides a comprehensive API for managing a library's book collection, user accounts, and associated operations, with authentication and authorization via JWT.

## Key Features

### Authentication & Authorization
- **Login**: Users can authenticate using JWT tokens.  
  `POST /auth/login`

### User Management
- **User Registration**: New users can register.  
  `POST /users/register`
- **Profile Management**:  
  - View profile: `GET /users/profile`  
  - Update profile: `PUT /users/update/profile`  
  - Admin/Librarian can manage users by ID:  
    - Get: `GET /users/id/{userId}`  
    - Update: `PUT /users/update/id/{userId}`  
    - Delete: `DELETE /users/delete/id/{userId}`  
  - Get all users (Admin/Librarian): `GET /users/getAllUsers`

### Book Management
- **Add Books**: Add new books with optional image upload.  
  `POST /api/books/add`  
- **Retrieve Books**:  
  - Get by book code: `GET /api/books/BookCode/{bookCode}`  
  - Get all books: `GET /api/books/getAll`  
- **Update Books**: Update book details by book code.  
  `PUT /api/books/updateBook/{bookCode}`  
- **Delete Books**: Remove a book by book code.  
  `DELETE /api/books/delete/{bookCode}`  

### Book Requests
- **Create Request**: Users can request books.  
  `POST /api/book-requests`
- **Retrieve Requests**:  
  - Get all requests (Admin): `GET /api/book-requests/Admin`  
  - Get requests by user (Student): `GET /api/book-requests/student`  

### Issued Books
- **Issue Book Records**:  
  - View all records: `GET /api/book-issued`  
  - View record by ID: `GET /api/book-issued/{id}`  
  - Create new record: `POST /api/book-issued`  
- **Retrieve Issued Books**:  
  - Get returned books by user: `GET /api/book-issued/returnedBooks`  
  - Get currently issued books by user: `GET /api/book-issued/issuedBooks`  

## Roles and Permissions
The application implements role-based access control (RBAC) with the following roles:
- **ADMIN**: Full access to all resources.
- **LIBRARIAN**: Can manage books, view user details, and handle book requests.
- **STUDENT**: Limited access to viewing and requesting books, and viewing their own profile.

## Tech Stack
- **Frontend**: ReactJS
- **Backend**: Spring Boot, Spring Security, JWT
- **Database**: MySQL
- **Build Tool**: Maven



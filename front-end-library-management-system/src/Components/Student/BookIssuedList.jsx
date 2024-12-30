import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BookIssuedList.css';

const StudentIssuedBooks = () => {
  const [issuedBooks, setIssuedBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch issued books data from API
    axios
      .get('http://localhost:8080/api/book-issued/issuedBooks', {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Optional, for authenticated requests
        },
      })
      .then((response) => {
        setIssuedBooks(response.data);
        setLoading(false);
      })
      .catch((error) => {
        setError('Failed to fetch issued books data.');
        setLoading(false);
        console.error(error);
      });
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  if (error) {
    return <p>{error}</p>;
  }

  return (
    <div className="student-issued-books">
      <h1 className="student-title">Issued Books</h1>
      {issuedBooks.length === 0 ? (
        <p>No books have been issued.</p>
      ) : (
        <table className="student-issued-table">
          <thead>
            <tr>
              <th>Book Name</th>
              <th>Author</th>
              <th>Issue Date</th>
              <th>Due Date</th>
            </tr>
          </thead>
          <tbody>
            {issuedBooks.map((book, index) => (
              <tr key={index}>
                <td>{book.bookName}</td>
                <td>{book.bookAuthor}</td>
                <td>{new Date(book.issuedDate).toLocaleDateString()}</td>
                <td>{new Date(book.dueDate).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default StudentIssuedBooks;

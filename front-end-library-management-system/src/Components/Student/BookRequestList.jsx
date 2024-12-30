import React, { useEffect, useState } from 'react';
import './BookRequestList.css';
import axios from 'axios';

const StudentRequestBooks = () => {
  const [requestedBooks, setRequestedBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRequestedBooks = async () => {
      try {
        setLoading(true);
        // Make an API call to fetch the book requests using axios
        const response = await axios.get('http://localhost:8080/api/book-requests/student', {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Optional, for authenticated requests
          },
        });

        console.log('Fetched book requests successfully:', response.data);
        // Update the state with the fetched data
        setRequestedBooks(response.data);
      } catch (error) {
        console.error('Error fetching the requested books:', error);
        setError('Failed to fetch requested books.');
      } finally {
        setLoading(false);
      }
    };

    fetchRequestedBooks();
  }, []);

  if (loading) return <p>Loading requested books...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="student-book-request-list">
      <h1 className="student-heading">My Requested Books</h1>
      {requestedBooks.length === 0 ? (
        <p className="student-no-books">No books requested yet.</p>
      ) : (
        <table className="student-requested-books-table">
          <thead>
            <tr>
              <th>Book Name</th>
              <th>Author</th>
              <th>Status</th>
              <th>Requested Date</th>
            </tr>
          </thead>
          <tbody>
            {requestedBooks.map((book, index) => (
              <tr key={index}>
                <td>{book.bookName}</td>
                <td>{book.bookAuthor}</td>
                <td>
                  <span className={`student-status student-${book.status.toLowerCase()}`}>
                    {book.status}
                  </span>
                </td>
                <td>{book.requestDate}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default StudentRequestBooks;

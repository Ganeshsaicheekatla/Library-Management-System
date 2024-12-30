import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './BookReturnList.css';

const StudentReturnedBooks = () => {
  const [returnedBooks, setReturnedBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchReturnedBooks = async () => {
      try {
        setLoading(true);
        const response = await axios.get('http://localhost:8080/api/book-issued/returnedBooks', {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Optional, for authenticated requests
          },
        }
        );
        setReturnedBooks(response.data);
      } catch (err) {
        console.error('Error fetching returned books:', err);
        setError('Failed to fetch returned books.');
      } finally {
        setLoading(false);
      }
    };

    fetchReturnedBooks();
  }, []);

  const calculateTotalFine = () =>
    returnedBooks.reduce((total, book) => total + (book.fine || 0), 0);

  if (loading) return <p>Loading returned books...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className="student-book-return-list">
      <h1 className="student-heading">Returned Books</h1>
      {returnedBooks.length === 0 ? (
        <p className="student-no-books">No books returned yet.</p>
      ) : (
        <table className="student-returned-books-table">
          <thead>
            <tr>
              <th>Book Name</th>
              <th>Issue Date</th>
              <th>Due Date</th>
              <th>Return Date</th>
              <th>Fine (₹)</th>
            </tr>
          </thead>
          <tbody>
            {returnedBooks.map((book) => (
              <tr key={book.bookRequestId}>
                <td>{book.bookName}</td>
                <td>{new Date(book.issuedDate).toLocaleDateString()}</td>
                <td>{new Date(book.dueDate).toLocaleDateString()}</td>
                <td>{new Date(book.returnDate).toLocaleDateString()}</td>
                <td>{book.fine}</td>
              </tr>
            ))}
          </tbody>
          <tfoot>
            <tr>
              <td colSpan="4" className="student-total-label">
                Total Fine
              </td>
              <td className="student-total-fine">₹{calculateTotalFine()}</td>
            </tr>
          </tfoot>
        </table>
      )}
    </div>
  );
};

export default StudentReturnedBooks;

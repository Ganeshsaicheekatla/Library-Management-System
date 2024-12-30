import React, { useState, useEffect } from 'react';
import BookCard from './BookCard';  // Import the BookCard component
import './../style/DashboardContent.css';  // Your styles
import axios from 'axios';

const DashboardContent = ({ isSidebarOpen }) => {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);  // Loading state to control rendering

  useEffect(() => {
    // Make the API call to fetch books
    const token = localStorage.getItem('jwtToken'); // Retrieve the token from localStorage
    
    axios.get('http://localhost:8080/api/books/getAll' ,{
      headers: {
        Authorization: `Bearer ${token}`, // Attach the JWT token
      },
    }) // Adjust the URL to your actual API endpoint
      .then(response => {
        // Assuming response.data is an array of book objects with bookRequest and imageBase64
        setBooks(response.data);  // Set the books state with the response data
        setLoading(false);  // Set loading to false once the data is fetched
     
      })
      .catch(error => {
        console.error('There was an error fetching the books!', error);
        setLoading(false);  // If there's an error, still stop loading
      });
  }, []); // Empty dependency array ensures this runs only once when the component mounts

  // Show a loading message or spinner until the data is fetched
  if (loading) {
    return <div className="loading">Loading books...</div>;
  }

  return (
    <div className={`main-content ${isSidebarOpen ? 'with-sidebar' : 'without-sidebar'}`}>
      <h2>Available Books</h2>
    
      <div className={`book-list ${isSidebarOpen ? 'with-sidebar' : 'without-sidebar'}`}>
        {books.length > 0 ? (
          books.map((bookResponse) => {
            
            // Pass the bookRequest and imageBase64 to BookCard
            return (
              <BookCard
              
                key={bookResponse.bookAddRequest.bookCode}
                bookRequest={bookResponse.bookAddRequest}
                imageBase64={bookResponse.imageBase64}
              />
            );
          })
        ) : (
          <p>No books available.</p>
        )}
      </div>
    </div>
  );
};

export default DashboardContent;

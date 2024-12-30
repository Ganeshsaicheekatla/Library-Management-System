import React, { useState } from 'react';
import axios from 'axios';
import './../style/BookCard.css';

const getRandomImage = () => {
  const randomImages = [
    'https://via.placeholder.com/150?text=No+Image',
    'https://via.placeholder.com/150/0000FF/808080?text=No+Image',
    'https://via.placeholder.com/150/FF0000/FFFFFF?text=No+Image',
  ];
  return randomImages[Math.floor(Math.random() * randomImages.length)];
};

const BookCard = ({ bookRequest, imageBase64 }) => {
  const [isPending, setIsPending] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [updatedBook, setUpdatedBook] = useState({ ...bookRequest });

  const imageSrc = imageBase64 ? `data:image/jpeg;base64,${imageBase64}` : getRandomImage();
  const role = localStorage.getItem('role');

  const handleRequest = async (bookCode) => {
    setIsPending(true);
    const requestData = {
      requestDate: new Date().toISOString().split('T')[0],
      bookCode,
      status: 'PENDING',
    };

    try {
      const response = await axios.post('http://localhost:8080/api/book-requests', requestData, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
        },
      });
      console.log('Book request submitted successfully:', response.data);
    } catch (error) {
      console.error('Error making the request:', error);
      setIsPending(false);
    }
  };



  const handleUpdate = async () => {
    for (const [key, value] of Object.entries(updatedBook)) {
      console.log(updatedBook);
      if (key !== "bookImage" && (value === "" || value === null || value === undefined)) {
        alert(`The field "${key}" cannot be empty.`);
        return;
      }
    }

    try {
      const response = await axios.put(
        `http://localhost:8080/api/books/updateBook/${updatedBook.bookCode}`,
        updatedBook,
        {
          headers: {
            
            Authorization: `Bearer ${localStorage.getItem('jwtToken')}`,
          },
        }
      );
      console.log('Book updated successfully:', response.data);
      setIsEditing(false);
    } catch (error) {
      console.error('Error updating book:', error);
    }
  };

  const handleInputChange = (field, value) => {
    setUpdatedBook((prevState) => ({ ...prevState, [field]: value }));
  };

  return (
    <div className="book-card">
    <div className="book-card-body">
      <img src={imageSrc} alt={bookRequest.bookName} className="book-image" />
      {isEditing ? (
        <>
          <label>
            Book Name:
            <input
              type="text"
              value={updatedBook.bookName}
              onChange={(e) => handleInputChange('bookName', e.target.value)}
              placeholder="Book Name"
            />
          </label>
          <label>
            Book Author:
            <input
              type="text"
              value={updatedBook.bookAuthor}
              onChange={(e) => handleInputChange('bookAuthor', e.target.value)}
              placeholder="Book Author"
            />
          </label>
          <label>
            Book Floor:
            <input
              type="text"
              value={updatedBook.bookFloor}
              onChange={(e) => handleInputChange('bookFloor', e.target.value)}
              placeholder="Book Floor"
            />
          </label>
          <label>
            Book Shulf:
            <input
              type="text"
              value={updatedBook.bookShulf}
              onChange={(e) => handleInputChange('bookShulf', e.target.value)}
              placeholder="Book Shulf"
            />
          </label>
          <label>
            Book Room No:
            <input
              type="text"
              value={updatedBook.bookRoomCode}
              onChange={(e) => handleInputChange('bookRoomCode', e.target.value)}
              placeholder="Book Room No"
            />
          </label>
          <label>
            Quantity:
            <input
              type="number"
              value={updatedBook.quantity}
              onChange={(e) => handleInputChange('quantity', e.target.value)}
              placeholder="Quantity"
            />
          </label>
          <label>
            Price:
            <input
              type="number"
              value={updatedBook.price}
              onChange={(e) => handleInputChange('price', e.target.value)}
              placeholder="Price"
            />
          </label>
        </>
      ) : (
        <>
          <h5 className="book-title">Book Name: {updatedBook.bookName}</h5>
          <h6 className="book-author">Book Author: {updatedBook.bookAuthor}</h6>
          <p className="book-code">Code: {updatedBook.bookCode}</p>
          <p className="book-floor">Book Floor: {updatedBook.bookFloor}</p>
          <p className="book-shulf">Book Shulf: {updatedBook.bookShulf}</p>
          <p className="book-room">Book Room No: {updatedBook.bookRoomCode}</p>
          <p className="book-quantity">Quantity: {updatedBook.quantity}</p>
          <p className="book-price">Price: ${updatedBook.price}</p>
        </>
      )}

      {role === 'ROLE_STUDENT' && (
        <button
          className="book-request"
          onClick={() => handleRequest(updatedBook.bookCode)}
          disabled={isPending}
        >
          {isPending ? <span className="pending-icon">⏳ Requested</span> : 'Request'}
        </button>
      )}

      {(role === 'ROLE_ADMIN' || role === 'ROLE_LIBRARIAN') && (
        <div className="admin-buttons">
          {isEditing ? (
            <>
              <button className="book-save" onClick={handleUpdate}>
                Save
              </button>
              <button className="book-cancel" onClick={() => setIsEditing(false)}>
                Cancel
              </button>
            </>
          ) : (
            <>
              <button className="book-update" onClick={() => setIsEditing(true)}>
                Update
              </button>
            </>
          )}
        </div>
      )}
    </div>
  </div>
);
};

export default BookCard;

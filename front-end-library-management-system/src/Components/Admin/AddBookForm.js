import React, { useState } from "react";
import "./AddBookForm.css";
import axios from "axios";

const AddBookForm = ({ isSidebarOpen }) => {
  const [formData, setFormData] = useState({
    bookCode: "",
    bookName: "",
    bookAuthor: "",
    quantity: "",
    price: "",
    bookFloor: "",
    bookShulf: "",
    bookRoom: "",
    bookImage: null,
  });

  const handleChange = (e) => {
    const { name, value, type, files } = e.target;
    if (type === "file") {
      setFormData({ ...formData, bookImage: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formDataToSubmit = new FormData();
    formDataToSubmit.append(
      "book",
      JSON.stringify({
        bookCode: formData.bookCode,
        bookName: formData.bookName,
        bookAuthor: formData.bookAuthor,
        quantity: formData.quantity,
        price: formData.price,
        bookFloor: formData.bookFloor,
        bookShulf: formData.bookShulf,
        bookRoomCode: formData.bookRoom,
      })
    );

    if (formData.bookImage) {
      formDataToSubmit.append("file", formData.bookImage);
    }

    try {
      const response = await axios.post("http://localhost:8080/book/add", formDataToSubmit, {
        headers: { "Content-Type": "multipart/form-data" },
      });

      if (response.data) {
        alert("Book added successfully!");
        setFormData({
          bookCode: "",
          bookName: "",
          bookAuthor: "",
          quantity: "",
          price: "",
          bookFloor: "",
          bookShulf: "",
          bookRoom: "",
          bookImage: null,
        });
      }
    } catch (error) {
      console.error("Error:", error);
      alert("Failed to add book");
    }
  };

  return (
    <div className={`add-book-form-container ${isSidebarOpen ? "with-sidebar" : "without-sidebar"}`}>
      <h2>Add New Book</h2>
      <form className="add-book-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="bookCode">Book Code</label>
          <input
            type="text"
            id="bookCode"
            name="bookCode"
            value={formData.bookCode}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bookName">Book Name</label>
          <input
            type="text"
            id="bookName"
            name="bookName"
            value={formData.bookName}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bookAuthor">Book Author</label>
          <input
            type="text"
            id="bookAuthor"
            name="bookAuthor"
            value={formData.bookAuthor}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="quantity">Quantity</label>
          <input
            type="number"
            id="quantity"
            name="quantity"
            value={formData.quantity}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bookFloor">Book Floor</label>
          <input
            type="text"
            id="bookFloor"
            name="bookFloor"
            value={formData.bookFloor}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bookRoom">Book Room Code</label>
          <input
            type="text"
            id="bookRoom"
            name="bookRoom"
            value={formData.bookRoom}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bookShulf">Book Shelf</label>
          <input
            type="text"
            id="bookShulf"
            name="bookShulf"
            value={formData.bookShulf}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="price">Price</label>
          <input
            type="number"
            id="price"
            name="price"
            value={formData.price}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="bookImage">Book Image</label>
          <input
            type="file"
            id="bookImage"
            name="bookImage"
            onChange={handleChange}
          />
        </div>

        <button type="submit" className="submit-button">
          Add Book
        </button>
      </form>
    </div>
  );
};

export default AddBookForm;

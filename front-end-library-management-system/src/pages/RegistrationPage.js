import React, { useState } from "react";
import "./../style/RegistrationPage.css"; 
import { useNavigate } from 'react-router-dom';
import axios from "axios";

const RegistrationForm = ({ isSidebarOpen }) => {

  const navigate = useNavigate(); // Initialize navigate
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "", // New field for password
  });

  // Handle form input changes
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Handle form submission
const handleSubmit = (e) => {
    e.preventDefault();
  
    // Log form data for now (replace with actual submission logic)
    console.log(formData);
  
    axios
      .post("http://localhost:8080/users/register", formData) // Replace with actual API endpoint
      .then((response) => {
        console.log(response.status);
        if (response.status === 200) {  
          alert("Registration successful!");
          setFormData({
            name: "",
            email: "",
            password: "",
          });
          navigate('/login'); 

        } else {
          alert("Registration failed. Please try again.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("Registration failed. Please try again.");
      });
  };
  

  return (
    <div className={`registration-form-container ${isSidebarOpen ? "with-sidebar" : "without-sidebar"}`}>
      <h2>Registration Form</h2>
      <form className="registration-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            required
          />
        </div>

        <button type="submit" className="submit-button">
          Register
        </button>
      </form>
    </div>
  );
};

export default RegistrationForm;

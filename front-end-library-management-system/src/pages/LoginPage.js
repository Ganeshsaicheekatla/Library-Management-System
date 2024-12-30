import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; // Make sure to import useNavigate
import './../style/LoginPage.css';
import axios from "axios";

const LoginPage = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();


    // Basic validation
    if (username === '' || password === '') {
      setError('Please enter both username and password');
      return;
    }

    try {
      // Call the backend API for login
      const response = await axios.post('http://localhost:8080/auth/login', { username, password });
      console.log(response.data);
      if (response.status === 200) {
        const  token  = response.data.jwtToken; // Assuming `JwtResponse` contains `token`
        const  role = response.data.role;
        // Save the token (use localStorage or cookies based on your requirement)
        localStorage.setItem('jwtToken', token);
        localStorage.setItem('role' , role);
     
        if(role === 'ROLE_STUDENT')
          navigate("/student/Avaliable-book-list");
        if(role === "ROLE_ADMIN")
           navigate('/admin/dashboard');
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setError('Invalid username or password');
      } else {
        setError('An error occurred. Please try again.');
      }
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>
        {error && <p className="error-message">{error}</p>}
        <form onSubmit={handleLogin}>
          <div className="input-group">
            <label htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="input-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="login-button">Login</button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;

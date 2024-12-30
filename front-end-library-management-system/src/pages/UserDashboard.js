import React from 'react';
import './../style/UserDashboard.css';
import Navbar from '../Components/Navbar';
const UserDashboard = () => {
  return (
    <div className="dashboard-container">
       <Navbar /> 
      <h2>Welcome to your Dashboard!</h2>
      <p>This is your personalized dashboard page.</p>
      {/* Add more content for the user dashboard as needed */}
    </div>
  );
};

export default UserDashboard;

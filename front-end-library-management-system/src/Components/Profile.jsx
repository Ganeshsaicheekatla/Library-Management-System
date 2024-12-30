import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';

const UserProfile = () => {
  const [userData, setUserData] = useState(null);
  const [isEditing, setIsEditing] = useState(false);

  // Fetch user data from the API on component mount
  useEffect(() => {
    axios.get('http://localhost:8080/users/profile',{
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Optional, for authenticated requests
      },
     })
      .then(response => {
        setUserData(response.data);
      })
      .catch(error => {
        console.error('Error fetching user profile:', error);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleEdit = () => {
    setIsEditing(true);
  };

  const handleSubmit = () => {
    console.log(userData);
    for (const [key, value] of Object.entries(userData)) {
      if ((key !== "password"&&key !== "institutionId") && (value === "" || value === null || value === undefined)) {
        alert(`The field "${key}" cannot be empty.`);
        return;
      }
    }

    axios.put('http://localhost:8080/users/update/profile', userData,{
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Optional, for authenticated requests
      },
     })
      .then(response => {
        alert('Profile updated successfully!');

        setIsEditing(false);
      })
      .catch(error => {
        console.error('Error updating profile:', error);
        alert('Failed to update profile.');
      });
  };

  if (!userData) {
    return <div>Loading...</div>;
  }

  return (
    <div className="user-profile">
      <div className="user-profile-header">
        <h2>User Profile</h2>
        <button className="edit-button" onClick={isEditing ? handleSubmit : handleEdit}>
          {isEditing ? "Submit" : "Edit"}
        </button>
      </div>

      <div className="user-info">
        <div className="user-info-row">
          <label>Name:</label>
          {isEditing ? (
            <input
              type="text"
              name="name"
              value={userData.name}
              onChange={handleChange}
              required
            />
          ) : (
            <span>{userData.name}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Email:</label>
          <span>{userData.email}</span>
        </div>

        <div className="user-info-row">
          <label>Institution ID:</label>
          {isEditing ? (
            <input
              type="text"
              name="institutionId"
              value={userData.institutionId}
              onChange={handleChange}
              required
            />
          ) : (
            <span>{userData.institutionId}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Education:</label>
          {isEditing ? (
            <input
              type="text"
              name="education"
              value={userData.education}
              onChange={handleChange}
            />
          ) : (
            <span>{userData.education}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Year of Graduation(like- 2025):</label>
          {isEditing ? (
            <input
              type="Number"
              name="yearofgraduation"
              value={userData.yearofgraduation}
              onChange={handleChange}
            />
          ) : (
            <span>{userData.yearofgraduation}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Date of Birth:</label>
          {isEditing ? (
            <input
              type="date"
              name="dob"
              value={userData.dob}
              onChange={handleChange}
            />
          ) : (
            <span>{userData.dob}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Phone Number:</label>
          {isEditing ? (
            <input
              type="number"
              name="phoneNo"
              value={userData.phoneNo}
              onChange={handleChange}
            />
          ) : (
            <span>{userData.phoneNo}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Address:</label>
          {isEditing ? (
            <input
              type="text"
              name="address"
              value={userData.address}
              onChange={handleChange}
            />
          ) : (
            <span>{userData.address}</span>
          )}
        </div>

        <div className="user-info-row">
          <label>Institution name:</label>
          {isEditing ? (
            <input
              type="text"
              name="institution"
              value={userData.institution}
              onChange={handleChange}
            />
          ) : (
            <span>{userData.institution}</span>
          )}
        </div>
      </div>
    </div>
  );
};

export default UserProfile;

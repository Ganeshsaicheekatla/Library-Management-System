import React from 'react';
import {  NavLink } from 'react-router-dom';
import './StudentSideBar.css'; // Your custom styles

const StudentSidebar = () => {
  return (
    <div className="student-sidebar">
      
      <ul className="student-sidebar-list">
        <li className="student-sidebar-list-item">
          <NavLink to="/student/Avaliable-book-list" activeClassName="active">Book List</NavLink>
        </li>
        <li className="student-sidebar-list-item">
          <NavLink to="/student/book-request" activeClassName="active">Book Request</NavLink>
        </li>
        <li className="student-sidebar-list-item">
          <NavLink to="/student/book-return" activeClassName="active">Book Return</NavLink>
        </li>
        <li className="student-sidebar-list-item">
          <NavLink to="/student/book-issued" activeClassName="active">Issued Books</NavLink>
        </li>
        <li className="student-sidebar-list-item">
          <NavLink to="/student/profile" activeClassName="active">Profile</NavLink>
        </li>
        <li className="student-sidebar-list-item">
          <NavLink to="/logout" activeClassName="active">Logout</NavLink>
        </li>
      </ul>
    </div>
  );
};

export default StudentSidebar;

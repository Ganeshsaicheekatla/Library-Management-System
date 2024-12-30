import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = ({ isOpen, toggleSidebar }) => {
  return (
    <>
    <div className={`sidebar ${isOpen ? 'open' : 'closed'}`}>
      <ul className="sidebar-menu">
        <li><Link to="/admin/dashboard">Available Books</Link></li>
        <li><Link to="/admin/addbook">Add Book</Link></li>
        <li><Link to="/admin/userlist" >user List</Link></li>
        <li><Link to="/admin/addstudent">Add Student</Link></li>
        <li><Link to="/admin/issuebook">Issue Book</Link></li>
        <li><Link to="/admin/returnbook">Return Book</Link></li>
        <li><Link to="/admin/findbook">Find Book</Link></li>
        <li><Link to="/admin/findstudent">Find Student</Link></li>
        <li><Link to="/logout">Logout</Link></li>
      </ul>
     
    </div>
     <button className="sidebar-toggle" onClick={toggleSidebar}>
         ☰ Menu
     </button>
    </>
  );
};

export default Sidebar;

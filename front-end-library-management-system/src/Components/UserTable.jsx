import React, { useState, useEffect } from "react";
import axios from "axios";
import "./../style/StudentTable.css";

const UserTable = () => {
  const [users, setUsers] = useState([]);
  const [filters, setFilters] = useState({
    role: "",
    yearofgraduation: "",
  });

  useEffect(() => {
    // Fetch users from the API
    axios
      .get("http://localhost:8080/users/getAllUsers" ,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('jwtToken')}`, // Optional, for authenticated requests
        },
       })
      .then((response) => {
        const data = response.data;
        const formattedData = data.map((user) => ({
          userId: user.userId,
          name: user.name,
          role: user.role,
          yearofgraduation: user.yearofgraduation || "N/A",
        }));
        setUsers(formattedData);
      })
      .catch((error) => {
        console.error("Error fetching user data:", error);
      });
  }, []);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const filteredUsers = users.filter((user) => {
    return (
      (filters.role === "" || user.role === filters.role) &&
      (filters.yearofgraduation === "" ||
        user.yearofgraduation.toString() === filters.yearofgraduation)
    );
  });

  return (
    <div className="student-table-container">
      <h2>User List</h2>
      <div className="filters">
        <select name="role" value={filters.role} onChange={handleFilterChange}>
          <option value="">All Roles</option>
          <option value="ADMIN">Admin</option>
          <option value="STUDENT">Student</option>
        </select>
        <select
          name="yearofgraduation"
          value={filters.yearofgraduation}
          onChange={handleFilterChange}
        >
          <option value="">All Years</option>
          <option value="2021">2021</option>
          <option value="2022">2022</option>
          <option value="2023">2023</option>
          <option value="2025">2025</option>
        </select>
      </div>
      <table className="student-table">
        <thead>
          <tr>
            <th>User ID</th>
            <th>Name</th>
            <th>Role</th>
            <th>Year of Graduation</th>
          </tr>
        </thead>
        <tbody>
          {filteredUsers.length > 0 ? (
            filteredUsers.map((user) => (
              <tr key={user.userId}>
                <td>{user.userId}</td>
                <td>{user.name}</td>
                <td>{user.role}</td>
                <td>{user.yearofgraduation}</td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="4">No users match the filters.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default UserTable;

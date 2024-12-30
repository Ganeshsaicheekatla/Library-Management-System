import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import AdminDashboard from './pages/Admin/AdminDashboard';
import RegistrationForm from './pages/RegistrationPage';
import StudentPage from './pages/Student/StudentPage';
function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path='/registration' element={<RegistrationForm/>} />
        <Route path="/admin/dashboard" element={<AdminDashboard element={"dashboard"}/>} />
        <Route path="/admin/addbook" element={<AdminDashboard element={"addbook"}/>} />
        <Route path="/admin/userlist" element={<AdminDashboard element={"user"}/>} />
        <Route path="/student/Avaliable-book-list" element = {<StudentPage  element={"book-list"}/>} />
        <Route path="/student/book-request" element = {<StudentPage  element={"book-request"}/>} />
        <Route path="/student/book-return" element = {<StudentPage  element={"book-return"}/>} />
        <Route path="/student/book-issued" element = {<StudentPage  element={"book-issued"}/>} />
        <Route path="/student/profile" element = {<StudentPage  element={"profile"}/>} />
      </Routes>
    </Router>
  );
}

export default App;

import React from 'react';
import StudentSidebar from './StudentSideBar';
import './StudentPage.css'; // Custom styles for the page layout
import Navbar from '../../Components/Navbar';
import DashboardContent from '../../Components/AvailableBooks';
import StudentRequestBooks from '../../Components/Student/BookRequestList';
import StudentReturnedBooks from '../../Components/Student/BookReturnList';
import StudentIssuedBooks from '../../Components/Student/BookIssuedList';
import UserProfile from '../../Components/Profile';


const StudentPage = (props) => {

    // Function to render components based on props.element
  const handleElement = (element) => {
    switch (element) {
      case 'book-list':
        return <DashboardContent  />;
      case 'book-request':
        return <StudentRequestBooks />;
      case 'book-return':
         return <StudentReturnedBooks />;
      case 'book-issued':
         return <StudentIssuedBooks />;
      case 'profile':
        return <UserProfile />;
      default:
          
          break;
      
    }
  };
  return (
    <div className="student-page">
      {/* Navbar Component */}
      <Navbar />

      {/* Sidebar Component */}
      <StudentSidebar />

      {/* Main Content */}
      <div className="student-main-content">
        {handleElement(props.element)}
      </div>
    </div>
  );
};

export default StudentPage;

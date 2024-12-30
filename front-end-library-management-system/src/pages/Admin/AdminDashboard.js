import React, { useState } from 'react';
import Navbar from '../../Components/Navbar';
import Sidebar from '../../Components/Admin/Slidebar';
import DashboardContent from '../../Components/AvailableBooks';
import UserTable from '../../Components/UserTable';
import AddBookForm from '../../Components/Admin/AddBookForm';
const AdminDashboard = (props) => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

 
  // Function to render components based on props.element
  const handleElement = (element) => {
    switch (element) {
      case 'dashboard':
        return <DashboardContent isSidebarOpen={isSidebarOpen}  />;
      case 'addbook':
        return <AddBookForm isSidebarOpen={isSidebarOpen}  />;
      case 'user':
        return <UserTable />;
      default:
        return <DashboardContent isSidebarOpen={isSidebarOpen}  />;
    }
  };

  return (
    <div className="dashboard-container">
      <Navbar />
      <div className="content-wrapper">
        <Sidebar isOpen={isSidebarOpen} toggleSidebar={toggleSidebar} />
        <div className={`main-content ${isSidebarOpen ? 'with-sidebar' : 'without-sidebar'}`}>
          {/* Call handleElement to render appropriate content */}
          {handleElement(props.element)}
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;

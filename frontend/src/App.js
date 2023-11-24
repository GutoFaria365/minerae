// import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import './RedirectPage.css';
import './Home.css';

const RedirectedPage = () => {
  const [metallum, setMetallum] = useState([]);
  const [newMetallum, setNewMetallum] = useState({ name: '', description: '', category: '', color: '', crystalSystem: '', ultravioletFluorescence: '', otherProperties: '', mohsScale: '', formula: '' });
  const [showForm, setShowForm] = useState(false);
  const [collapsed, setCollapsed] = useState(true);
  const [activeFilter, setActiveFilter] = useState('all')
  const token = localStorage.getItem('your-token-key');

  if (!token) {
    window.location.href = '/';
  }

  useEffect(() => {
    const token = localStorage.getItem('your-token-key');
    // Fetch the existing metallum from the backend
    fetch(`http://localhost:8080/menu?filter=${activeFilter}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
    })
      .then(response => response.json())
      .then(data => {
        if (Array.isArray(data)) {
        setMetallum(data);
      } else {
        console.error('Data received is not an array:', data);
      }
      })
      .catch(error => console.error('Error fetching data:', error));
  }, [activeFilter]); // The empty dependency array ensures the effect runs only once on component mount

  const handleInputChange = event => {
    const { name, value } = event.target;
    setNewMetallum({ ...newMetallum, [name]: value });
  };

  const handleCreateMetallum = async event => {
    event.preventDefault();

    // Make a POST request to create a new metallum
    try {
      const response = await fetch('http://localhost:8080/menu/add', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newMetallum),
      });

      if (response.ok) {
        // If the metallum is created successfully, fetch the updated list of metallum
        window.location.reload();
               setNewMetallum({ name: '', description: '' }); // Clear the form fields
        setShowForm(false); // Hide the form after successful creation
        console.log('mMtallum created successfully');
      } else {
        // Handle error response
        console.error('Failed to create metallum');
      }
    } catch (error) {
      console.error('Error creating metallum:', error);
    }
  };

  function getSmallImageUrl(otherProperties) {
    if(otherProperties === 'Radioactive'){
      return './sources/Radioactive.svg';
    } else {
      return '';
    }
  }

  function getCategoryName(category) {
    if (category === 'SATIVA') {
      return 'sativa';
    } else {
      return 'indica';
    }
  }

  const toggleSidebar = (event) => {
    // Check if the click target is a button within the sidebar
    if (event.target.tagName.toLowerCase() === 'button' && event.target.closest('.sidebar')) {
      return;
    }
  
    setCollapsed(!collapsed);
  };

  const handleFilter = (filter) => {
    setActiveFilter(filter);
  };

  return (
    <div className={`app-container ${collapsed ? 'collapsed' : ''}`}>
      {/* SIDEBAR*/}
      <div className="sidebar"  onClick={toggleSidebar}>
        
        {!collapsed && (
          <>
            {/* Button to toggle the visibility of the form */}
            <button onClick={() => {
              setShowForm(!showForm);
              setNewMetallum({ name: '', description: '' }); // Clear the form fields
            }} className="new-flower-button">
            {showForm ? 'Cancel' : 'Add a New Metallum'}
            </button>
            <p>Filters</p>
             {/* Filter Buttons */}
            <button onClick={() => handleFilter('all')} className={activeFilter === 'all' ? 'active-filter' : ''}>All</button>
            <button onClick={() => handleFilter('SATIVA')} className={activeFilter === 'SATIVA' ? 'active-filter' : ''}>Sativa</button>
            <button onClick={() => handleFilter('INDICA')} className={activeFilter === 'INDICA' ? 'active-filter' : ''}>Indica</button>
          </>
        )}
      </div>
      {/* MAIN CONTENT*/}
      <div className="main-content">
        <div className="flower-container">
          

          {/* Modal for the form */}
          {showForm && (
            <div className={`modal ${showForm ? 'active' : ''}`} onClick={() => setShowForm(false)}>
              <form onSubmit={handleCreateMetallum} className="new-flower-form" onClick={(e) => e.stopPropagation()}>
                <h2>Add a New Metallum</h2>
                <label>
                  Name:
                  <input type="text" name="name" value={newMetallum.name} onChange={handleInputChange} required />
                </label>
                {/* Add other form fields as needed */}
                <button type="submit">Create Metallum</button>
              </form>
            </div>
          )}

          <h1>My Colletion</h1>
          
          {/* Metallum cards*/}
          <div className="flower-cards">
            {metallum.map(metallum => (
              <a key={metallum.id} href={metallum.description} target="_blank" rel="noopener noreferrer" className="flower-card no-link-style">
                <div className="card-content">
                  <img src={metallum.imageUrl} alt={metallum.name} className="main-image" />
                  <img src={getSmallImageUrl(metallum.otherProperties)} alt={metallum.otherProperties} className="small-image" />
                  <h2>{metallum.name}</h2>
                  <div className="flower-details">
                    <div className="column">
                      <h3>{metallum.color + ' ' + metallum.category}</h3>
                      <div className={`progress-bar ${getCategoryName(metallum.category)}`}>
                        <div
                          className={`progress-bar-fill ${getCategoryName(metallum.category)}`}
                          style={{ width: `${Math.min(parseInt(metallum.color), 100)}%` }}
                        ></div>
                      </div>
                    </div>
                    <div className="column">
                      <h3>{metallum.crystalSystem}</h3>
                    </div>
                  </div>
                </div>
              </a>
            ))}
          </div>
        </div>
      </div>
    </div>  
  );
};

const Home = () => {
  const [showRegisterForm, setShowRegisterForm] = useState(false);
  const [showLoginForm, setShowLoginForm] = useState(false);
  const [user, setUser] = useState({ username: '', password: '' });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const handleRegisterUser = async (event) => {
    event.preventDefault();

    // Make a POST request to register a new user
    try {
      const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
      });

      if (response.ok) {
        // If the user is registered successfully, close the form
        setShowRegisterForm(false);
        console.log('User registered successfully');
      } else {
        // Handle error response
        console.error('Failed to register user');
      }
    } catch (error) {
      console.error('Error registering user:', error);
    }
  };

  const handleLoginInputChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };
  
  const handleLoginUser = async (event) => {
    event.preventDefault();
  
    // Make a POST request to log in the user
    try {
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
      });
      console.log(response);
      if (response.ok) {
        const token = await response.text();
        localStorage.setItem('your-token-key', token);
        window.location.href = '/garden';
      } else {
        console.error('Failed to log in user');
      }
    } catch (error) {
      console.error('Error logging in user:', error);
    }
  };
  
  return (
    <div className="home-container">
      <h1>Welcome to the Garden!</h1>
      <p>Click the button to go to your own personal garden.</p>

      <button onClick={() => {
        setShowLoginForm(!showLoginForm);
        }} className="new-flower-button">
        {showLoginForm ? 'Cancel' : 'to the garden'}
      </button>

     {/* Modal for the register form */}
     {showLoginForm && (
        <div className={`modal ${showLoginForm ? 'active' : ''}`} onClick={() => setShowLoginForm(false)}>
          <form onSubmit={handleLoginUser} className="user-login-form" onClick={(e) => e.stopPropagation()}>
            <h2>Login</h2>
            <label>
              Username:
              <input type="text" name="username" value={user.username} onChange={handleLoginInputChange} required />
            </label>
            <label>
              Password:
              <input type="password" name="password" value={user.password} onChange={handleLoginInputChange} required />
            </label>
            <button type="submit">Login</button>
          </form>
        </div>
      )}

      <button onClick={() => {
        setShowRegisterForm(!showRegisterForm);
        }} className="new-flower-button">
        {showRegisterForm ? 'Cancel' : 'Register'}
      </button>

      {/* Modal for the register form */}
      {showRegisterForm && (
        <div className={`modal ${showRegisterForm ? 'active' : ''}`} onClick={() => setShowRegisterForm(false)}>
          <form onSubmit={handleRegisterUser} className="user-registration-form" onClick={(e) => e.stopPropagation()}>
            <h2>Register User</h2>
            <label>
              Username:
              <input type="text" name="username" value={user.username} onChange={handleInputChange} required />
            </label>
            <label>
              Password:
              <input type="password" name="password" value={user.password} onChange={handleInputChange} required />
            </label>
            <button type="submit">Register</button>
          </form>
        </div>
      )}
    </div>
  );
};


const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/garden" element={<RedirectedPage />} />
      </Routes>
      {/* Redirect /home to / */}
      {/* <Navigate from="/home" to="/" /> */}
    </Router>
  );
};

export default App; 

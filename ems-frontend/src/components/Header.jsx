import React from "react";
import { NavLink } from "react-router-dom";
import "./header.css";

const Header = () => {
  return (
    <header className="header">
      <nav className="nav-container">
        <NavLink
          to="/employees"
          className={({ isActive }) =>
            isActive ? "nav-btn active" : "nav-btn"
          }
        >
          Employees
        </NavLink>

        <NavLink
          to="/hods"
          className={({ isActive }) =>
            isActive ? "nav-btn active" : "nav-btn"
          }
        >
          HODs
        </NavLink>

        <NavLink
          to="/projects"
          className={({ isActive }) =>
            isActive ? "nav-btn active" : "nav-btn"
          }
        >
          Projects
        </NavLink>
      </nav>
    </header>
  );
};

export default Header;
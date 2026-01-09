import React from 'react';
import { NavLink } from 'react-router-dom';
import './header.css';

const Header = () => {
    return (
        <div className="header">
            <NavLink to = "/employees" className = "nav-btn">Employees</NavLink>
            <NavLink to = "/hods" className="nav-btn">HODs</NavLink>
            <NavLink to = "/projects" className="nav-btn">Projects</NavLink>
        </div>
    );
};

export default Header;
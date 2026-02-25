import React from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Header from "./components/Header";
import EmployeePage from "./components/EmployeePage";
import HodPage from "./components/HodPage";
import ProjectPage from "./components/ProjectPage";
import ErrorBoundary from "./components/ErrorBoundary";

function App() {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <Header />

        <Routes>
          <Route path="/" element={<Navigate to="/employees" />} />
          <Route path="/employees" element={<EmployeePage />} />
          <Route path="/hods" element={<HodPage />} />
          <Route path="/projects" element={<ProjectPage />} />
        </Routes>
      </BrowserRouter>
    </ErrorBoundary>
  );
}

export default App;

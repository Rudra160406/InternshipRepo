import React, { useState } from "react";
import ProjectForm from "./ProjectForm";
import ProjectList from "./ProjectList";
import "../styles/form.css";

const ProjectPage = () => {
  const [refresh, setRefresh] = useState(0);

  return (
    <div className="page-shell">
      <h2 className="page-title">Project Management</h2>
      <ProjectForm onSuccess={() => setRefresh((prev) => prev + 1)} />
      <ProjectList refresh={refresh} />
    </div>
  );
};

export default ProjectPage;

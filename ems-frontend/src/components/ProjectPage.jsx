import React, { useState } from "react";
import ProjectForm from "./ProjectForm";
import ProjectList from "./ProjectList";

const ProjectPage = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <>
      <ProjectForm onSuccess={() => setRefresh(!refresh)} />
      <ProjectList refresh={refresh} />
    </>
  );
};

export default ProjectPage;

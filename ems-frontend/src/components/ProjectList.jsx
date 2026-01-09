import React, { useEffect, useState } from "react";
import {
  fetchAllProjects,
  fetchProjectsByEmployee,
} from "../services/api";
import "../styles/table.css";

const ProjectList = ({ refresh }) => {
  const [projects, setProjects] = useState([]);
  const [employeeId, setEmployeeId] = useState("");

  const loadProjects = async () => {
    let res;
    if (employeeId) {
      res = await fetchProjectsByEmployee(employeeId);
    } else {
      res = await fetchAllProjects();
    }
    setProjects(res.data);
  };

  useEffect(() => {
    loadProjects();
  }, [refresh]);

  return (
    <div>
      <h3>Projects</h3>

      <input
        type="number"
        placeholder="Search by Employee ID"
        value={employeeId}
        onChange={(e) => setEmployeeId(e.target.value)}
      />
      <button onClick={loadProjects}>Search</button>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Project</th>
            <th>Status</th>
            <th>Employee</th>
            <th>Email</th>
          </tr>
        </thead>
        <tbody>
          {projects.map((p) => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.projectName}</td>
              <td>{p.status}</td>
              <td>{p.employeeName}</td>
              <td>{p.employeeEmail}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ProjectList;

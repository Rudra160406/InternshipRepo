import { useEffect, useMemo, useState } from "react";
import { fetchAllProjects } from "../services/api";
import "../styles/form.css";
import "../styles/table.css";

function ProjectList({ refresh }) {
  const [projects, setProjects] = useState([]);
  const [query, setQuery] = useState("");
  const [error, setError] = useState("");

  useEffect(() => {
    const loadProjects = async () => {
      try {
        const data = await fetchAllProjects();
        setProjects(data);
        setError("");
      } catch (e) {
        setProjects([]);
        setError(e.message || "Unable to fetch projects.");
      }
    };

    loadProjects();
  }, [refresh]);

  const filteredProjects = useMemo(() => {
    const key = query.trim().toLowerCase();
    if (!key) {
      return projects;
    }

    return projects.filter((project) => {
      const teamKeywords = Array.isArray(project.employeeDepartments)
        ? project.employeeDepartments.map((d) => d.departmentName).join(" ")
        : "";

      const haystack = [
        project.projectName,
        project.status,
        project.employeeName,
        project.employeeEmail,
        project.employeeAddress?.city,
        project.employeeAddress?.state,
        teamKeywords,
      ]
        .filter(Boolean)
        .join(" ")
        .toLowerCase();

      return haystack.includes(key);
    });
  }, [projects, query]);

  return (
    <div className="table-card">
      <h3>Projects</h3>

      <div className="search-bar">
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search by project, status, employee name/email, city, state, team"
        />
        <button className="btn-secondary" onClick={() => setQuery("")}>
          Clear
        </button>
      </div>

      {error && <div className="error-text">{error}</div>}
      {!error && filteredProjects.length === 0 && (
        <p style={{ color: "#64748b" }}>No projects found.</p>
      )}

      {!error && filteredProjects.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Project</th>
              <th>Status</th>
              <th>Employee</th>
              <th>Email</th>
              <th>City</th>
              <th>State</th>
              <th>Team</th>
            </tr>
          </thead>
          <tbody>
            {filteredProjects.map((project) => (
              <tr key={project.id}>
                <td>{project.projectName}</td>
                <td>{project.status}</td>
                <td>{project.employeeName}</td>
                <td>{project.employeeEmail}</td>
                <td>{project.employeeAddress?.city || "-"}</td>
                <td>{project.employeeAddress?.state || "-"}</td>
                <td>
                  {Array.isArray(project.employeeDepartments) &&
                  project.employeeDepartments.length > 0
                    ? project.employeeDepartments
                        .map((d) => d.departmentName)
                        .join(", ")
                    : "-"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default ProjectList;

import { useEffect, useState } from "react";
import { createProject, fetchEmployees } from "../services/api";
import "../styles/form.css";

const initialForm = {
  projectName: "",
  status: "PLANNED",
  employeeId: "",
};

function ProjectForm({ onSuccess }) {
  const [form, setForm] = useState(initialForm);
  const [employees, setEmployees] = useState([]);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    const loadEmployees = async () => {
      try {
        setEmployees(await fetchEmployees());
      } catch (error) {
        setErrors({ general: error.message || "Unable to load employees." });
      }
    };

    loadEmployees();
  }, []);

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createProject({
        projectName: form.projectName,
        status: form.status,
        employeeId: Number(form.employeeId),
      });

      setErrors({});
      setForm(initialForm);
      onSuccess?.();
    } catch (error) {
      setErrors(
        error?.details && Object.keys(error.details).length > 0
          ? error.details
          : { general: error.message || "Unable to create project." }
      );
    }
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <h3>Add Project</h3>
      {errors.general && <div className="error-text">{errors.general}</div>}

      <input
        name="projectName"
        placeholder="Project Name"
        value={form.projectName}
        onChange={handleChange}
      />
      {errors.projectName && (
        <div className="field-error">{errors.projectName}</div>
      )}

      <select name="status" value={form.status} onChange={handleChange}>
        <option value="PLANNED">Planned</option>
        <option value="IN_PROGRESS">In Progress</option>
        <option value="COMPLETED">Completed</option>
        <option value="ON_HOLD">On Hold</option>
      </select>
      {errors.status && <div className="field-error">{errors.status}</div>}

      <select
        name="employeeId"
        value={form.employeeId}
        onChange={handleChange}
      >
        <option value="">Select Employee</option>
        {employees.map((emp) => (
          <option key={emp.id} value={emp.id}>
            {emp.name} ({emp.email})
          </option>
        ))}
      </select>
      {errors.employeeId && (
        <div className="field-error">{errors.employeeId}</div>
      )}

      <button type="submit">Add Project</button>
    </form>
  );
}

export default ProjectForm;

import React, { useState } from "react";
import { createProject } from "../services/api";
import "../styles/form.css";

const ProjectForm = ({ onSuccess }) => {
  const [form, setForm] = useState({
    projectName: "",
    status: "",
    employeeId: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createProject({
        projectName: form.projectName,
        status: form.status,
        employeeId: Number(form.employeeId),
      });
      alert("Project added successfully");
      onSuccess();
      setForm({ projectName: "", status: "", employeeId: "" });
    } catch (err) {
      alert(err.response?.data?.message || "Error creating project");
    }
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <h3>Add Project</h3>

      <input
        name="projectName"
        placeholder="Project Name"
        value={form.projectName}
        onChange={handleChange}
        required
      />

      <input
        name="status"
        placeholder="Status"
        value={form.status}
        onChange={handleChange}
        required
      />

      <input
        name="employeeId"
        type="number"
        placeholder="Employee ID"
        value={form.employeeId}
        onChange={handleChange}
        required
      />

      <button type="submit">Add Project</button>
    </form>
  );
};

export default ProjectForm;

import React, { useState } from "react";
import { createHod } from "../services/api";
import "../styles/form.css";

const HodForm = ({ onSuccess }) => {
  const [form, setForm] = useState({
    name: "",
    email: "",
    salary: "",
    departmentId: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createHod({
        name: form.name,
        email: form.email,
        salary: Number(form.salary),
        departmentId: Number(form.departmentId),
      });
      alert("HOD added successfully");
      onSuccess();
      setForm({ name: "", email: "", salary: "", departmentId: "" });
    } catch (err) {
      alert(err.response?.data?.message || "Error creating HOD");
    }
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <h3>Add HOD</h3>

      <input name="name" placeholder="Name" value={form.name} onChange={handleChange} required />
      <input name="email" placeholder="Email" value={form.email} onChange={handleChange} required />
      <input name="salary" type="number" placeholder="Salary" value={form.salary} onChange={handleChange} required />
      <input name="departmentId" type="number" placeholder="Department ID" value={form.departmentId} onChange={handleChange} required />

      <button type="submit">Add HOD</button>
    </form>
  );
};

export default HodForm;

import { useEffect, useState } from "react";
import { createHod, fetchDepartments } from "../services/api";
import "../styles/form.css";

const initialForm = {
  name: "",
  email: "",
  salary: "",
  departmentId: "",
  address: {
    city: "",
    state: "",
    pincode: "",
  },
};

function HodForm({ onSuccess }) {
  const [form, setForm] = useState(initialForm);
  const [departments, setDepartments] = useState([]);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    const loadDepartments = async () => {
      try {
        setDepartments(await fetchDepartments());
      } catch (error) {
        setErrors({ general: error.message || "Unable to load departments." });
      }
    };

    loadDepartments();
  }, []);

  const handleChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
  };

  const handleAddressChange = (e) => {
    setForm((prev) => ({
      ...prev,
      address: {
        ...prev.address,
        [e.target.name]: e.target.value,
      },
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await createHod({
        name: form.name,
        email: form.email,
        salary: Number(form.salary),
        departmentId: Number(form.departmentId),
        address: form.address,
      });

      setErrors({});
      setForm(initialForm);
      onSuccess?.();
    } catch (error) {
      setErrors(
        error?.details && Object.keys(error.details).length > 0
          ? error.details
          : { general: error.message || "Unable to create HOD." }
      );
    }
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <h3>Add HOD</h3>
      {errors.general && <div className="error-text">{errors.general}</div>}

      <input
        name="name"
        placeholder="Name"
        value={form.name}
        onChange={handleChange}
      />
      {errors.name && <div className="field-error">{errors.name}</div>}

      <input
        name="email"
        placeholder="Email"
        value={form.email}
        onChange={handleChange}
      />
      {errors.email && <div className="field-error">{errors.email}</div>}

      <input
        name="salary"
        type="number"
        placeholder="Salary"
        value={form.salary}
        onChange={handleChange}
      />
      {errors.salary && <div className="field-error">{errors.salary}</div>}

      <select
        name="departmentId"
        value={form.departmentId}
        onChange={handleChange}
      >
        <option value="">Select Department</option>
        {departments.map((dep) => (
          <option key={dep.id} value={dep.id}>
            {dep.departmentName}
          </option>
        ))}
      </select>
      {errors.departmentId && (
        <div className="field-error">{errors.departmentId}</div>
      )}

      <h4>Address</h4>
      <input
        name="city"
        placeholder="City"
        value={form.address.city}
        onChange={handleAddressChange}
      />
      {errors["address.city"] && (
        <div className="field-error">{errors["address.city"]}</div>
      )}

      <input
        name="state"
        placeholder="State"
        value={form.address.state}
        onChange={handleAddressChange}
      />
      {errors["address.state"] && (
        <div className="field-error">{errors["address.state"]}</div>
      )}

      <input
        name="pincode"
        placeholder="Pincode"
        value={form.address.pincode}
        onChange={handleAddressChange}
      />
      {errors["address.pincode"] && (
        <div className="field-error">{errors["address.pincode"]}</div>
      )}

      <button type="submit">Add HOD</button>
    </form>
  );
}

export default HodForm;

import { useEffect, useState } from "react";
import { createEmployee, fetchDepartments } from "../services/api";
import "../styles/form.css";

function EmployeeForm({ onSuccess }) {
  const [employee, setEmployee] = useState({
    name: "",
    email: "",
    salary: "",
    address: {
      city: "",
      state: "",
      pincode: "",
    },
    departmentIds: [],
  });
  const [departments, setDepartments] = useState([]);
  const [loadingDeps, setLoadingDeps] = useState(true);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    const loadDepartments = async () => {
      try {
        const data = await fetchDepartments();
        setDepartments(Array.isArray(data) ? data : []);
      } catch (e) {
        setErrors((prev) => ({
          ...prev,
          general: e.message || "Unable to fetch departments.",
        }));
      } finally {
        setLoadingDeps(false);
      }
    };

    loadDepartments();
  }, []);

  const handleChange = (e) => {
    setEmployee((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleAddressChange = (e) => {
    setEmployee((prev) => ({
      ...prev,
      address: {
        ...prev.address,
        [e.target.name]: e.target.value,
      },
    }));
  };

  const handleDepartmentChange = (id) => {
    setEmployee((prev) => {
      const exists = prev.departmentIds.includes(id);
      return {
        ...prev,
        departmentIds: exists
          ? prev.departmentIds.filter((d) => d !== id)
          : [...prev.departmentIds, id],
      };
    });
  };

  const resetForm = () => {
    setEmployee({
      name: "",
      email: "",
      salary: "",
      address: {
        city: "",
        state: "",
        pincode: "",
      },
      departmentIds: [],
    });
  };

  const submitForm = async () => {
    try {
      const payload = {
        ...employee,
        salary: employee.salary ? Number(employee.salary) : null,
      };

      await createEmployee(payload);
      setErrors({});
      resetForm();
      onSuccess?.();
    } catch (err) {
      const details = err?.details;
      setErrors(
        details && typeof details === "object" && !Array.isArray(details)
          ? details
          : { general: err?.message || "Something went wrong" }
      );
    }
  };

  return (
    <div className="card">
      <h2>Add Employee</h2>

      {errors.general && <div className="error-text">{errors.general}</div>}

      <input
        name="name"
        placeholder="Name"
        value={employee.name}
        onChange={handleChange}
      />
      {errors.name && <div className="field-error">{errors.name}</div>}

      <input
        name="email"
        placeholder="Email"
        value={employee.email}
        onChange={handleChange}
      />
      {errors.email && <div className="field-error">{errors.email}</div>}

      <input
        name="salary"
        placeholder="Salary"
        type="number"
        value={employee.salary}
        onChange={handleChange}
      />
      {errors.salary && <div className="field-error">{errors.salary}</div>}

      <h3>Address</h3>

      <input
        name="city"
        placeholder="City"
        value={employee.address.city}
        onChange={handleAddressChange}
      />
      {errors["address.city"] && (
        <div className="field-error">{errors["address.city"]}</div>
      )}

      <input
        name="state"
        placeholder="State"
        value={employee.address.state}
        onChange={handleAddressChange}
      />
      {errors["address.state"] && (
        <div className="field-error">{errors["address.state"]}</div>
      )}

      <input
        name="pincode"
        placeholder="Pincode"
        value={employee.address.pincode}
        onChange={handleAddressChange}
      />
      {errors["address.pincode"] && (
        <div className="field-error">{errors["address.pincode"]}</div>
      )}

      <h3>Departments</h3>

      <div className="checkbox-grid">
        {loadingDeps ? (
          <p style={{ color: "#64748b" }}>Loading departments...</p>
        ) : departments.length > 0 ? (
          departments.map((dep) => (
            <label key={dep.id} className="checkbox-item">
              <input
                type="checkbox"
                checked={employee.departmentIds.includes(dep.id)}
                onChange={() => handleDepartmentChange(dep.id)}
              />
              {dep.departmentName}
            </label>
          ))
        ) : (
          <p style={{ color: "#64748b" }}>No departments available.</p>
        )}
      </div>

      {errors.departmentIds && (
        <div className="field-error">{errors.departmentIds}</div>
      )}

      <button onClick={submitForm}>Save Employee</button>
    </div>
  );
}

export default EmployeeForm;

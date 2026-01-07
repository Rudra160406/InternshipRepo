import { useEffect, useState } from "react";
import { createEmployee, fetchDepartments } from "../services/api";
import "../styles/form.css";

function EmployeeForm() {
  const [employee, setEmployee] = useState({
    name: "",
    email: "",
    salary: "",
    address: {
      city: "",
      state: "",
      pincode: ""
    },
    departmentIds: []
  });

  const [departments, setDepartments] = useState([]);
  const [loadingDeps, setLoadingDeps] = useState(true);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchDepartments();
        setDepartments(Array.isArray(data) ? data : []);
      } catch (e) {
        setErrors(prev => ({ ...prev, general: e.message || "Unable to fetch departments." }));
      } finally {
        setLoadingDeps(false);
      }
    };

    load();
  }, []);

  const handleChange = (e) => {
    setEmployee({ ...employee, [e.target.name]: e.target.value });
  };

  const handleAddressChange = (e) => {
    setEmployee({
      ...employee,
      address: { ...employee.address, [e.target.name]: e.target.value }
    });
  };

  const handleDepartmentChange = (id) => {
    setEmployee((prev) => {
      const exists = prev.departmentIds.includes(id);
      return {
        ...prev,
        departmentIds: exists
          ? prev.departmentIds.filter(d => d !== id)
          : [...prev.departmentIds, id]
      };
    });
  };

  const submitForm = async () => {
    try {
      await createEmployee(employee);
      alert("Employee created successfully");
      setErrors({});
    } catch (err) {
      // Use backend validation errors when available, otherwise fall back to a general message
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

      <input name="name" placeholder="Name" onChange={handleChange} />
      <span>{errors.name}</span>

      <input name="email" placeholder="Email" onChange={handleChange} />
      <span>{errors.email}</span>

      <input name="salary" placeholder="Salary" onChange={handleChange} />
      <span>{errors.salary}</span>

      <h3>Address</h3>

      <input name="city" placeholder="City" onChange={handleAddressChange} />
      <span>{errors["address.city"]}</span>

      <input name="state" placeholder="State" onChange={handleAddressChange} />
      <span>{errors["address.state"]}</span>

      <input name="pincode" placeholder="Pincode" onChange={handleAddressChange} />
      <span>{errors["address.pincode"]}</span>

      <h3>Departments (At least one required)</h3>
      <div style={{ display: "flex", gap: "10px", flexWrap: "wrap", marginBottom: "10px" }}>
        {loadingDeps ? (
          <p style={{ color: "#999" }}>Loading departments...</p>
        ) : departments.length > 0 ? (
          departments.map(dep => (
            <label key={dep.id} style={{ display: "flex", alignItems: "center", gap: "5px" }}>
              <input
                type="checkbox"
                checked={employee.departmentIds.includes(dep.id)}
                onChange={() => handleDepartmentChange(dep.id)}
              />
              {dep.departmentName}
            </label>
          ))
        ) : (
          <p style={{ color: "#999" }}>No departments found. Please add departments in the database.</p>
        )}
      </div>
      {errors.departmentIds && <span style={{ color: "red" }}>{errors.departmentIds}</span>}

      <button onClick={submitForm}>Save Employee</button>
    </div>
  );
}

export default EmployeeForm;

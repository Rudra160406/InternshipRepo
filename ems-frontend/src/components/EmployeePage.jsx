import { useEffect, useMemo, useState } from "react";
import EmployeeForm from "./EmployeeForm";
import EmployeeList from "./EmployeeList";
import { fetchEmployees } from "../services/api";
import "../styles/form.css";

function EmployeePage() {
  const [employees, setEmployees] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [query, setQuery] = useState("");

  const loadEmployees = async () => {
    setLoading(true);
    try {
      const data = await fetchEmployees();
      setEmployees(data);
      setError("");
    } catch (e) {
      setEmployees([]);
      setError(e.message || "Unable to load employees.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadEmployees();
  }, []);

  const filteredEmployees = useMemo(() => {
    const key = query.trim().toLowerCase();
    if (!key) {
      return employees;
    }

    return employees.filter((emp) => {
      const departments = Array.isArray(emp.departments)
        ? emp.departments.map((d) => d.departmentName).join(" ")
        : "";
      const haystack = [
        emp.name,
        emp.email,
        emp.salary,
        emp.address?.city,
        emp.address?.state,
        emp.address?.pincode,
        departments,
      ]
        .filter(Boolean)
        .join(" ")
        .toLowerCase();

      return haystack.includes(key);
    });
  }, [employees, query]);

  return (
    <div className="page-shell">
      <h2 className="page-title">Employee Management</h2>

      <EmployeeForm onSuccess={loadEmployees} />

      <div className="search-bar">
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search employees by name, email, salary, city, state, pincode, department"
        />
        <button className="btn-secondary" onClick={() => setQuery("")}>
          Clear
        </button>
      </div>

      <EmployeeList
        employees={filteredEmployees}
        loading={loading}
        error={error}
        onRefresh={loadEmployees}
      />
    </div>
  );
}

export default EmployeePage;

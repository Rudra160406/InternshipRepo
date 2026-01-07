import { useEffect, useState } from "react";
import { fetchEmployees } from "../services/api";
import "../styles/table.css";

function EmployeeList() {
  const [employees, setEmployees] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        const data = await fetchEmployees();
        setEmployees(Array.isArray(data) ? data : []);
        setError("");
      } catch (e) {
        setEmployees([]);
        setError(e?.message || "Unable to fetch employees.");
      }
    };

    load();
  }, []);

  return (
    <div className="table-card">
      <h2>Employee List</h2>

      {error && <div className="error-text">{error}</div>}
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Salary</th>
            <th>Departments</th>
          </tr>
        </thead>
        <tbody>
          {employees.map((emp, index) => {
            const deps = Array.isArray(emp?.departments) ? emp.departments : [];
            return (
              <tr key={emp.id ?? `row-${index}`}>
                <td>{emp.name}</td>
                <td>{emp.email}</td>
                <td>{emp.salary}</td>
                <td>{deps.map(d => d.departmentName).join(", ")}</td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
}

export default EmployeeList;

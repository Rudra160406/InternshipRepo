import { deleteEmployee } from "../services/api";
import "../styles/table.css";

function EmployeeList({ employees, loading, error, onRefresh }) {
  const handleDelete = async (id) => {
    const confirmed = window.confirm("Delete this employee?");
    if (!confirmed) {
      return;
    }

    try {
      await deleteEmployee(id);
      onRefresh?.();
    } catch (e) {
      window.alert(e.message || "Unable to delete employee.");
    }
  };

  return (
    <div className="table-card">
      <h2>Employee List</h2>

      {loading && <p style={{ color: "#999" }}>Loading employees...</p>}
      {!loading && error && <div className="error-text">{error}</div>}

      {!loading && !error && employees.length === 0 && (
        <p style={{ color: "#999" }}>No employees found.</p>
      )}

      {!loading && !error && employees.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Salary</th>
              <th>City</th>
              <th>Departments</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {employees.map((emp, index) => {
              const deps = Array.isArray(emp?.departments) ? emp.departments : [];

              return (
                <tr key={emp?.id ?? `row-${index}`}>
                  <td>{emp?.name ?? "-"}</td>
                  <td>{emp?.email ?? "-"}</td>
                  <td>{emp?.salary ?? "-"}</td>
                  <td>{emp?.address?.city ?? "-"}</td>
                  <td>
                    {deps.length > 0
                      ? deps.map((d) => d.departmentName).join(", ")
                      : "-"}
                  </td>
                  <td>
                    <button
                      className="btn-danger btn-small"
                      onClick={() => handleDelete(emp.id)}
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default EmployeeList;

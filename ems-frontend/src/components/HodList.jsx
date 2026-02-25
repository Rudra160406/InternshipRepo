import { useEffect, useMemo, useState } from "react";
import { fetchHods } from "../services/api";
import "../styles/form.css";
import "../styles/table.css";

function HodList({ refresh }) {
  const [hods, setHods] = useState([]);
  const [error, setError] = useState("");
  const [query, setQuery] = useState("");

  useEffect(() => {
    const loadHods = async () => {
      try {
        setHods(await fetchHods());
        setError("");
      } catch (e) {
        setHods([]);
        setError(e.message || "Unable to fetch HODs.");
      }
    };

    loadHods();
  }, [refresh]);

  const filteredHods = useMemo(() => {
    const key = query.trim().toLowerCase();
    if (!key) {
      return hods;
    }

    return hods.filter((hod) => {
      const department = Array.isArray(hod.departments)
        ? hod.departments.map((d) => d.departmentName).join(" ")
        : "";

      const haystack = [
        hod.name,
        hod.email,
        hod.salary,
        hod.address?.city,
        hod.address?.state,
        department,
      ]
        .filter(Boolean)
        .join(" ")
        .toLowerCase();

      return haystack.includes(key);
    });
  }, [hods, query]);

  return (
    <div className="table-card">
      <h3>HOD List</h3>

      <div className="search-bar">
        <input
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Search HOD by name, email, salary, city, state, department"
        />
        <button className="btn-secondary" onClick={() => setQuery("")}>
          Clear
        </button>
      </div>

      {error && <div className="error-text">{error}</div>}

      {!error && filteredHods.length === 0 && (
        <p style={{ color: "#64748b" }}>No HODs found.</p>
      )}

      {!error && filteredHods.length > 0 && (
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Salary</th>
              <th>Address</th>
              <th>Department</th>
            </tr>
          </thead>
          <tbody>
            {filteredHods.map((hod) => (
              <tr key={hod.id}>
                <td>{hod.name}</td>
                <td>{hod.email}</td>
                <td>{hod.salary}</td>
                <td>
                  {[hod.address?.city, hod.address?.state].filter(Boolean).join(", ") || "-"}
                </td>
                <td>
                  {Array.isArray(hod.departments) && hod.departments.length > 0
                    ? hod.departments.map((d) => d.departmentName).join(", ")
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

export default HodList;

import React, { useEffect, useState } from "react";
import { fetchHods } from "../services/api";
import "../styles/table.css";

const HodList = () => {
  const [hods, setHods] = useState([]);

  const loadHods = async () => {
    const res = await fetchHods();
    setHods(res.data);
  };

  useEffect(() => {
    loadHods();
  }, []);

  return (
    <div>
      <h3>HOD List</h3>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Salary</th>
          </tr>
        </thead>
        <tbody>
          {hods.map((hod) => (
            <tr key={hod.id}>
              <td>{hod.id}</td>
              <td>{hod.name}</td>
              <td>{hod.email}</td>
              <td>{hod.salary}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default HodList;

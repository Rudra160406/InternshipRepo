import React, { useState } from "react";
import HodForm from "./HodForm";
import HodList from "./HodList";
import "../styles/form.css";

const HodPage = () => {
  const [refresh, setRefresh] = useState(0);

  return (
    <div className="page-shell">
      <h2 className="page-title">HOD Management</h2>
      <HodForm onSuccess={() => setRefresh((prev) => prev + 1)} />
      <HodList refresh={refresh} />
    </div>
  );
};

export default HodPage;

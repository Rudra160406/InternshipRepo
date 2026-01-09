import React, { useState } from "react";
import HodForm from "./HodForm";
import HodList from "./HodList";

const HodPage = () => {
  const [refresh, setRefresh] = useState(false);

  return (
    <>
      <HodForm onSuccess={() => setRefresh(!refresh)} />
      <HodList key={refresh} />
    </>
  );
};

export default HodPage;

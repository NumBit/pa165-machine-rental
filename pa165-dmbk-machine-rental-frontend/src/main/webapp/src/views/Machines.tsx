import React, { useContext } from "react";
import { GlobalContext, isAdmin } from "../context/GlobalState";

const Machines = () => {
  const { user } = useContext(GlobalContext);
  const customersView = <p>customers view</p>;
  const adminsView = <p>admins view</p>;
  return (
    <div>
      <h1>Machines</h1>
      {isAdmin(user) ? adminsView : customersView}
    </div>
  );
};

export default Machines;

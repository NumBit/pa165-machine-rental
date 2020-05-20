import React, { useContext } from "react";
import { GlobalContext, isAdmin } from "../context/GlobalState";

const Rentals = () => {
  const { user } = useContext(GlobalContext);
  const customersView = <p>customers view</p>;
  const adminsView = <p>admins view</p>;
  return (
    <div>
      <h1>Rentals</h1>
      {isAdmin(user) ? adminsView : customersView}
    </div>
  );
};

export default Rentals;

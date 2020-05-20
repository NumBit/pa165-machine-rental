import React, { useContext } from "react";
import { GlobalContext, isAdmin } from "../context/GlobalState";
import RentalsList from "../components/RentalsList";
import AdminRentalList from "../components/AdminRentalList";
import MachineAvaibility from "../components/MachineAvaibility";

const Rentals = () => {
  const { user } = useContext(GlobalContext);
  const customersView = <RentalsList/>;
  const adminsView = <AdminRentalList/>;

  return (
    <div>
      <h1>Rentals</h1>
      {isAdmin(user) ? adminsView : customersView}
      <MachineAvaibility/>
    </div>
  );
};

export default Rentals;

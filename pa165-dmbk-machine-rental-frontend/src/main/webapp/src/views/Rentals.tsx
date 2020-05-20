import React, { useContext } from "react";
import { GlobalContext } from "../context/GlobalState";
import RentalsList from "../components/RentalsList";
import MachineAvaibility from "../components/MachineAvaibility";
import AdminRentalList from "../components/AdminRentalList";

const Rentals = () => {
  const { user } = useContext(GlobalContext);
  const customersView = <p>customers view</p>;
  const adminsView = <p>admins view</p>;

  return (
    <div>
      <h1>Rentals</h1>
      {user.isAdmin ? <AdminRentalList/> :<RentalsList/> }
      <MachineAvaibility/>
    </div>
  );
};

export default Rentals;

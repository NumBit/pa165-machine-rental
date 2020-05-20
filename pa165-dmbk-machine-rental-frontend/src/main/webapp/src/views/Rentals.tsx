import React, { useContext } from "react";
import { GlobalContext } from "../context/GlobalState";
import RentalsList from "../components/RentalsList";
import NewRental from "../components/NewRental";

const Rentals = () => {
  const { user } = useContext(GlobalContext);
  const customersView = <p>customers view</p>;
  const adminsView = <p>admins view</p>;

  return (
    <div>
      <h1>Rentals</h1>
      {user.isAdmin ? adminsView :<RentalsList/> }
      <NewRental/>
    </div>
  );
};

export default Rentals;

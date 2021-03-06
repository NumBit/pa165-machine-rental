import React, { useContext } from "react";
import { GlobalContext, isAdmin } from "../context/GlobalState";
import RentalsList from "../components/RentalsList";
import AdminRentalList from "../components/AdminRentalList";
import {Link} from "react-router-dom";
import Button from "@material-ui/core/Button";

const Rentals = () => {
  const { user } = useContext(GlobalContext);
    const customersView = <div><RentalsList/></div>;
    const adminsView = <div><AdminRentalList/></div>;

  return (
    <div style={{width: "100%"}}>
      <h1>Rentals</h1>
        <Link to="/createRental" style={{textDecoration: "none"}}><Button variant="contained" color="primary" style={{marginBottom:10, float:"left" }}>Create rental </Button></Link>
        <Link to="/checkMachineAvailability" style={{textDecoration: "none"}}><Button variant="contained" color="primary" style={{marginBottom: 10, float:"right"}}>Check availability</Button></Link>

        {isAdmin(user) ? adminsView : customersView}

    </div>
  );
};

export default Rentals;

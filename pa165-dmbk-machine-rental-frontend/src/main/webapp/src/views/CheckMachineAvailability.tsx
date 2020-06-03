import React, { useContext } from "react";
import { GlobalContext, isAdmin } from "../context/GlobalState";
import {MachineAvailability} from "../components/MachineAvailability";


const CheckMachineAvailability = () => {
    const { user } = useContext(GlobalContext);

    return (
        <div>
            <MachineAvailability/>
        </div>
    );
};

export default CheckMachineAvailability;

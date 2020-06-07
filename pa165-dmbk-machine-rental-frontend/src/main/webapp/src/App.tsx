import React, { useContext } from "react";
import {Switch, Route, HashRouter} from "react-router-dom";
import Login from "./views/Login";
import PrivateRoute from "./components/PrivateRoute";
import Machines from "./views/Machines";
import Rentals from "./views/Rentals";
import Profile from "./views/Profile";
import NotFound from "./views/NotFound";
import Customers from "./views/Customers";
import Revisions from "./views/Revisions";
import SideNavigation from "./components/SideNavigation";
import ApplicationBar from "./components/ApplicationBar";

import { GlobalProvider, GlobalContext } from "./context/GlobalState";
import CreateRental from "./views/CreateRental";
import CheckMachineAvailability from "./views/CheckMachineAvailability";
import {UpdateRental} from "./components/UpdateRental";

const App: React.FC = () => {
  const { signedIn } = useContext(GlobalContext);
  return (
    <GlobalProvider>
      {signedIn ? (
        <HashRouter basename="">
          <ApplicationBar />
          <SideNavigation />
          <main>
            <Switch>
              <Route exact path="/" component={Login} />
              <Route path="/machines" component={Machines} />
              <Route path="/rentals" component={Rentals} />
              <Route path="/profile" component={Profile} />
              <Route path="/createRental/:machineId?" component={CreateRental} />
              <Route path="/updateRental" component={UpdateRental} />
              <Route path="/checkMachineAvailability" component={CheckMachineAvailability} />
              <PrivateRoute path="/revisions" component={Revisions} />
              <PrivateRoute path="/customers" component={Customers} />
              <Route component={NotFound} />
            </Switch>
          </main>
        </HashRouter>
      ) : (
        <Login />
      )}
    </GlobalProvider>
  );
};

export default App;

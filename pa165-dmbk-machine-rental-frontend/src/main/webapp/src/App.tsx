import React, { useContext } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
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

const App: React.FC = () => {
  const { signedIn } = useContext(GlobalContext);
  return (
    <GlobalProvider>
      {signedIn ? (
        <Router basename="pa165">
          <ApplicationBar />
          <SideNavigation />
          <main>
            <Switch>
              <Route exact path="/" component={Login} />
              <Route path="/machines" component={Machines} />
              <Route path="/rentals" component={Rentals} />
              <Route path="/profile" component={Profile} />
              <PrivateRoute path="/revisions" component={Revisions} />
              <PrivateRoute path="/customers" component={Customers} />
              <Route component={NotFound} />
            </Switch>
          </main>
        </Router>
      ) : (
        <Login />
      )}
    </GlobalProvider>
  );
};

export default App;

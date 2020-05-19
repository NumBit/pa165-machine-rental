import React, { useContext } from "react";
import { Route, Redirect } from "react-router-dom";
import { GlobalContext } from "../context/GlobalState";

const PrivateRoute = ({ component, ...rest }: any) => {
  const { user } = useContext(GlobalContext);
  const routeComponent = (props: any) =>
    user.isAdmin ? (
      React.createElement(component, props)
    ) : (
      <Redirect to={{ pathname: "/" }} />
    );
  return <Route {...rest} render={routeComponent} />;
};

export default PrivateRoute;

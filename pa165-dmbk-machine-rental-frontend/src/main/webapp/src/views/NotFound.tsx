import React from "react";
import Button from "@material-ui/core/Button";
import { useHistory } from "react-router-dom";

const NotFound = () => {
  const history = useHistory();

  function handleClick() {
    history.push("/");
  }

  return (
    <div>
      <h1>Page not found</h1>
      <Button variant="contained" color="primary" onClick={handleClick}>
        Home page
      </Button>
    </div>
  );
};

export default NotFound;

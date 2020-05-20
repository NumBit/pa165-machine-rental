import React, { useContext } from "react";
import { Link } from 'react-router-dom';
import { GlobalContext, isAdmin, isUnauthenticated } from "../context/GlobalState";
import { makeStyles } from "@material-ui/core/styles";
import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles(() => ({
  title: {
    flexGrow: 1,
  },
  button: {
    marginRight: 20,
    backgroundColor: '#40c4ff',
  }
}));

const ApplicationBar = () => {
  const classes = useStyles();
  const { user } = useContext(GlobalContext);
  return (
    <AppBar position="fixed">
      <Toolbar>
        <Typography variant="h6" className={classes.title}>
          DMBK machine rental
        </Typography>
        <Button className={classes.button} variant="contained" component={Link} to='/'>Login</Button>
        <Typography variant="body1">
          {isUnauthenticated(user)
              ? "Not signed in"
              : isAdmin(user) ? "Signed in as Admin" : "Signed in as Customer"
          }
        </Typography>
      </Toolbar>
    </AppBar>
  );
};

export default ApplicationBar;

import React, { useContext } from "react";
import { Link } from 'react-router-dom';
import { GlobalContext } from "../context/GlobalState";
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
  const { user, switchRole } = useContext(GlobalContext);
  return (
    <AppBar position="fixed">
      <Toolbar>
        <Typography variant="h6" className={classes.title}>
          DMBK machine rental
        </Typography>
        <Button className={classes.button} variant="contained" component={Link} to='/login'>Login</Button>
        <Typography variant="body1">
          Signed in as {user.isAdmin ? "Admin" : "Customer"}
        </Typography>
        <Button color="inherit" onClick={() => switchRole(!user.isAdmin)}>
          Switch
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default ApplicationBar;

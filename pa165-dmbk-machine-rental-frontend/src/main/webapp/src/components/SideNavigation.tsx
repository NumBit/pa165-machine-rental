import React, { useContext } from 'react';
import {GlobalContext, isAdmin, isUnauthenticated} from "../context/GlobalState";
import clsx from "clsx";
import { makeStyles } from "@material-ui/core/styles";
import { NavLink as RouterNavLink } from "react-router-dom";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";
import Button from "@material-ui/core/Button";
import { Link } from 'react-router-dom';
import Toolbar from "@material-ui/core/Toolbar";

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
  drawer: {
    width: drawerWidth,
    height: "100%",
    position: "fixed",
    top: "64px",
    left: 0,
    borderRight: "3px solid #eee",
    padding: "1vh 1vw",
  },
  link: {
    textDecoration: "none",
    color: theme.palette.text.primary,
    "&:hover": {
      textDecoration: "none",
    },
  },
  button: {
  },
}));

export interface State {
  redirectToReferrer: boolean;
}

const SideNavigation = () => {
  const classes = useStyles();
  const { user, switchRole } = useContext(GlobalContext);

  function handleLogout() {
      fetch("http://localhost:8080/pa165/rest/user/logout")
      .then(response => switchRole({ objectName: "Unauthenticated" }))
  }

  const customerNavigation = (
    <>
      <RouterNavLink to="/machines" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Rent Machine" />
        </ListItem>
      </RouterNavLink>
      <RouterNavLink to="/rentals" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="My rentals" />
        </ListItem>
      </RouterNavLink>
      <RouterNavLink to="/profile" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Profile" />
        </ListItem>
      </RouterNavLink>
    </>
  );

  const adminNavigation = (
    <>
      <RouterNavLink to="/rentals" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Rental records" />
        </ListItem>
      </RouterNavLink>
      <RouterNavLink to="/revisions" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Machine revisions" />
        </ListItem>
      </RouterNavLink>
      <RouterNavLink to="/customers" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Customers" />
        </ListItem>
      </RouterNavLink>
      <RouterNavLink to="/machines" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Machines" />
        </ListItem>
      </RouterNavLink>
      <RouterNavLink to="/profile" className={clsx(classes.link)}>
        <ListItem button>
          <ListItemText primary="Profile" />
        </ListItem>
      </RouterNavLink>
    </>
  );

  const logoutChoice = (
    <>
    <Divider/>
    <ListItem button>
        <Button className={classes.button} variant="contained"
                component={Link} to='/' onClick={() => handleLogout()}>Logout</Button>
    </ListItem>
    </>
  );

  return (
    <div className={classes.drawer}>
        {isAdmin(user) ? adminNavigation : customerNavigation}
        {isUnauthenticated(user) ? "" : logoutChoice}
    </div>
  );
};

export default SideNavigation;

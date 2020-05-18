import React, { useContext } from "react";
import { GlobalContext } from "../context/GlobalState";
import clsx from "clsx";
import { makeStyles } from "@material-ui/core/styles";
import { NavLink as RouterNavLink } from "react-router-dom";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import Divider from "@material-ui/core/Divider";

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
}));

const SideNavigation = () => {
  const classes = useStyles();
  const { user } = useContext(GlobalContext);

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
    </>
  );

  return (
    <div className={classes.drawer}>
      {user.isAdmin ? adminNavigation : customerNavigation}
      <Divider />
      <RouterNavLink
        to="/"
        className={clsx(classes.link)}
        activeClassName="selected"
      >
        <ListItem button>
          <ListItemText primary="Logout" />
        </ListItem>
      </RouterNavLink>
    </div>
  );
};

export default SideNavigation;

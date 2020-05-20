import React, { useState, useEffect } from "react";
import { makeStyles } from "@material-ui/core/styles";
import axios from "axios";
import Divider from "@material-ui/core/Divider";
import Typography from "@material-ui/core/Typography";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import RevisionsTable from "../components/RevisionsTable";
import CreateRevisionForm from "../components/CreateRevisionForm";
import FilterRevisionForm from "../components/FilterRevisionForm";
import CircularProgress from "@material-ui/core/CircularProgress";

const useStyles = makeStyles(() => ({
  root: {
    width: "100%",
    textAlign: "center",
  },
}));

const api = axios.create({
  baseURL: `http://localhost:8080/pa165/rest/revisions/`,
});

const Revisions = () => {
  const classes = useStyles();
  const [revisions, setRevisions] = useState([]);
  const [initialized, setInitialized] = useState(false);
  const [machines, setMachines] = useState([]);

  useEffect(() => {
    setInitialized(false);
    axios
      .all([
        api.get(`all`),
        axios.get(`http://localhost:8080/pa165/rest/machine/`),
      ])
      .then(
        axios.spread((...responses) => {
          setRevisions(responses[0].data);
          setMachines(responses[1].data);
          setInitialized(true);
        })
      )
      .catch((errors) => {
        setInitialized(true);
      });
  }, []);

  return initialized ? (
    <div className={classes.root}>
      <Typography variant="h4">Revisions</Typography>
      <Divider />
      <Container maxWidth="lg">
        <Typography variant="h6">Create new revision</Typography>
        <Grid container style={{ justifyContent: "center" }}>
          <CreateRevisionForm
            api={api}
            setData={setRevisions}
            machines={machines}
          />
        </Grid>
        <div
          style={{
            marginTop: "10px",
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Divider style={{ width: "100%" }} />
          <Typography variant="h6" style={{ margin: "10px" }}>
            Revisions History
          </Typography>
          <FilterRevisionForm
            api={api}
            revisions={revisions}
            machines={machines}
            setRevisions={setRevisions}
          />
          <RevisionsTable
            headers={["Machine", "Date", "Time", "Note"]}
            data={revisions}
            setData={setRevisions}
            api={api}
          />
        </div>
      </Container>
    </div>
  ) : (
    <CircularProgress style={{ justifySelf: "center" }} />
  );
};

export default Revisions;

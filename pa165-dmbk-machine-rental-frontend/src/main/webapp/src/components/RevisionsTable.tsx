import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { makeStyles } from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Button from "@material-ui/core/Button";
import Modal from "@material-ui/core/Modal";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";

const useStyles = makeStyles({
  table: {
    minWidth: 650,
  },
  modal: {
    padding: "25px",
    background: "#FFFFFF",
    position: "absolute",
    top: `50%`,
    left: `50%`,
    transform: `translate(-50%, -50%)`,
  },
});

const RevisionsTable = ({ headers, data, api, setData }: any) => {
  const classes = useStyles();
  const [openedModal, setOpenedModal] = useState(false);
  const [modalId, setModalId] = useState(0);
  const [note, setNote] = useState("");

  useEffect(() => {}, [data]);

  const handleDelete = (id: number) => {
    api.delete(`${id}`).then((res: any) => {
      setData(data.filter((revision: any) => revision.id !== id));
    });
  };

  const handleNoteChanged = (event: any) => {
    setNote(event.target.value);
  };

  const handleAddNote = (event: any, id: number) => {
    event.preventDefault();
    api
      .post(`setRevisionNote/${id}`, `${note}`, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res: any) => {
        api
          .get(`all`)
          .then((res: any) => {
            setData(res.data);
          })
          .catch();
      });
    setOpenedModal(false);
  };

  return (
    <TableContainer component={Paper}>
      <Table className={classes.table} aria-label="simple table">
        <TableHead>
          <TableRow>
            {headers.map((header: string, index: number) => (
              <TableCell key={"header_" + index}>{header}</TableCell>
            ))}
            <TableCell align="center">Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row: any) => (
            <TableRow key={row.id}>
              <TableCell component="th" scope="row">
                {`${row.machine.id} - ${row.machine.name}`}
              </TableCell>
              <TableCell align="left">{row.revisionDate}</TableCell>
              <TableCell align="left">{row.revisionTime}</TableCell>
              <TableCell align="left">{row.note}</TableCell>
              <TableCell align="right">
                <Button
                  variant="contained"
                  onClick={() => {
                    setOpenedModal(true);
                    setModalId(row.id);
                  }}
                >
                  Change note
                </Button>
                <Button
                  variant="contained"
                  onClick={() => handleDelete(row.id)}
                >
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <Modal
        open={openedModal}
        onClose={() => setOpenedModal(false)}
        aria-labelledby="simple-modal-title"
        aria-describedby="simple-modal-description"
      >
        <div className={classes.modal}>
          <form
            onSubmit={(event) => handleAddNote(event, modalId)}
            style={{
              display: "flex",
              flexDirection: "column",
            }}
          >
            <Typography variant="h6">Set new note</Typography>
            <TextField
              name="note"
              label="Note"
              type="text"
              onChange={handleNoteChanged}
            />
            <Button variant="contained" type="submit">
              Change
            </Button>
          </form>
        </div>
      </Modal>
    </TableContainer>
  );
};

RevisionsTable.propTypes = {
  headers: PropTypes.array,
  data: PropTypes.array,
  setData: PropTypes.func,
  api: PropTypes.any,
};

export default RevisionsTable;

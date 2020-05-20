import React, { useState, useEffect } from "react";
import axios from "axios";
import PropTypes from "prop-types";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import Grid from "@material-ui/core/Grid";
import DateFnsUtils from "@date-io/date-fns";
import {
  DatePicker,
  TimePicker,
  MuiPickersUtilsProvider,
} from "@material-ui/pickers";

const formatDate = (date: any) => {
  var d = new Date(date),
    month = "" + (d.getMonth() + 1),
    day = "" + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2) month = "0" + month;
  if (day.length < 2) day = "0" + day;

  return [year, month, day].join("-");
};

const formatTime = (time: any) => {
  var d = new Date(time),
    hour = "" + d.getHours(),
    minute = "" + d.getMinutes(),
    second = "" + d.getSeconds();

  if (hour.length < 2) hour = "0" + hour;
  if (minute.length < 2) minute = "0" + minute;
  if (second.length < 2) second = "0" + second;

  return [hour, minute, second].join(":");
};

const CreateRevisionForm = ({ api, setData, revisions }: any) => {
  const [selectedDate, setSelectedDate] = useState("2020-05-19");
  const [selectedTime, setSelectedTime] = useState(new Date());
  const [apiTime, setApiTime] = useState(formatTime(selectedTime));
  const [selectedMachine, setSelectedMachine] = useState(1);
  const [note, setNote] = useState("");
  const [machines, setMachines] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/rest/machine`).then(
      (res: any) => setMachines(res)
    );
  }, [])

  const handleSubmit = (event: any) => {
    event.preventDefault();
    const data = {
      revisionDate: selectedDate,
      revisionTime: apiTime,
      machine: {
        id: selectedMachine,
      },
      note: note,
    };
    console.log("object to create: ", data);
    api.post("create", data).then((res: any) => {
      api.get(`all`).then((res: any) => setData(res.data))
    });
  };

  const handleDateChange = (value: any) => {
    setSelectedDate(formatDate(value));
  };

  const handleTimeChange = (value: any) => {
    setApiTime(formatTime(value));
    setSelectedTime(value);
  };

  const handleMachineSelect = (event: any) => {
    setSelectedMachine(event.target.value);
  };

  const handleNoteChange = (event: any) => {
    setNote(event.target.value);
  };

  return (
    <>
      <form onSubmit={handleSubmit} style={{ width: "75%" }}>
        <Grid container>
          <MuiPickersUtilsProvider utils={DateFnsUtils}>
            <Grid item xs={12}>
              <DatePicker
                value={selectedDate}
                onChange={handleDateChange}
                label="Revision date"
                disableFuture={true}
                fullWidth
              />
            </Grid>
            <Grid item xs={12}>
              <TimePicker
                value={selectedTime}
                onChange={handleTimeChange}
                label="Revision time"
                ampm={false}
                fullWidth
              />
            </Grid>
          </MuiPickersUtilsProvider>
          <Grid item xs={12}>
            <FormControl style={{ width: "100%" }}>
              <InputLabel id="select-label">Machine</InputLabel>
              <Select
                labelId="select-label"
                value={selectedMachine}
                onChange={handleMachineSelect}
                style={{ textAlign: "left" }}
                fullWidth
              >
                {machines.map((machine: any) => <MenuItem value={machine.id} key={machine.id}>{machine.name}</MenuItem>)}
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12}>
            <TextField
              name="note"
              label="Note"
              type="text"
              onChange={handleNoteChange}
              fullWidth
            />
          </Grid>
          <Grid item xs={12}>
            <Button
              color="primary"
              variant="contained"
              type="submit"
              style={{ marginTop: "10px" }}
            >
              Create
            </Button>
          </Grid>
        </Grid>
      </form>
    </>
  );
};

CreateRevisionForm.propTypes = {
  api: PropTypes.any,
  setData: PropTypes.func,
  revisions: PropTypes.array,
};

export default CreateRevisionForm;

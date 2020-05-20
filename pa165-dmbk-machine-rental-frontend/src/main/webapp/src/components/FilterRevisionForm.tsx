import React, { useState, useEffect } from 'react';
import axios from "axios";
import PropTypes from "prop-types";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import InputLabel from "@material-ui/core/InputLabel";
import FormControl from "@material-ui/core/FormControl";
import Grid from "@material-ui/core/Grid";
import Button from "@material-ui/core/Button";
import DateFnsUtils from "@date-io/date-fns";
import {
  DatePicker,
  MuiPickersUtilsProvider,
} from "@material-ui/pickers";
import Radio from "@material-ui/core/Radio";
import RadioGroup from "@material-ui/core/RadioGroup";
import FormControlLabel from "@material-ui/core/FormControlLabel";
import FormLabel from "@material-ui/core/FormLabel";

const formatDate = (date: any) => {
  var d = new Date(date),
    month = "" + (d.getMonth() + 1),
    day = "" + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2) month = "0" + month;
  if (day.length < 2) day = "0" + day;

  return [year, month, day].join("-");
};

const FilterRevisionForm = ({ api, revisions, setRevisions }:any) => {
  const [selectMachineValue, setSelectMachineValue] = useState(0);
  const [selectedDate, setSelectedDate] = useState("2020-05-19");
  const [value, setValue] = useState("all");
  const [machines, setMachines] = useState([]);

  useEffect(() => {
    axios.get(`http://localhost:8080/rest/machine`).then(
      (res: any) => setMachines(res)
    )
  }, [])

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValue((event.target as HTMLInputElement).value);
  };

  const handleDateChange = (value: any) => {
    setSelectedDate(formatDate(value));
  }

  const handleMachineChange = (event: any) => {
    const id = event.target.value;
    setSelectMachineValue(id);
  };

  const handleSubmit = (event: any) => {
    event.preventDefault();
    let machineFilter: any = [];
    let dateFilter: any = [];
    selectMachineValue !== 0 && (
      api
      .get(`machine/${selectMachineValue}`)
      .then((res: any) => {
        machineFilter = res.data;
        console.log("machine filter: ", machineFilter)
      })
      .catch());
    value !== "all" && (
      api
        .post(`revisionDate${value}`, `"${selectedDate}"`, {
          headers: {
            'Content-Type': 'application/json',
          }
        })
        .then((res: any) => {
          console.log("date filter data: ", res.data);
          dateFilter = res.data;
        })
        .catch()
    );
    console.log(`${selectedDate}`);
    if (machineFilter.length === 0 && dateFilter.length === 0) {
      api
        .get(`all`)
        .then((res: any) => {
          setRevisions(res.data);
        })
        .catch();
    } else {
      let filtered = machineFilter.filter((machineElement: any) => dateFilter.map((dateElem: any) => dateElem.id).includes(machineElement.id));
      setRevisions(filtered);
      console.log(filtered);
    }
  }

  return (
    <div style={{ width: "75%", marginBottom: "10px" }}>
      
        <Grid container style={{ margin: "10px" }}>
          <Grid item xs={12}>
            <FormLabel component="legend">Filter by machine</FormLabel>
            <FormControl style={{ width: "100%" }}>
              <InputLabel id="demo-simple-select-label">
                Select machine
              </InputLabel>
              <Select
                labelId="demo-simple-select-label"
                value={selectMachineValue}
                onChange={handleMachineChange}
                fullWidth
            >
              {machines.map((machine: any) => <MenuItem value={machine.id} key={machine.id}>{machine.name}</MenuItem>)}
              </Select>
            </FormControl>
          </Grid>
          <Grid item xs={12} style={{ margin: "10px 0px" }}>
            <FormLabel component="legend">Filter by date</FormLabel>
          </Grid>
          <Grid item xs={6}>
            <FormControl component="fieldset">
              <RadioGroup value={value} onChange={handleChange}>
                <FormControlLabel
                  value="Before"
                  control={<Radio />}
                  label="Revisions before selected date"
                />
                <FormControlLabel
                  value="After"
                  control={<Radio />}
                  label="Revisions after selected date"
                />
                <FormControlLabel
                  value="all"
                  control={<Radio />}
                  label="All dates"
                />
              </RadioGroup>
            </FormControl>
          </Grid>
          <Grid item xs={6} style={{ display: "flex", alignItems: "center" }}>
            <MuiPickersUtilsProvider utils={DateFnsUtils}>
              <DatePicker
                value={selectedDate}
                onChange={handleDateChange}
                label="Select date: "
                disableFuture={true}
                fullWidth
              />
            </MuiPickersUtilsProvider>
          </Grid>
        </Grid>
        <Button
          color="primary"
          variant="contained"
          onClick={handleSubmit}
          style={{ marginTop: "10px" }}
        >
          Filter
        </Button>
    </div>
  );
}

FilterRevisionForm.propTypes = {
  api: PropTypes.any,
  revisions: PropTypes.array,
  setRevisions: PropTypes.func
}

export default FilterRevisionForm

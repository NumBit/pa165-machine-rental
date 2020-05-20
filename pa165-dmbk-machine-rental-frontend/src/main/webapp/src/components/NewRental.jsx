import React, {Component, useState} from "react";
import RentalDataSercvice from "./RentalDataService";
import TextField from "@material-ui/core/TextField";
import DateFnsUtils from '@date-io/date-fns';
import {
    MuiPickersUtilsProvider,
    KeyboardDatePicker,
} from '@material-ui/pickers';
import Grid from "@material-ui/core/Grid";
import MenuItem from "@material-ui/core/MenuItem";
import Button from "@material-ui/core/Button";
import TableHead from "@material-ui/core/TableHead";
import {TableBody, TableCell, TableRow} from "@material-ui/core";
import Table from "@material-ui/core/Table";

class NewRental extends Component{
    constructor(props) {
        super(props);
        this.state = {
            customers: [],
            machines: [],
            user: {
                type: "CustomerDto",
                id: 7,
                login: "user7",
                legalForm: "INDIVIDUAL",
                email: "user7@gmail.com"
            },
            rentalDate: "",
            returnDate: "",
            machine: {
                id: 1,
                name: "Best machine",
                description: "machine description",
                manufacturer: "manufacturer",
                price: 100
            },
            description: "",
            value: "",
            rental: {
                "rentalDate": "2020-02-02",
                "returnDate": "2020-03-02",
                "customer": {
                    "type": "CustomerDto",
                    "id": 7,
                    "login": "user7",
                    "legalForm": "INDIVIDUAL",
                    "email": "user7@gmail.com"
                },
                "machine": {
                    "id": 1,
                    "name": "Best machine",
                    "description": "machine description",
                    "manufacturer": "manufacturer",
                    "price": 100
                },
                "description": "long term"
            }
        };
        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleRentalChange = this.handleRentalChange.bind(this);
        this.handleReturnChange = this.handleReturnChange.bind(this);
        this.handleMachineChange = this.handleMachineChange.bind(this);

    }

    handleDescriptionChange(event) {
        this.setState({description: event.target.value});
    }
    handleRentalChange(event) {
        this.setState({rentalDate: event.target.value});
    }
    handleReturnChange(event) {
        this.setState({returnDate: event.target.value});
    }
    handleMachineChange(event) {
        this.setState({machine: event.target.value});
    }

    createRental(description, rentalDate, returnDate, machine, user) {
        RentalDataSercvice.createRental(description, rentalDate, returnDate, machine, user).then()
    }

    componentDidMount() {
        RentalDataSercvice.getAllMachines().then(response => this.setState({machines: response.data}))
    }


    render(){
        return(
            <form noValidate autoComplete="off">
                <div>
                    <TextField required label="Description"  value={this.state.description} onChange={this.handleDescriptionChange} />
                    <TextField required  label="Rental Date" value={this.state.rentalDate} onChange={this.handleRentalChange} />
                    <TextField required  label="Return Date" value={this.state.returnDate} onChange={this.handleReturnChange}/>
                    <TextField required  label="Machine" value={this.state.machine.name} onChange={this.handleMachineChange}/>
                    <TextField
                        id="standard-select-currency"
                        select
                        label="Machine"
                        value={this.state.machine}
                        onChange={this.handleMachineChange}
                        helperText="Please select machine to rent"
                    >
                        {this.state.machines.map((option) => (
                            <MenuItem key={option} value={option}>
                                {option.name}
                            </MenuItem>
                        ))}
                    </TextField>

                </div>
                <Button variant="contained" color="primary" onClick={() => this.createRental(this.state.description, this.state.rentalDate, this.state.returnDate, this.state.machine, this.state.user)}>
                    ADD
                </Button>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Description</TableCell>
                            <TableCell align="right">Rental Date</TableCell>
                            <TableCell align="right">Return Date</TableCell>

                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.machines.map((row) => (
                            <TableRow key={row.name}>
                                <TableCell component="th" scope="row">
                                    {row.name}
                                </TableCell>
                                <TableCell align="right">{row.manufacturer}</TableCell>
                                <TableCell align="right">{row.price}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </form>



        )
    }

}
export default NewRental
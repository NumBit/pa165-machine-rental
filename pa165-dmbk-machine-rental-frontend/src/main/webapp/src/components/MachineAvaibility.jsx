import React, {Component} from "react";
import RentalDataSercvice from "./RentalDataService";
import TextField from "@material-ui/core/TextField";
import MenuItem from "@material-ui/core/MenuItem";
import Button from "@material-ui/core/Button";
import { Alert } from '@material-ui/lab';

class MachineAvaibility extends Component{
    constructor(props) {
        super(props);
        this.state = {
            machines: [],
            machine: {},
            id: 0,
            rentalDate: "",
            returnDate: "",
            available: false,
            checked: false
        }

        this.handleRentalChange = this.handleRentalChange.bind(this);
        this.handleReturnChange = this.handleReturnChange.bind(this);
        this.handleMachineChange = this.handleMachineChange.bind(this);
    }

    checkMachineAvailability(id, rentalDate, returnDate) {
        RentalDataSercvice.checkMachineAvailability(id, rentalDate, returnDate).then(response => this.setState({available: response.data, checked: true}))
    }

    componentDidMount() {
        RentalDataSercvice.getAllMachines().then(response => this.setState({machines: response.data}))
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



    render() {
        return (
            <div>
                <form noValidate autoComplete="off">
                    <div>
                        <TextField
                            select
                            label="Machine"
                            value={this.state.machine}
                            onChange={this.handleMachineChange}
                            style={{minWidth:200}}
                        >
                            {this.state.machines.map((option) => (
                                <MenuItem key={option} value={option}>
                                    {option.name}
                                </MenuItem>
                            ))}
                        </TextField>
                        <TextField
                            id="date"
                            label="Rental Date"
                            type="date"
                            value={this.state.rentalDate}
                            onChange={this.handleRentalChange}
                            defaultValue="2020-01-01"
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />

                        <TextField
                            id="date"
                            label="Return Date"
                            type="date"
                            value={this.state.returnDate}
                            onChange={this.handleReturnChange}
                            defaultValue="2020-01-01"
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />


                    </div>
                    <Button variant="contained" color="primary"  style={{marginTop: 10}} onClick={() => this.checkMachineAvailability(this.state.machine.id, this.state.rentalDate, this.state.returnDate)}>
                        CheckAvailability
                    </Button>
                </form>
                {this.state.available ? <Alert severity="success">Machine is available.</Alert>: <Alert severity="error">Machine is not available in selected dates!</Alert>}
            </div>
        );
    }
}

export default MachineAvaibility
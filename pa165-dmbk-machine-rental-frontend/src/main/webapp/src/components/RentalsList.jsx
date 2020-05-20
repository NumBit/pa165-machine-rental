import React, {Component} from "react";
import RentalDataService from "./RentalDataService";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import Table from "@material-ui/core/Table";
import {Paper, TableRow, TableCell, TableBody} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import MenuItem from "@material-ui/core/MenuItem";
import TablePagination from "@material-ui/core/TablePagination";


class RentalsList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rentals: [],
            machines: [],
            user: {},
            rentalDate: "2020-01-01",
            returnDate: "2020-01-01",
            machine: {},
            description: "",
            page: 0,
            rowsPerPage: 10
        };

        this.handleDescriptionChange = this.handleDescriptionChange.bind(this);
        this.handleRentalChange = this.handleRentalChange.bind(this);
        this.handleMachineChange = this.handleMachineChange.bind(this);
        this.handleReturnChange = this.handleReturnChange.bind(this);
        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);
    }


    refreshRentals() {
        RentalDataService.getAllRentalsByCustomerId(this.state.user.id).then(response => this.setState({rentals: response.data}));
    }

    deleteRental(id) {
        RentalDataService.deleteRental(id).then(() => this.refreshRentals())
    }

    getAuthenticatedUser() {
        RentalDataService.getAuthenticatedUser().then(response => this.setState({user: response.data}))
    }

    createRental(description, rentalDate, returnDate, machine, user) {
        RentalDataService.createRental(description, rentalDate, returnDate, machine, user).then(()=> this.refreshRentals());
    }

     handleChangePage(event, newPage){
        this.setState({page: newPage})
    }

    handleChangeRowsPerPage(event) {
        this.setState({rowsPerPage: event.target.value});
        this.setState({page: 0})
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



    componentDidMount() {
        RentalDataService.getAuthenticatedUser().then(response => this.setState({user: response.data})).then(()=> this.refreshRentals());
        //RentalDataService.getAllRentalsByCustomerId(this.state.user.id).then(response => this.setState({rentals: response.data}));
        RentalDataService.getAllMachines().then(response=> this.setState({machines: response.data}));
    }


    render(){
        return(
            <div>
                <form noValidate autoComplete="off">
                    <div>
                        <TextField required label="Description"  value={this.state.description} onChange={this.handleDescriptionChange} />


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


                        <TextField
                            select
                            label="Machine"
                            value={this.state.machine}
                            onChange={this.handleMachineChange}
                            helperText="Select machine to rent"
                            style={{minWidth:200}}
                        >
                            {this.state.machines.map((option) => (
                                <MenuItem key={option} value={option}>
                                    {option.name}
                                </MenuItem>
                            ))}
                        </TextField>


                    </div>
                    <Button variant="contained" color="primary" style={{marginBottom: 10}} onClick={() => this.createRental(this.state.description, this.state.rentalDate, this.state.returnDate, this.state.machine, this.state.user)}>
                        Create Rental
                    </Button>
                </form>
            <TableContainer component={Paper}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Description</TableCell>
                            <TableCell align="right">Rental Date</TableCell>
                            <TableCell align="right">Return Date</TableCell>
                            <TableCell align="right">Machine</TableCell>
                            <TableCell align="right">Customer</TableCell>
                            <TableCell align="right">Delete</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {this.state.rentals.slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage).map((row) => {
                            return (
                                <TableRow key={row.name}>
                                    <TableCell component="th" scope="row">
                                        {row.description}
                                    </TableCell>
                                    <TableCell align="right">{row.rentalDate}</TableCell>
                                    <TableCell align="right">{row.returnDate}</TableCell>
                                    <TableCell align="right">{row.machine.name}</TableCell>
                                    <TableCell align="right">{row.customer.login}</TableCell>
                                    <TableCell align="right">
                                        <Button variant="contained" color="secondary"
                                                onClick={() => this.deleteRental(row.id)}>
                                            X
                                        </Button>
                                    </TableCell>
                                </TableRow>
                            );
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
                <TablePagination
                    rowsPerPageOptions={[10, 25, 100]}
                    component="div"
                    count={this.state.rentals.length}
                    rowsPerPage={this.state.rowsPerPage}
                    page={this.state.page}
                    onChangePage={this.handleChangePage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </div>
        )
    }

}

export default RentalsList
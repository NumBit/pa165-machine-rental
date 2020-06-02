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
import Alert from "@material-ui/lab/Alert";
import {Link, useHistory} from "react-router-dom";


class RentalsList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rentals: [],
            page: 0,
            rowsPerPage: 10,
            creatingResponse: 0,
        };


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


     handleChangePage(event, newPage){
        this.setState({page: newPage})
    }

    handleChangeRowsPerPage(event) {
        this.setState({rowsPerPage: event.target.value});
        this.setState({page: 0})
    }




    componentDidMount() {
        RentalDataService.getAuthenticatedUser().then(response => this.setState({user: response.data})).then(()=> this.refreshRentals());
    }


    render(){
        return(
            <div>
                <Link to="/createRental"><Button >Create rental </Button></Link>
            <TableContainer component={Paper}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Description</TableCell>
                            <TableCell align="right">Rental Date</TableCell>
                            <TableCell align="right">Return Date</TableCell>
                            <TableCell align="right">Machine</TableCell>
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
import React, {Component} from "react";
import RentalDataSercvice from "../RentalDataSercvice";
import TableContainer from "@material-ui/core/TableContainer";
import TableHead from "@material-ui/core/TableHead";
import Table from "@material-ui/core/Table";
import {Paper, TableRow, TableCell, TableBody} from "@material-ui/core";
import Button from "@material-ui/core/Button";
class RentalsList extends Component {
    constructor(props) {
        super(props)
        this.state = {
            rentals: []
        }
    }
    refreshRentals() {
        RentalDataSercvice.getAllRentals().then(response => this.setState({rentals: response.data}))
    }

    deleteRental(id) {
        RentalDataSercvice.deleteRental(id).then(() => this.refreshRentals())
    }

    componentDidMount() {
        RentalDataSercvice.getAllRentals().then(response => this.setState({rentals: response.data}))
    }

    render(){
        return(
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
                        {this.state.rentals.map((row) => (
                            <TableRow key={row.name}>
                                <TableCell component="th" scope="row">
                                    {row.description}
                                </TableCell>
                                <TableCell align="right">{row.rentalDate}</TableCell>
                                <TableCell align="right">{row.returnDate}</TableCell>
                                <TableCell align="right">{row.machine.name}</TableCell>
                                <TableCell align="right">{row.customer.login}</TableCell>
                                <TableCell align="right">
                                    <Button variant="contained" color="secondary" onClick={()=>this.deleteRental(row.id)}>
                                        X
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }

}

export default RentalsList
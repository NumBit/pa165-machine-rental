import RentalDataService from "./RentalDataService";
import TableContainer from "@material-ui/core/TableContainer";
import {Paper, TableBody, TableCell, TableRow} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import Button from "@material-ui/core/Button";
import TablePagination from "@material-ui/core/TablePagination";
import React, {Component} from "react";

class CustomersList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            customers: [],
            page: 0,
            rowsPerPage: 10,
        };
        this.handleChangePage = this.handleChangePage.bind(this);
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this);

    }
        handleChangePage(event, newPage){
            this.setState({page: newPage})
        };

        handleChangeRowsPerPage(event) {
            this.setState({rowsPerPage: event.target.value});
            this.setState({page: 0})
        };

        getAllCustomers() {
            RentalDataService.getAllCustomers().then(response => this.setState({customers: response.data}))
        }

        deleteCustomer(id) {
            RentalDataService.deleteCustomer(id).then(() => this.getAllCustomers());
        }

        componentDidMount(){
            this.getAllCustomers();
        }


        render() {
            return(
                <div>
                    <TableContainer component={Paper}>
                        <Table aria-label="simple table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Login</TableCell>
                                    <TableCell align="right">Legal Form</TableCell>
                                    <TableCell align="right">Email</TableCell>
                                    <TableCell align="right">Delete</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {this.state.customers.slice(this.state.page * this.state.rowsPerPage, this.state.page * this.state.rowsPerPage + this.state.rowsPerPage).map((row) => {
                                    return (
                                        <TableRow key={row.name}>
                                            <TableCell component="th" scope="row">
                                                {row.login}
                                            </TableCell>
                                            <TableCell align="right">{row.legalForm}</TableCell>
                                            <TableCell align="right">{row.email}</TableCell>
                                            <TableCell align="right">
                                                <Button variant="contained" color="secondary"
                                                        onClick={() => this.deleteCustomer(row.id)}>
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
                        count={this.state.customers.length}
                        rowsPerPage={this.state.rowsPerPage}
                        page={this.state.page}
                        onChangePage={this.handleChangePage}
                        onChangeRowsPerPage={this.handleChangeRowsPerPage}
                    />
                </div>
            )
        }

}

export default CustomersList
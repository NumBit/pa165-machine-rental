import React, {useContext, useEffect, useState} from "react";
import {GlobalContext, isAdmin} from "../context/GlobalState";
import {createStyles, makeStyles, Theme} from "@material-ui/core/styles";
import {
    Button, Divider,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TablePagination,
    TableRow
} from "@material-ui/core";
import CreateMachineForm from "../components/CreateMachineForm";
import TextField from "@material-ui/core/TextField";
import {Alert} from "@material-ui/lab";
import EditMachineForm from "../components/EditMachineForm";
import {Link} from "react-router-dom";

/**
 * Machine View with pagination
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

interface ServiceInit {
    status: 'init';
}

interface ServiceLoading {
    status: 'loading';
}

interface ServiceLoaded<T> {
    status: 'loaded';
    payload: T;
}

interface ServiceError {
    status: 'error';
    error: Error;
}

interface Row {
    id: 'id' | 'name' | 'description' | 'manufacturer' | 'price';
    label: string;
    minWidth?: number;
    align?: 'right';
    format?: (value: number) => string;
}

const table: Row[] = [
    {id: 'name', label: 'Name', minWidth: 50},
    {
        id: 'manufacturer',
        label: 'Manufacturer',
        minWidth: 50,
        align: 'right'
    },
    {
        id: 'description',
        label: 'Description',
        minWidth: 50,
        align: 'right'
    },
    {
        id: 'price',
        label: '€/day',
        minWidth: 50,
        align: 'right',
    }
];

export interface Machine {
    id: number;
    name: string;
    description: string;
    manufacturer: string;
    price: number;
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            width: '100%',
            '& .MuiTextField-root': {
                margin: theme.spacing(1),
                width: '25ch',
            },
            '.MuiDivider-root': {
                margin: '15px 0 15px 0 !important'
            }
        },
        container: {
            maxHeight: 1080,
        },
    }),
);

export type MachineService<T> =
    | ServiceInit
    | ServiceLoading
    | ServiceLoaded<T>
    | ServiceError;

export default function Machines() {
    const {user} = useContext(GlobalContext);
    const classes = useStyles();
    const [itemEdited, setItemEdited] = React.useState(0);
    const [deleteAlert, setDeleteAlert] = React.useState(false);
    const [page, setPage] = React.useState(0);
    const [rowsPerPage, setRowsPerPage] = React.useState(10);
    const [nameSearch, setNameSearch] = useState("");

    const [result, setResult] = useState<MachineService<Machine[]>>({status: 'loading'});

    useEffect(() => {
        fetchAll().then();
    }, []);

    function fetchAll() {
        return fetch('/pa165/rest/machine/')
            .then(res => res.json())
            .then(res => setResult({status: 'loaded', payload: res}))
            .catch(error => setResult({status: 'error', error}));
    }

    const handleSearchNameChange = (event: any) => {
        setNameSearch(event.target.value);
        if (nameSearch !== "") {
            fetch('/pa165/rest/machine/namelike/' + nameSearch)
                .then(res => res.json())
                .then(res => setResult({status: 'loaded', payload: res}))
                .catch(error => setResult({status: 'error', error}));
        } else {
            fetchAll().then();
        }
    };

    const handleDelete = (itemId: number) => {
        let resStatus = 0
        return fetch('/pa165/rest/machine/' + itemId, {
            method: 'delete'
        }).then((res) => resStatus = res.status).then(() => {
            switch (resStatus) {
                case 304:
                    handleDeleteAlert(true);
                    break;
                default:
                    handleDeleteAlert(false);
                    fetchAll().then();
                    break;
            }
        })
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleDeleteAlert = (state: boolean) => {
        setDeleteAlert(state);
    };

    const handleItemEdit = (itemId: number) => {
        if (itemId === itemEdited) {
            setItemEdited(0);
        } else {
            setItemEdited(itemId);
        }
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    return (
        <Paper className={classes.root} style={{width: "80%"}}>
            {isAdmin(user) ?
                <CreateMachineForm
                    setData={setResult}/>
                : ""}
            <Divider/>
            <TextField id="outlined-search" label="Search field" type="search" onChange={handleSearchNameChange}
                       variant="outlined"/>
            <Button color="primary"
                    variant="contained"
                    onClick={() => fetchAll()}
                    style={{marginTop: "15px"}}>
                Clean
            </Button>
            <Divider/>

            {deleteAlert ?
                <div><Alert severity="warning">Machine is used in other relations and will not be deleted.</Alert></div>
                : ""}


            <TableContainer className={classes.container}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            {table.map((table) => (
                                <TableCell
                                    key={table.id}
                                    align={table.align}
                                    style={{minWidth: table.minWidth}}
                                >
                                    {table.label}
                                </TableCell>
                            ))}
                            <TableCell align="right" style={{minWidth: 50}}>
                                Actions
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {result.status === 'loading' && <></>}
                        {result.status === 'loaded' &&
                        result.payload.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return (
                                <>
                                    <TableRow hover role="checkbox" tabIndex={-1} key={row.name}>
                                        {table.map((table) => {
                                            const value = row[table.id];
                                            return (
                                                <TableCell key={table.id} align={table.align}>
                                                    {table.format && typeof value === 'number' ? table.format(value) : value}
                                                </TableCell>
                                            );
                                        })}

                                        <TableCell align="right">
                                            {isAdmin(user) ?
                                                <>
                                                    <Button variant="contained" color="primary"
                                                            onClick={() => handleItemEdit(row.id)}>EDIT</Button>
                                                    <Button variant="contained" color="secondary"
                                                            onClick={() => handleDelete(row.id)}>X</Button>
                                                </>
                                                : <Link
                                                    to={`/createRental/${row.id}`}>
                                                    <Button variant="contained" color="primary">RENT</Button>
                                                </Link>
                                            }
                                        </TableCell>
                                    </TableRow>
                                    {itemEdited === row.id ?
                                        <TableRow><TableCell colSpan={5}><EditMachineForm machine={row}
                                                                                          ref={fetchAll()}/></TableCell></TableRow>
                                        : ""}

                                </>
                            );
                        })
                        }
                        {result.status === 'error' && (
                            <></>
                        )}

                    </TableBody>
                </Table>
            </TableContainer>
            {result.status === 'loading' && <div><Alert severity="warning">Loading...</Alert></div>}
            {result.status === 'loaded' &&
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={result.payload.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onChangePage={handleChangePage}
                onChangeRowsPerPage={handleChangeRowsPerPage}
            />
            }
            {result.status === 'error' && (
                <div><Alert severity="error">Error, the backend not connected.</Alert></div>
            )}
        </Paper>
    );
}
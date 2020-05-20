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

/**
 * Machine View
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
    {id: 'id', label: 'Id', minWidth: 50},
    {id: 'name', label: 'Name', minWidth: 50},
    {
        id: 'description',
        label: 'Description',
        minWidth: 50,
        align: 'right'
    },
    {
        id: 'manufacturer',
        label: 'Manufacturer',
        minWidth: 50,
        align: 'right'
    },
    {
        id: 'price',
        label: '€',
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
            '.MuiDivider-root':{
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
        if (nameSearch !== ""){
            fetch('/pa165/rest/machine/namelike/' + nameSearch)
                .then(res => res.json())
                .then(res => setResult({status: 'loaded', payload: res}))
                .catch(error => setResult({status: 'error', error}));
        }else {
            fetchAll().then();
        }
    };

    const handleDelete = (itemId: number) => {
        return fetch('/pa165/rest/machine/' + itemId, {
            method: 'delete'
        }).then(() => fetchAll().then());
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    return (
        <Paper className={classes.root}>
            <CreateMachineForm
            setData={setResult}/>
            <Divider />
            <TextField id="outlined-search" label="Search field" type="search" onChange={handleSearchNameChange} variant="outlined" />
            <Button color="primary"
                    variant="contained"
                    onClick={() => fetchAll()}
                    style={{ marginTop: "15px" }}>
                Clean
            </Button>
            <Divider/>
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
                            {isAdmin(user) ? <TableCell align="right" style={{minWidth: 50}}>
                                Actions
                            </TableCell> : ""}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {result.status === 'loading' && <div>Loading...</div>}
                        {result.status === 'loaded' &&
                        result.payload.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return (
                                <TableRow hover role="checkbox" tabIndex={-1} key={row.name}>
                                    {table.map((table) => {
                                        const value = row[table.id];
                                        return (
                                            <TableCell key={table.id} align={table.align}>
                                                {table.format && typeof value === 'number' ? table.format(value) : value}
                                            </TableCell>
                                        );
                                    })}

                                    {isAdmin(user) ? <TableCell align="right">
                                        <Button onClick={() => handleDelete(row.id)}>Delete</Button>
                                    </TableCell> : ""}
                                </TableRow>
                            );
                        })
                        }
                        {result.status === 'error' && (
                            <div>Error, the backend not connected.</div>
                        )}

                    </TableBody>
                </Table>
            </TableContainer>
            {result.status === 'loading' && <div>Loading...</div>}
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
                <div>Error, the backend not connected.</div>
            )}
        </Paper>
    );
}
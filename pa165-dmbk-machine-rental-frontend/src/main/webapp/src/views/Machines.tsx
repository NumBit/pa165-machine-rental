import React, {useContext, useEffect, useState} from "react";
import { GlobalContext } from "../context/GlobalState";
import {makeStyles} from "@material-ui/core/styles";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow
} from "@material-ui/core";

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
  { id: 'id', label: 'Id', minWidth: 50 },
  { id: 'name', label: 'Name', minWidth: 50 },
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

const useStyles = makeStyles({
  root: {
    width: '100%',
  },
  container: {
    maxHeight: 440,
  },
});

export type MachineService<T> =
    | ServiceInit
    | ServiceLoading
    | ServiceLoaded<T>
    | ServiceError;

export default function Machines() {
  const { user } = useContext(GlobalContext);
  const customersView = <p>customers view</p>;
  const adminsView = <p>admins view</p>;

  const classes = useStyles();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  const [result, setResult] = useState<MachineService<Machine[]>>({ status: 'loading' });
  useEffect(() => {
    fetch('http://localhost:8080/pa165/rest/machine/')
        .then(res => res.json())
        .then(res => setResult({ status: 'loaded', payload: res}))
        .catch(error => setResult({ status: 'error', error }));
  }, []);

  const handleChangePage = (event: unknown, newPage: number) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRowsPerPage(+event.target.value);
    setPage(0);
  };

  return (
      <Paper className={classes.root}>
        <TableContainer className={classes.container}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                {table.map((table) => (
                    <TableCell
                        key={table.id}
                        align={table.align}
                        style={{ minWidth: table.minWidth }}
                    >
                      {table.label}
                    </TableCell>
                ))}
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
        {user.isAdmin ? adminsView : customersView}
      </Paper>
  );
}
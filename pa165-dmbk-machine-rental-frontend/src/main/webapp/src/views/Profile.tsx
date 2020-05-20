import React, { useState, useEffect } from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import CardHeader from '@material-ui/core/CardHeader';
import Table from '@material-ui/core/Table';
import { TableRow, TableBody, TableCell } from '@material-ui/core';


const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    container: {
      display: 'flex',
      flexWrap: 'wrap',
      width: 400,
      margin: `${theme.spacing(0)} auto`
    },
    loginBtn: {
      marginTop: theme.spacing(2),
      flexGrow: 1
    },
    header: {
      textAlign: 'center',
      background: '#212121',
      color: '#fff'
    },
    card: {
      marginTop: theme.spacing(10),
      minWidth: 455,
      float: 'left'
    },
    img: {
      width: '15%',
      height: '15%',
      paddingTop: '50px'
    },
    table: {
      minWidth: 450,
    },
  }),
);

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

export interface Customer {
  type: string,
  id: number,
  login: string,
  legalForm: string,
  email: string
}

export interface Admin {
  type: string,
  id: number,
  login: string,
  name: string,
  sureName: string
}

export type User = | Customer | Admin;

function isAdmin(object: any): object is Admin {
  return 'type' in object && 'id' in object && 'login' in object
              && 'name' in object && 'sureName' in object;
}

export type UserService<T> =
| ServiceInit
| ServiceLoading
| ServiceLoaded<T>
| ServiceError;

const Profile = () => {
  const classes = useStyles();
  const [result, setResult] = useState<UserService<User>>({ status: 'loading' });

  useEffect(() => {
      fetch('http://localhost:8080/pa165/rest/user/authenticated')
      .then(res => res.json())
      .then(res => setResult({ status: 'loaded', payload: res}))
      .catch(error => setResult({ status: 'error', error }));
  }, []);

  function userResultPrint(result: User) {
      return isAdmin(result)
        ? <TableBody>
            <TableRow key='ID'>
              <TableCell component='th' scope='row'>ID:</TableCell>
              <TableCell align='right'>{result.id}</TableCell>
            </TableRow>
            <TableRow key='Login'>
              <TableCell component='th' scope='row'>Login:</TableCell>
              <TableCell align='right'>{result.login}</TableCell>
            </TableRow>
            <TableRow key='Name'>
              <TableCell component='th' scope='row'>Name:</TableCell>
              <TableCell align='right'>{result.name}</TableCell>
            </TableRow>
            <TableRow key='Surename'>
              <TableCell component='th' scope='row'>Surename:</TableCell>
              <TableCell align='right'>{result.sureName}</TableCell>
            </TableRow>
        </TableBody>
        : <TableBody>
            <TableRow key='ID'>
              <TableCell component='th' scope='row'>ID:</TableCell>
              <TableCell align='right'>{result.id}</TableCell>
            </TableRow>
            <TableRow key='ID'>
              <TableCell component='th' scope='row'>Login:</TableCell>
              <TableCell align='right'>{result.login}</TableCell>
            </TableRow>
            <TableRow key='ID'>
              <TableCell component='th' scope='row'>Email:</TableCell>
              <TableCell align='right'>{result.email}</TableCell>
            </TableRow>
            <TableRow key='ID'>
              <TableCell component='th' scope='row'>Legalform:</TableCell>
              <TableCell align='right'>{result.legalForm}</TableCell>
            </TableRow>
        </TableBody>
  }

  return (
    <React.Fragment>
      <img alt='' className={classes.img} src={String('/pa165/profile.png')} />
      <Card className={classes.card}>
        <CardHeader className={classes.header} title="Profile Information" />
        <CardContent>
        <div>
      {result.status === 'loading' && <div>Loading...</div>}
      {result.status === 'loaded' &&
        <Table className={classes.table} aria-label="User profile">
          {userResultPrint(result.payload)}
        </Table>
      }
      {result.status === 'error' && (
        <div>Error, the backend moved to the dark side.</div>
      )}
    </div>   
        </CardContent>
        <CardActions>
        </CardActions>
      </Card>
    </React.Fragment>
  );
}

export default Profile;

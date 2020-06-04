import React, {useEffect, useState} from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";

import { Formik, Form } from "formik";
import * as yup from "yup";
import RentalDataService from "./RentalDataService";
import MenuItem from "@material-ui/core/MenuItem";
import Alert from "@material-ui/lab/Alert";

let AvailabilitySchema = yup.object().shape({
    machine: yup.object()
        .typeError("Required"),
    rentalDate:
        yup.date()
            .required("Required")
            .max(yup.ref("returnDate"), "Rental date must be before return"),
    returnDate:
        yup.date()
            .required("Required")
            .min(yup.ref("rentalDate"), "Return date must be after rental"),
});

const useStyles = makeStyles(theme => ({
    paper: {
        marginTop: theme.spacing(8),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    },
    form: {
        width: "100%", // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        height: 40,
        margin: theme.spacing(2, 0, 1),
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    }
}));

export const MachineAvailability = () => {
    const classes = useStyles();
    const [machines, setMachines] = useState([]);
    const [availability, setAvailability] = useState(false);

    useEffect(()=>{
        getAllMachines();
    }, []);

    const getAllMachines = () => {
        RentalDataService.getAllMachines().then(response => setMachines(response.data))
    };

    return(
        <Container component="main" maxWidth="xs">
            <div className={classes.paper}>
                <Typography component="h1" variant="h5">
                    Check machine availability
                </Typography>
                <Formik
                    initialValues={{
                        machine: null,
                        rentalDate: "",
                        returnDate: ""
                    }}
                    validationSchema={AvailabilitySchema}
                    onSubmit={values => {
                        RentalDataService.checkMachineAvailability(values.machine.id, values.rentalDate, values.returnDate).then(response => {setAvailability(response.data)})}}
                    >

                    {({errors, handleChange, touched }) => (
                        <Form className={classes.form}>
                            <Grid container spacing={3}>
                                <Grid item xs={12}>
                                    <TextField
                                        variant="outlined"
                                        error={Boolean(errors.rentalDate) && touched.rentalDate}
                                        name="rentalDate"
                                        label="Rental Date"
                                        type="date"
                                        onChange={handleChange}
                                        style={{minWidth:400}}
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        helperText=
                                            {errors.rentalDate && touched.rentalDate
                                                ? errors.rentalDate : null}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        variant="outlined"
                                        error={Boolean(errors.returnDate) && touched.returnDate}
                                        name="returnDate"
                                        label="Return Date"
                                        type="date"
                                        onChange={handleChange}
                                        style={{minWidth:400}}
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        helperText=
                                            {errors.returnDate && touched.returnDate
                                                ? errors.returnDate : null}
                                    />
                                </Grid>
                                <Grid item xs={12}>
                                    <TextField
                                        variant="outlined"
                                        error={Boolean(errors.machine && touched.machine)}
                                        name="machine"
                                        select
                                        label="Machine"
                                        onChange={handleChange}
                                        helperText=
                                            {errors.machine && touched.machine
                                                ? errors.machine : "Select machine to rent"}
                                        style={{minWidth:400}}
                                    >
                                        {machines.map((option) => (
                                            <MenuItem key={option} value={option}>
                                                {option.name}
                                            </MenuItem>
                                        ))}
                                    </TextField>
                                </Grid>
                                <Grid item xs={12} className={classes.center}>
                                    {touched.machine ? (availability ? <Alert severity="success">Machine is available.</Alert>: <Alert severity="error">Machine is not available in selected dates!</Alert>): null}
                                </Grid>
                                <Grid item xs={12} className={classes.center}>
                                    <Button
                                        type="submit"
                                        alignItems="center"
                                        variant="contained"
                                        color="primary"
                                        className={classes.submit}
                                    >
                                        Check availability
                                    </Button>
                                </Grid>
                            </Grid>

                        </Form>
                    )}
                </Formik>
            </div>
        </Container>
    )
};
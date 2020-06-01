import React, {useEffect, useState} from "react";
import Button from "@material-ui/core/Button";
import CssBaseline from "@material-ui/core/CssBaseline";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import Typography from "@material-ui/core/Typography";
import { makeStyles } from "@material-ui/core/styles";
import Container from "@material-ui/core/Container";

import { Formik, Form } from "formik";
import * as yup from "yup";
import RentalDataService from "./RentalDataService";
import MenuItem from "@material-ui/core/MenuItem";


let RentalSchema = yup.object().shape({
    description: yup.string().required("Required").max(60, "Max 60 characters."),
    rentalDate: yup.date().default(()=> {return new Date()}).max(yup.ref("returnDate"), "Rental date must be before return"),
    returnDate: yup.date().default(()=> {return new Date()}).min(yup.ref("rentalDate"), "Return date must be after rental"),
});

const useStyles = makeStyles(theme => ({
    "@global": {
        body: {
            backgroundColor: theme.palette.common.white
        }
    },
    paper: {
        marginTop: theme.spacing(8),
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main
    },
    form: {
        width: "100%", // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    }
}));


export const AddRental = () => {
    const classes = useStyles();
    const [creatingResponse, setCreatingResponse] = useState(0);
    const [user, setUser] = useState({});
    const [machines, setMachines] = useState([] );

    useEffect(() => {
       getAuthenticatedUser();
       getAllMachines();
    });

    const getAuthenticatedUser = () => {
        RentalDataService.getAuthenticatedUser().then(response => setUser(response.data))
    };

    const getAllMachines = () => {
        RentalDataService.getAllMachines().then(response => setMachines(response.data))
    };


    return(
        <Container component="main">
            <div className={classes.paper}>
                <Typography component="h1" variant="h5">
                    Add rental
                </Typography>
                <Formik
                    initialValues={{
                    user: getAuthenticatedUser(),
                    machine: {},
                    description: "",
                    rentalDate: "",
                    returnDate: "",
                }}
                    validationSchema={RentalSchema}
                    onSubmit={values => {RentalDataService.createRental(values.description, values.rentalDate, values.returnDate, values.machine, values.user).then(response => {setCreatingResponse(response.data)})}}>

                    {({errors, handleChange, touched }) => (
                        <Form className={classes.form}>
                            <TextField
                                error={Boolean(errors.description) && touched.description}
                                name="description"
                                label="Description"
                                onChange={handleChange}
                                helperText=
                                    {errors.description && touched.description
                                    ? errors.description : null}
                            />


                            <TextField
                                error={Boolean(errors.rentalDate) && touched.rentalDate}
                                name="rentalDate"
                                label="Rental Date"
                                type="date"
                                onChange={handleChange}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                helperText=
                                    {errors.rentalDate && touched.rentalDate
                                    ? errors.rentalDate : null}
                            />

                            <TextField
                                error={Boolean(errors.returnDate) && touched.returnDate}
                                name={"returnDate"}
                                label="Return Date"
                                type="date"
                                onChange={handleChange}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                helperText=
                                    {errors.returnDate && touched.returnDate
                                    ? errors.returnDate : null}
                            />


                            <TextField
                                select
                                label="Machine"
                                onChange={handleChange}
                                helperText="Select machine to rent"
                                style={{minWidth:200}}
                            >
                                {machines.map((option) => (
                                    <MenuItem key={option} value={option}>
                                        {option.name}
                                    </MenuItem>
                                ))}
                            </TextField>


                        <Button type="submit" variant="contained" color="primary" style={{marginBottom: 10}} >
                        Create Rental
                        </Button>
                        </Form>
                    )}
                </Formik>
            </div>
        </Container>
    )
};
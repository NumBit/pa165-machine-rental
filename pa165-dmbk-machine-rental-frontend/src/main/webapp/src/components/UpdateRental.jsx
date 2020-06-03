import React, {useEffect, useState} from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";

import { Formik, Form } from "formik";
import * as yup from "yup";
import RentalDataService from "./RentalDataService";
import Alert from "@material-ui/lab/Alert";
import {useHistory} from "react-router";

let RentalUpdateSchema = yup.object().shape({
    id:
        yup.number(),
    description:
        yup.string()
            .required("Required")
            .max(60, "Max 60 characters."),
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
        display: "flex",
        flexDirection: "row",
        alignItems: "center"
    },
    form: {
        width: "100%", // Fix IE 11 issue.
        marginTop: theme.spacing(3)
    },
    submit: {
        height: 40,
        margin: theme.spacing(2, 0, 1),
        marginLeft: 5,
    },
    center: {
        display: "flex",
        alignItems: "center",
        justifyContent: "center"
    }
}));

export const UpdateRental = ({rental})=> {
    const classes = useStyles();
    const history = useHistory();
    const [updatingResponse, setUpdatingResponse] = useState(0);

    const redirectBackToList = (success) => {
        if(success > 0) {
            window.location.reload();
        }
    };

    return(
            <div className={classes.paper}>
                <Formik
                    initialValues={{
                        id: rental.id,
                        description: rental.description,
                        rentalDate: rental.rentalDate,
                        returnDate: rental.returnDate,
                    }}
                    validationSchema={RentalUpdateSchema}
                    onSubmit={values => {
                        RentalDataService.updateRental(values.id, values.description, values.rentalDate, values.returnDate).then(response => {setUpdatingResponse(response.data); redirectBackToList(response.data)})}}
                >

                    {({errors, handleChange, touched, values }) => (
                        <Form className={classes.form} fullWidth>
                                    <TextField
                                        //variant="outlined"
                                        error={Boolean(errors.description) && touched.description}
                                        name="description"
                                        label="Description"
                                        defaultValue={rental.description}
                                        onChange={handleChange}
                                        helperText=
                                            {errors.description && touched.description
                                                ? errors.description : null}
                                    />
                                    <TextField
                                        //variant="outlined"
                                        error={Boolean(errors.rentalDate) && touched.rentalDate}
                                        name="rentalDate"
                                        label="Rental Date"
                                        value={values.rentalDate}
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
                                        //variant="outlined"
                                        error={Boolean(errors.returnDate) && touched.returnDate}
                                        name="returnDate"
                                        label="Return Date"
                                        value={values.returnDate}
                                        type="date"
                                        onChange={handleChange}
                                        InputLabelProps={{
                                            shrink: true,
                                        }}
                                        helperText=
                                            {errors.returnDate && touched.returnDate
                                                ? errors.returnDate : null}
                                    />
                                    <Button
                                        type="submit"
                                        alignItems="center"
                                        variant="contained"
                                        color="primary"
                                        className={classes.submit}
                                    >
                                        OK
                                    </Button>
                                {(updatingResponse === -1) ? <Grid item xs={12}><Alert severity="error">Machine is not available in selected dates!</Alert> </Grid>: null}

                        </Form>
                    )}
                </Formik>
            </div>
    )
};
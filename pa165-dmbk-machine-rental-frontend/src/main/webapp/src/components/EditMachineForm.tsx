import React from 'react';
import TextField from '@material-ui/core/TextField';
import {createStyles, makeStyles, Theme} from '@material-ui/core/styles';
import Button from "@material-ui/core/Button";
import * as yup from "yup";
import {Form, Formik} from "formik";
import {Machine} from "../views/Machines";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            '& .MuiTextField-root': {
                margin: theme.spacing(1),
                width: '25ch',
            },
        },
        paper: {
            display: "flex",
            flexDirection: "row",
            alignItems: "center"
        },
    }),
);

let MachineSchema = yup.object().shape({
    id:
        yup.number(),
    name:
        yup.string()
            .required("Required")
            .max(60, "Max 60 characters."),
    manufacturer:
        yup.string()
            .required("Required")
            .max(60, "Max 60 characters."),
    description:
        yup.string()
            .required("Required")
            .max(500, "Max 500 characters."),
    price:
        yup.number()
            .required("Required")
            .positive("Positive numbers only")
            .integer("Not valid number")
});

type EditMachineProps = {
    machine: Machine,
    ref: any,
}

export default function EditMachineForm(props: EditMachineProps) {
    const classes = useStyles();

    function handleEditSubmit(machine: Machine) {

        fetch('/pa165/rest/machine/update/', {
            method: 'post',
            credentials: 'include',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(machine)
        }).then().catch();
    }

    return (
        <div className={classes.paper}>
            <Formik validationSchema={MachineSchema}
                    initialValues={{
                        id: props.machine.id,
                        name: props.machine.name,
                        manufacturer: props.machine.manufacturer,
                        description: props.machine.description,
                        price: props.machine.price,
                    }}
                    onSubmit={values => {
                        handleEditSubmit(values); props.ref()
                    }}>
                {({errors, handleChange, touched, values}) => (
                    <Form className={classes.root}>
                        <div>
                            <TextField
                                error={Boolean(errors.name) && touched.name}
                                name="name"
                                label="Name"
                                onChange={handleChange}
                                value={values.name}
                                variant="outlined"
                                helperText=
                                    {errors.name && touched.name
                                        ? errors.name : null}
                            />
                            <TextField
                                error={Boolean(errors.manufacturer) && touched.manufacturer}
                                name="manufacturer"
                                label="Manufacturer"
                                onChange={handleChange}
                                value={values.manufacturer}
                                variant="outlined"
                                helperText=
                                    {errors.manufacturer && touched.manufacturer
                                        ? errors.manufacturer : null}
                            />
                            <TextField
                                error={Boolean(errors.description) && touched.description}
                                name="description"
                                label="Description"
                                onChange={handleChange}
                                value={values.description}
                                variant="outlined" helperText=
                                    {errors.description && touched.description
                                        ? errors.description : null}

                            />
                            <TextField
                                error={Boolean(errors.price) && touched.price}
                                name="price"
                                label="Price"
                                type="number"
                                onChange={handleChange}
                                value={values.price}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                variant="outlined"
                                helperText=
                                    {errors.price && touched.price
                                        ? errors.price : null}
                            />
                            <Button
                                color="primary"
                                variant="contained"
                                type="submit"
                                style={{marginTop: "15px"}}
                            >
                                Edit
                            </Button>
                        </div>
                    </Form>)}
            </Formik>
        </div>
    );
}
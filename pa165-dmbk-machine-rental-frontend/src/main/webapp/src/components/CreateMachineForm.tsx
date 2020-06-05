import React from 'react';
import TextField from '@material-ui/core/TextField';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Button from "@material-ui/core/Button";
import * as yup from "yup";
import {Form, Formik} from "formik";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            '& .MuiTextField-root': {
                margin: theme.spacing(1),
                width: '25ch',
            },
        },
    }),
);

let MachineSchema = yup.object().shape({
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

export default function CreateMachineForm({setData}: any) {
    const classes = useStyles();

    function handleSubmit(name: string, description : string, manufacturer: string, price: number) {
        const formData = {
            name: name,
            description: description,
            manufacturer: manufacturer,
            price: price
        };

        const data = new FormData();
        data.append( "json", JSON.stringify(formData) );

        fetch('/pa165/rest/machine/', {
            method: 'post',
            credentials: 'include',
            headers: {'Content-Type':'application/json'},
            body: JSON.stringify(formData)
        }).then(() => refreshAll().then()).catch();
    }

    function refreshAll() {
        return fetch('/pa165/rest/machine/')
            .then(res => res.json())
            .then(res => setData({status: 'loaded', payload: res}))
            .catch(error => setData({status: 'error', error}));
    }

    return (
        <Formik validationSchema={MachineSchema}
                initialValues={{
            name: "",
            manufacturer: "",
            description: "",
            price: 0,}}
                onSubmit={values => {handleSubmit(values.name, values.description, values.manufacturer, values.price)}}>
            {({errors, handleChange, touched }) => (
            <Form className={classes.root}>
            <div>
                <TextField
                    error={Boolean(errors.name) && touched.name}
                    name="name"
                    label="Name"
                    onChange={handleChange}
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
                    defaultValue="0"
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
                    style={{ marginTop: "15px" }}
                >
                    Create
                </Button>
            </div>
            </Form>)}
        </Formik>
    );
}
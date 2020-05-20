import React, {useState} from 'react';
import TextField from '@material-ui/core/TextField';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import Button from "@material-ui/core/Button";

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

export default function CreateMachineForm({setData}: any) {
    const classes = useStyles();
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [manufacturer, setManufacturer] = useState("");
    const [price, setPrice] = useState(0);

    const handleSubmit = (event: any) => {
        event.preventDefault();


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
    };

    function refreshAll() {
        return fetch('/pa165/rest/machine/')
            .then(res => res.json())
            .then(res => setData({status: 'loaded', payload: res}))
            .catch(error => setData({status: 'error', error}));
    }

    const handleNameChange = (event: any) => {
        setName(event.target.value);
    };

    const handleDescriptionChange = (event: any) => {
        setDescription(event.target.value);
    };

    const handleManufacturerChange = (event: any) => {
        setManufacturer(event.target.value);
    };

    const handlePriceChange = (event: any) => {
        setPrice(event.target.value);
    };

    return (
        <form className={classes.root} noValidate autoComplete="off">
            <div>
                <TextField
                    required
                    id="name"
                    label="Name"
                    defaultValue="Name"
                    onChange={handleNameChange}
                    variant="outlined"
                />
                <TextField
                    required
                    id="description"
                    label="Description"
                    defaultValue="Description"
                    onChange={handleDescriptionChange}
                    variant="outlined"
                />
                <TextField
                    required
                    id="manufacturer"
                    label="Manufacturer"
                    defaultValue="Manufacturer"
                    onChange={handleManufacturerChange}
                    variant="outlined"
                />
                <TextField
                    required
                    id="price"
                    label="Price"
                    type="number"
                    onChange={handlePriceChange}
                    defaultValue="1"
                    InputLabelProps={{
                        shrink: true,
                    }}
                    variant="outlined"
                />
                <Button
                    color="primary"
                    variant="contained"
                    type="submit"
                    onClick={handleSubmit}
                    style={{ marginTop: "15px" }}
                >
                    Create
                </Button>
            </div>
        </form>
    );
}
import axios from "axios"

class RentalDataService {
    getAllRentals() {
        return axios.get("http://localhost:8080/rest/rental")
    }
    getAllMachines() {
        return axios.get("http://localhost:8080/rest/machine/")
    }
    getAllCustomers() {
        return axios.get("http://localhost:8080/rest/rental")
    }

    deleteRental(id) {
        return axios.delete("http://localhost:8080/rest/rental/" + id)
    }

    createRental(description, rentalDate, returnDate, machine, user) {
        return axios.post("http://localhost:8080/rest/rental/create" ,
            {description: description, rentalDate: rentalDate, returnDate: returnDate, machine: machine, customer: user})
    }
}
export default new RentalDataService()
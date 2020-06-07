import axios from "axios"

class RentalDataService {
    getAllRentals() {
        return axios.get("http://localhost:8080/pa165/rest/rental")
    }
    getAllRentalsByCustomerId(id) {
        return axios.get("http://localhost:8080/pa165/rest/rental/customer/" + id)
    }
    getAllMachines() {
        return axios.get("http://localhost:8080/pa165/rest/machine/")
    }
    getMachineById(id) {
        return axios.get("http://localhost:8080/pa165/rest/machine/" + id)
    }

    getAllCustomers() {
        return axios.get("http://localhost:8080/pa165/rest/admin/allCustomers")
    }
    checkMachineAvailability(id, rentalDate, returnDate) {
        return axios.post("http://localhost:8080/pa165/rest/rental/machineAvailability",
            {machineId: id, rentalDate: rentalDate, returnDate: returnDate})
    }

    deleteRental(id) {
        return axios.delete("http://localhost:8080/pa165/rest/rental/" + id)
    }

    deleteCustomer(id) {
        return axios.delete("http://localhost:8080/pa165/rest/admin/user/" + id)
    }

    createRental(description, rentalDate, returnDate, machine, user) {
        return axios.post("http://localhost:8080/pa165/rest/rental/create" ,
            {description: description, rentalDate: rentalDate, returnDate: returnDate, machine: machine, customer: user})
    }

    getAuthenticatedUser() {
        return axios.get("http://localhost:8080/pa165/rest/user/authenticated")
    }

    updateRental(id, description, rentalDate, returnDate) {
        return axios.post("http://localhost:8080/pa165/rest/rental/update",
            {id: id, description: description, rentalDate: rentalDate, returnDate: returnDate})
    }

}
export default new RentalDataService()
import axios from "axios"

class RentalDataSercvice {
    getAllRentals() {
        return axios.get("http://localhost:8080/rest/rental")
    }

    deleteRental(id) {
        return axios.delete("http://localhost:8080/rest/rental/" + id)
    }
}
export default new RentalDataSercvice()
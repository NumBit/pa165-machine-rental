package cz.muni.fi.pa165.dmbk.machinerental.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.RentalFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalUpdateDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/***
 * Rental REST controller
 *
 * @author Peter Baltazarovič
 */
@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "${spring.custom.rest-api.contextPath}")
public class RentalRestController {

    @Autowired private RentalFacade rentalFacade;


    @Getter
    @Setter
    @EqualsAndHashCode
    @JsonDeserialize(builder = Dates.Builder.class)
    @Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
    static class  Dates {
        private final LocalDate rentalDate;
        private final LocalDate returnDate;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @JsonDeserialize(builder = MachineAvailability.Builder.class)
    @Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
    static class  MachineAvailability {
        private final Long machineId;
        private final LocalDate rentalDate;
        private final LocalDate returnDate;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @JsonDeserialize(builder = ReturnDate.Builder.class)
    @Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
    static class  ReturnDate {
        private final Long id;
        private final LocalDate returnDate;
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @JsonDeserialize(builder = RentalDate.Builder.class)
    @Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
    static class  RentalDate {
        private final Long id;
        private final LocalDate rentalDate;
    }

    /** Find rental by id
     * @param id Long
     * @return rentalDto
     */
    @GetMapping("${spring.rest-api.rentalPath}/{id}")
    public ResponseEntity<RentalDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(rentalFacade.findById(id));
    }


    /** Delete all rentals
     * @return ok response
     */
    @DeleteMapping("${spring.rest-api.rentalPath}/rental")
    public ResponseEntity<?> deleteAllRentals() {
        rentalFacade.deleteAll();
        return ResponseEntity.ok().build();
    }

    /** Delete rental by id
     * @param id Long
     * @return ok response
     */
    @DeleteMapping("${spring.rest-api.rentalPath}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        rentalFacade.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /** Find all rentals
     * @return list of rentals
     */
    @GetMapping("${spring.rest-api.rentalPath}")
    public ResponseEntity<List<RentalDto>> findAll(){
        return ResponseEntity.ok(rentalFacade.findAll());
    }

    /** Create rental
     * @param rental RentalDto
     * @return Id of created entity -1 if machine is not available in selected dates -2 if machine does not exists
     */
    @PostMapping("${spring.rest-api.rentalPath}/create")
    public ResponseEntity<Long> create(@RequestBody RentalCreateDto rental){
        return ResponseEntity.ok(rentalFacade.createRental(rental));
    }

    /** Update rental
     * @param rental updated rentalDto
     * @return Id of updated entity -1 if machine is not available in selected dates -2 if rental does not exists
     */
    @PostMapping("${spring.rest-api.rentalPath}/update")
    public ResponseEntity<Long> update(@RequestBody RentalUpdateDto rental){
        return ResponseEntity.ok(rentalFacade.updateRental(rental));
    }

    /** Find rentals with matching machine id
     * @param id id of machine
     * @return list of matching rentals
     */
    @GetMapping("${spring.rest-api.rentalPath}/machine/{id}")
    public ResponseEntity<List<RentalDto>> findByMachineId(@PathVariable Long id) {
        return ResponseEntity.ok(rentalFacade.findAllByMachineId(id));
    }

    /** Find rentals with matching customer id
     * @param id id of customer
     * @return list of matching rentals
     */
    @GetMapping("${spring.rest-api.rentalPath}/customer/{id}")
    public ResponseEntity<List<RentalDto>> findByCustomerId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(rentalFacade.findAllByCustomerId(id));
    }

    /** Find rentals with rental date after specified date
     * @param rentalDate specified date
     * @return list of matching rentals
     */
    @PostMapping("${spring.rest-api.rentalPath}/rentalDateAfter")
    public ResponseEntity<List<RentalDto>> findAllByRentalDateAfter(@RequestBody LocalDate rentalDate) {
        return ResponseEntity.ok(rentalFacade.findAllByRentalDateAfter(rentalDate));
    }

    /** Find rentals with rental date before specified date
     * @param rentalDate specified date
     * @return list of matching rentals
     */
    @PostMapping("${spring.rest-api.rentalPath}/rentalDateBefore")
    public ResponseEntity<List<RentalDto>> findAllByRentalDateBefore(@RequestBody LocalDate rentalDate) {
        return ResponseEntity.ok(rentalFacade.findAllByRentalDateBefore(rentalDate));
    }

    /** Find rentals with rental date between specified dates
     * @param dates specified dates
     * @return list of matching rentals
     */
    @PostMapping("${spring.rest-api.rentalPath}/rentalDateBetween")
    public ResponseEntity<List<RentalDto>> findAllByRentalDateBetween(@RequestBody Dates dates) {
        return ResponseEntity.ok(rentalFacade.findAllByRentalDateBetween(dates.rentalDate, dates.returnDate));
    }

    /** Find rentals with return date after specified date
     * @param returnDate specified date
     * @return list of matching rentals
     */
    @PostMapping("${spring.rest-api.rentalPath}/returnDateAfter")
    public ResponseEntity<List<RentalDto>> findAllByReturnDateAfter(@RequestBody LocalDate returnDate) {
        return ResponseEntity.ok(rentalFacade.findAllByReturnDateAfter(returnDate));
    }

    /** Find rentals with return date before specified date
     * @param returnDate specified date
     * @return list of matching rentals
     */
    @PostMapping("${spring.rest-api.rentalPath}/returnDateBefore")
    public ResponseEntity<List<RentalDto>> findAllByReturnDateBefore(@RequestBody LocalDate returnDate) {
        return ResponseEntity.ok(rentalFacade.findAllByReturnDateBefore(returnDate));
    }

    /** Find rentals with return date between specified date
     * @param dates specified dates
     * @return list of matching rentals
     */
    @PostMapping("${spring.rest-api.rentalPath}/returnDateBetween")
    public ResponseEntity<List<RentalDto>> findAllByReturnDateBetween(@RequestBody Dates dates) {
        return ResponseEntity.ok(rentalFacade.findAllByReturnDateBetween(dates.rentalDate, dates.returnDate));
    }

    /** Checks if selected machine is available for rent in specified dates
     * @param machineAvailability id of machine and dates to check
     * @return true if machine is available false if is not available 404 if machine does not exists
     */
    @PostMapping("${spring.rest-api.rentalPath}/machineAvailability")
    public ResponseEntity<Boolean> checkMachineAvailabilityForRent(@RequestBody MachineAvailability machineAvailability){
        return ResponseEntity.of(rentalFacade.checkMachineAvailabilityForRent(machineAvailability.machineId, machineAvailability.rentalDate, machineAvailability.returnDate));
    }

    /** Set rental date to machine
     * @param rentalDate if of machine and new rental date
     * @return ok response
     */
    @PostMapping("${spring.rest-api.rentalPath}/setRentalDate")
    public ResponseEntity<?> setRentalDate(@RequestBody RentalDate rentalDate){
        rentalFacade.setRentalDate(rentalDate.id, rentalDate.rentalDate);
        return rentalFacade.findById(rentalDate.id).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    /** Set return date to machine
     * @param returnDate if of machine and new return date
     * @return ok response
     */
    @PostMapping("${spring.rest-api.rentalPath}/setReturnDate")
    public ResponseEntity<?> setReturnDate(@RequestBody ReturnDate returnDate){
        rentalFacade.setReturnDate(returnDate.id, returnDate.returnDate);
        return rentalFacade.findById(returnDate.id).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

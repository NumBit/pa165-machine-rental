package cz.muni.fi.pa165.dmbk.machinerental.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.RentalFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalDto;
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

@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "${spring.custom.rest-api.contextPath}")
public class RentalRestController {

    @Autowired private RentalFacade rentalFacade;

    @GetMapping("${spring.rest-api.rentalPath}/{id}")
    public ResponseEntity<RentalDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(rentalFacade.findById(id));
    }

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

    @DeleteMapping("${spring.rest-api.rentalPath}/rental")
    public ResponseEntity<?> deleteAllRentals() {
        rentalFacade.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("${spring.rest-api.rentalPath}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        rentalFacade.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("${spring.rest-api.rentalPath}")
    public ResponseEntity<List<RentalDto>> findAll(){
        return ResponseEntity.ok(rentalFacade.findAll());
    }

    @PostMapping("${spring.rest-api.rentalPath}/create")
    public ResponseEntity<Long> create(@RequestBody RentalCreateDto rental){
        return ResponseEntity.ok(rentalFacade.createRental(rental));
    }

    @GetMapping("${spring.rest-api.rentalPath}/machine/{id}")
    public ResponseEntity<List<RentalDto>> findByMachineId(@PathVariable Long id) {
        return ResponseEntity.ok(rentalFacade.findAllByMachineId(id));
    }

    @GetMapping("${spring.rest-api.rentalPath}/customer/{id}")
    public ResponseEntity<List<RentalDto>> findByCustomerId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(rentalFacade.findAllByCustomerId(id));
    }

    @PostMapping("${spring.rest-api.rentalPath}/rentalDateAfter")
    public ResponseEntity<List<RentalDto>> findAllByRentalDateAfter(@RequestBody LocalDate rentalDate) {
        return ResponseEntity.ok(rentalFacade.findAllByRentalDateAfter(rentalDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/rentalDateBefore")
    public ResponseEntity<List<RentalDto>> findAllByRentalDateBefore(@RequestBody LocalDate rentalDate) {
        return ResponseEntity.ok(rentalFacade.findAllByRentalDateBefore(rentalDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/rentalDateBetween")
    public ResponseEntity<List<RentalDto>> findAllByRentalDateBetween(@RequestBody Dates dates) {
        return ResponseEntity.ok(rentalFacade.findAllByRentalDateBetween(dates.rentalDate, dates.returnDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/returnDateAfter")
    public ResponseEntity<List<RentalDto>> findAllByReturnDateAfter(@RequestBody LocalDate returnDate) {
        return ResponseEntity.ok(rentalFacade.findAllByReturnDateAfter(returnDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/returnDateBefore")
    public ResponseEntity<List<RentalDto>> findAllByReturnDateBefore(@RequestBody LocalDate returnDate) {
        return ResponseEntity.ok(rentalFacade.findAllByReturnDateBefore(returnDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/returnDateBetween")
    public ResponseEntity<List<RentalDto>> findAllByReturnDateBetween(@RequestBody Dates dates) {
        return ResponseEntity.ok(rentalFacade.findAllByReturnDateBetween(dates.rentalDate, dates.returnDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/machineAvailability")
    public ResponseEntity<Boolean> checkMachineAvailabilityForRent(@RequestBody MachineAvailability machineAvailability){
        return ResponseEntity.of(rentalFacade.checkMachineAvailabilityForRent(machineAvailability.machineId, machineAvailability.rentalDate, machineAvailability.returnDate));
    }

    @PostMapping("${spring.rest-api.rentalPath}/setRentalDate")
    public ResponseEntity<?> setRentalDate(@RequestBody RentalDate rentalDate){
        rentalFacade.setRentalDate(rentalDate.id, rentalDate.rentalDate);
        return rentalFacade.findById(rentalDate.id).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("${spring.rest-api.rentalPath}/setReturnDate")
    public ResponseEntity<?> setReturnDate(@RequestBody ReturnDate returnDate){
        rentalFacade.setReturnDate(returnDate.id, returnDate.returnDate);
        return rentalFacade.findById(returnDate.id).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}

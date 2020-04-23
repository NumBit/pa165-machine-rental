package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental;

import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Peter Baltazarovič
 */
@Component
public interface RentalFacade {
    Long createRental(RentalCreateDto rental);
    Optional<RentalDto> findById(Long id);
    List<RentalDto> findAll();
    List<RentalDto> findAllByMachineId(Long machineId);
    List<RentalDto> findAllByCustomerId(Long customerId);
    List<RentalDto> findAllByRentalDateAfter(LocalDate rentalDate);
    List<RentalDto> findAllByRentalDateBefore(LocalDate rentalDate);
    List<RentalDto> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd);
    List<RentalDto> findAllByReturnDateAfter(LocalDate returnDate);
    List<RentalDto> findAllByReturnDateBefore(LocalDate returnDate);
    List<RentalDto> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd);
    void deleteById(Long id);
    void deleteAll();
    void setRentalDate(Long id, LocalDate rentalDate);
    void setReturnDate(Long id, LocalDate returnDate);
    Optional<Boolean> checkMachineAvailabilityForRent(Long machineId, LocalDate rentalDate, LocalDate returnDate);

}

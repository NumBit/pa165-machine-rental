package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Peter Baltazarovič
 */
@Service
public interface RentalService {
    Long createRental(Rental rental);
    void updateRental(Rental rental);
    Optional<Rental> findById(Long id);
    List<Rental> findAll();
    List<Rental> findAllByMachineId(Long machineId);
    List<Rental> findAllByCustomerId(Long customerId);
    List<Rental> findAllByRentalDateAfter(LocalDate rentalDate);
    List<Rental> findAllByRentalDateBefore(LocalDate rentalDate);
    List<Rental> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd);
    List<Rental> findAllByReturnDateAfter(LocalDate returnDate);
    List<Rental> findAllByReturnDateBefore(LocalDate returnDate);
    List<Rental> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd);
    void deleteById(Long id);
    void deleteAll();
    void setRentalDate(Long id, LocalDate rentalDate);
    void setReturnDate(Long id, LocalDate returnDate);
    Optional<Boolean> checkAvailabilityForRent(Long machineId, LocalDate rentalDate, LocalDate returnDate);
}

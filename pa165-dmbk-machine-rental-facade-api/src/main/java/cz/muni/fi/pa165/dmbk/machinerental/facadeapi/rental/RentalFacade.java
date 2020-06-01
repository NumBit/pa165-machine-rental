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
    /**
     * Store rental entity into db
     * @param rental entity to create
     * @return Id of created entity -1 if machine is not available in selected dates -2 if machine does not exists
     */
    Long createRental(RentalCreateDto rental);

    /**
     * Find rentals by Id
     * @param id id of rental
     * @return optional of found rental
     */
    Optional<RentalDto> findById(Long id);

    /**
     * Find all rentals
     * @return list of all rentals
     */
    List<RentalDto> findAll();

    /**
     * Find rentals with matching machine id
     * @param machineId id of machine
     * @return list of matching rentals
     */
    List<RentalDto> findAllByMachineId(Long machineId);

    /**
     * Find rentals with matching customer id
     * @param customerId id of customer
     * @return list of matching rentals
     */
    List<RentalDto> findAllByCustomerId(Long customerId);

    /**
     * Find rentals with rental date after specified date
     * @param rentalDate specified date
     * @return list of matching rentals
     */
    List<RentalDto> findAllByRentalDateAfter(LocalDate rentalDate);

    /**
     * Find rentals with rental date before specified date
     * @param rentalDate specified date
     * @return list of matching rentals
     */
    List<RentalDto> findAllByRentalDateBefore(LocalDate rentalDate);

    /**
     * Find rentals with rental date between boundaries
     * @param rentalDateStart starting date
     * @param rentalDateEnd ending date
     * @return list of matching rentals
     */
    List<RentalDto> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd);

    /**
     * Find rentals with return date after specified date
     * @param returnDate specified date
     * @return list of matching rentals
     */
    List<RentalDto> findAllByReturnDateAfter(LocalDate returnDate);

    /**
     * Find rentals with return date before specified date
     * @param returnDate specified date
     * @return list of matching rentals
     */
    List<RentalDto> findAllByReturnDateBefore(LocalDate returnDate);

    /**
     * Find rentals with return date between boundaries
     * @param returnDateStart starting date
     * @param returnDateEnd ending date
     * @return list of matching rentals
     */
    List<RentalDto> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd);

    /** Delete selected rental
     * @param id id of rental to be deleted
     */
    void deleteById(Long id);

    /**
     *Delete all rentals
     */
    void deleteAll();

    /**
     * Set the rental date of specified rental
     * @param id id of rental to be updated
     * @param rentalDate new rental date
     */
    void setRentalDate(Long id, LocalDate rentalDate);

    /**
     * Set the return date of specified rental
     * @param id id of rental to be updated
     * @param returnDate new return date
     */
    void setReturnDate(Long id, LocalDate returnDate);

    /**
     * Checks if selected machine is available for rent in specified dates
     * @param machineId id of machine
     * @param rentalDate starting date of rental
     * @param returnDate returning date of rental
     * @return true if machine is available false if is not available optional.empty if machine does not exists
     */
    Optional<Boolean> checkMachineAvailabilityForRent(Long machineId, LocalDate rentalDate, LocalDate returnDate);
}

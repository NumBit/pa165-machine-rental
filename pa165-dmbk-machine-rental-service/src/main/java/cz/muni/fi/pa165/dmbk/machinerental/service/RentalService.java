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
    /**
     * Store rental entity into db
     * @param rental entity to create
     * @return Id of created entity
     */
    Long createRental(Rental rental);

    /**
     * Update specified rental
     * @param rental updated rental
     */
    void updateRental(Rental rental);

    /**
     * Find rentals by Id
     * @param id id of rental
     * @return optional of found rental
     */
    Optional<Rental> findById(Long id);

    /**
     * Find all rentals
     * @return list of all rentals
     */
    List<Rental> findAll();

    /**
     * Find rentals with matching machine id
     * @param machineId id of machine
     * @return list of matching rentals
     */
    List<Rental> findAllByMachineId(Long machineId);

    /**
     * Find rentals with matching customer id
     * @param customerId id of customer
     * @return list of matching rentals
     */
    List<Rental> findAllByCustomerId(Long customerId);

    /**
     * Find rentals with rental date after specified date
     * @param rentalDate specified date
     * @return list of matching rentals
     */
    List<Rental> findAllByRentalDateAfter(LocalDate rentalDate);

    /**
     * Find rentals with rental date before specified date
     * @param rentalDate specified date
     * @return list of matching rentals
     */
    List<Rental> findAllByRentalDateBefore(LocalDate rentalDate);

    /**
     * Find rentals with rental date between boundaries
     * @param rentalDateStart starting date
     * @param rentalDateEnd ending date
     * @return list of matching rentals
     */
    List<Rental> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd);

    /**
     * Find rentals with return date after specified date
     * @param returnDate specified date
     * @return list of matching rentals
     */
    List<Rental> findAllByReturnDateAfter(LocalDate returnDate);

    /**
     * Find rentals with return date before specified date
     * @param returnDate specified date
     * @return list of matching rentals
     */
    List<Rental> findAllByReturnDateBefore(LocalDate returnDate);

    /**
     * Find rentals with return date between boundaries
     * @param returnDateStart starting date
     * @param returnDateEnd ending date
     * @return list of matching rentals
     */
    List<Rental> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd);

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
    Optional<Boolean> checkAvailabilityForRent(Long machineId, LocalDate rentalDate, LocalDate returnDate);
}

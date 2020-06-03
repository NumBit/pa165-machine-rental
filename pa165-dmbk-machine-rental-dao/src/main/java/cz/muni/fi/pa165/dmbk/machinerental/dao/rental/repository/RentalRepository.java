package cz.muni.fi.pa165.dmbk.machinerental.dao.rental.repository;

import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Rental DAO JPA operations.
 *
 * @author Peter Baltazarovič
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    /**
     * Find rentals with matching customer id
     * @param customerId id of customer
     * @return list of matching rentals
     */
    List<Rental> findAllByCustomerId(Long customerId);

    /**
     * Find rentals with matching machine id
     * @param machineId id of machine
     * @return list of matching rentals
     */
    List<Rental> findAllByMachineId(Long machineId);

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
     * Find rentals with selected machine id and rental dates between boundaries
     * @param rentalDateStart lower rental date boundary
     * @param rentalDateEnd upper rental date boundary
     * @param machineId id of selected machine
     * @return list of matching rentals
     */
    List<Rental> findAllByRentalDateBetweenAndMachineId(LocalDate rentalDateStart, LocalDate rentalDateEnd, Long machineId);

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

    /**
     * Find rentals with selected machine id and return dates between boundaries
     * @param returnDateStart lower return date boundary
     * @param returnDateEnd upper return date boundary
     * @param machineId id of selected machine
     * @return list of matching rentals
     */
    List<Rental> findAllByReturnDateBetweenAndMachineId(LocalDate returnDateStart, LocalDate returnDateEnd, Long machineId);
}

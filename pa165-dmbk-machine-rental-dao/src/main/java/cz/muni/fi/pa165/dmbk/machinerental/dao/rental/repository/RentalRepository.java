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
    List<Rental> findAllByCustomerId(Long customerId);
    List<Rental> findAllByMachineId(Long machineId);
    List<Rental> findAllByRentalDateAfter(LocalDate rentalDate);
    List<Rental> findAllByRentalDateBefore(LocalDate rentalDate);
    List<Rental> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd);
    List<Rental> findAllByReturnDateAfter(LocalDate returnDate);
    List<Rental> findAllByReturnDateBefore(LocalDate returnDate);
    List<Rental> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd);
}

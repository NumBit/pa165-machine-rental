package cz.muni.fi.pa165.dmbk.machinerental.service.rental;

import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.repository.RentalRepository;
import cz.muni.fi.pa165.dmbk.machinerental.service.CustomDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * @author Peter Baltazarovič
 */
@Service
public class RentalServiceImpl implements RentalService{

    @Autowired
    private RentalRepository rentalRepository;

    @Override
    public Long createRental(Rental rental) {
        return exceptionCatcher(() -> rentalRepository.saveAndFlush(rental)).getId();
    }

    @Override
    public void updateRental(Rental rental) {
        exceptionCatcher(() -> rentalRepository.save(rental));
    }

    @Override
    public Optional<Rental> findById(Long id) {
        return rentalRepository.findById(id);
    }

    @Override
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Override
    public List<Rental> findAllByMachineId(Long machineId) {
        return rentalRepository.findAllByMachineId(machineId);
    }

    @Override
    public List<Rental> findAllByCustomerId(Long customerId) {
        return rentalRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Rental> findAllByRentalDateAfter(LocalDate rentalDate) {
        return rentalRepository.findAllByRentalDateAfter(rentalDate);
    }

    @Override
    public List<Rental> findAllByRentalDateBefore(LocalDate rentalDate) {
        return rentalRepository.findAllByRentalDateBefore(rentalDate);
    }

    @Override
    public List<Rental> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd) {
        return rentalRepository.findAllByRentalDateBetween(rentalDateStart, rentalDateEnd);
    }

    @Override
    public List<Rental> findAllByReturnDateAfter(LocalDate returnDate) {
        return rentalRepository.findAllByReturnDateAfter(returnDate);
    }

    @Override
    public List<Rental> findAllByReturnDateBefore(LocalDate returnDate) {
        return rentalRepository.findAllByReturnDateBefore(returnDate);
    }

    @Override
    public List<Rental> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd) {
        return rentalRepository.findAllByReturnDateBetween(returnDateStart, returnDateEnd);
    }

    @Override
    public void deleteById(Long id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        rentalRepository.deleteAll();
    }

    @Override
    public void setRentalDate(Long id, LocalDate rentalDate) {
        var rental = rentalRepository.findById(id);
        if (rental.isPresent()){
            var obtainedRental = rental.get();
            obtainedRental.setRentalDate(rentalDate);
            exceptionCatcher(() -> rentalRepository.saveAndFlush(obtainedRental));
        }
    }

    @Override
    public void setReturnDate(Long id, LocalDate returnDate) {
        var rental = rentalRepository.findById(id);
        if (rental.isPresent()){
            var obtainedRental = rental.get();
            obtainedRental.setReturnDate(returnDate);
            exceptionCatcher(() -> rentalRepository.saveAndFlush(obtainedRental));
        }
    }

    @Override
    public Optional<Boolean> checkAvailabilityForRent(Long machineId, LocalDate rentalDate, LocalDate returnDate) {
        if (rentalRepository.findAllByMachineId(machineId).isEmpty()){
            return Optional.empty();
        } else {
             return Optional.of(
                     rentalRepository.findAllByRentalDateBetweenAndMachineId(rentalDate, returnDate, machineId).isEmpty() &&
                             rentalRepository.findAllByReturnDateBetweenAndMachineId(rentalDate,returnDate, machineId).isEmpty());
        }
    }

    private static <T> T exceptionCatcher(Callable<T> thrower) {
        try { return thrower.call(); }
        catch (Exception exception) {
            throw new CustomDataAccessException(exception.getMessage(), exception);
        }
    }
}

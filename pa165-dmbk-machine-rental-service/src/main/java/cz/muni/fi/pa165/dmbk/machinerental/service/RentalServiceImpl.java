package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.MachineRepository;
import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Rental service implementation {@link RentalService}
 *
 * @author Peter Baltazarovič
 */
@Service
public class RentalServiceImpl implements RentalService{

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private MachineRepository machineRepository;

    @Override
    public Long createRental(Rental rental) {
        var availabilityOfMachine = checkAvailabilityForRent(rental.getMachine().getId(), rental.getRentalDate(), rental.getReturnDate());
        if(availabilityOfMachine.isEmpty()) {
            return -2L;
        } else if(!availabilityOfMachine.get()) {
            return -1L;
        } else {
            return exceptionCatcher(() -> rentalRepository.saveAndFlush(rental)).getId();
        }
    }

    @Override
    public Long updateRental(Rental rental) {
        var originalRental = rentalRepository.findById(rental.getId());
        if(originalRental.isEmpty()) {
            return -2L;
        }
        rental.setMachine(originalRental.get().getMachine());
        rental.setCustomer(originalRental.get().getCustomer());
        var availabilityOfMachine = checkAvailabilityForRent(rental.getMachine().getId(), rental.getRentalDate(), rental.getReturnDate());

        if(!availabilityOfMachine.get()) {
            var rentalSelfBlock = true;
            var returnSelfBlock = true;

            var rentalDateRentals = rentalRepository.findAllByRentalDateBetweenAndMachineId(rental.getRentalDate(), rental.getReturnDate(), rental.getMachine().getId());
            var returnDateRentals = rentalRepository.findAllByReturnDateBetweenAndMachineId(rental.getRentalDate(), rental.getReturnDate(), rental.getMachine().getId());

            if(rentalDateRentals.size() > 1) {
                rentalSelfBlock = false;
            } else if (rentalDateRentals.size() == 1) {
                rentalSelfBlock = rentalDateRentals.get(0).getId().equals(rental.getId());
            }
            if(returnDateRentals.size() > 1) {
                returnSelfBlock = false;
            } else if (returnDateRentals.size() == 1) {
                returnSelfBlock = returnDateRentals.get(0).getId().equals(rental.getId());
            }
            if(!rentalSelfBlock || !returnSelfBlock) {
                return -1L;
            }
        }

        return exceptionCatcher(() -> rentalRepository.save(rental)).getId();
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
        if (machineRepository.findById(machineId).isEmpty()){
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

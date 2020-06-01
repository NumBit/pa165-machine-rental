package cz.muni.fi.pa165.dmbk.machinerental.service.rental;

import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.RentalFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingService;
import cz.muni.fi.pa165.dmbk.machinerental.service.CustomDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Peter Baltazarovič
 */
@Component
@Transactional(rollbackFor = CustomDataAccessException.class)
public class RentalFacadeImpl implements RentalFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private RentalService rentalService;

    @Override
    public Long createRental(RentalCreateDto rental) {
        return rentalService.createRental(beanMappingService.mapTo(rental, Rental.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RentalDto> findById(Long id) {
        var rental = rentalService.findById(id);
        return rental.map(value -> beanMappingService.mapTo(value, RentalDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAll() {
        return beanMappingService.mapTo(rentalService.findAll(), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByMachineId(Long machineId) {
        return beanMappingService.mapTo(rentalService.findAllByMachineId(machineId), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByCustomerId(Long customerId) {
        return beanMappingService.mapTo(rentalService.findAllByCustomerId(customerId), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByRentalDateAfter(LocalDate rentalDate) {
        return beanMappingService.mapTo(rentalService.findAllByRentalDateAfter(rentalDate), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByRentalDateBefore(LocalDate rentalDate) {
        return beanMappingService.mapTo(rentalService.findAllByRentalDateBefore(rentalDate), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByRentalDateBetween(LocalDate rentalDateStart, LocalDate rentalDateEnd) {
        return beanMappingService.mapTo(rentalService.findAllByRentalDateBetween(rentalDateStart, rentalDateEnd), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByReturnDateAfter(LocalDate returnDate) {
        return beanMappingService.mapTo(rentalService.findAllByReturnDateAfter(returnDate), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByReturnDateBefore(LocalDate returnDate) {
        return beanMappingService.mapTo(rentalService.findAllByReturnDateBefore(returnDate), RentalDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> findAllByReturnDateBetween(LocalDate returnDateStart, LocalDate returnDateEnd) {
        return beanMappingService.mapTo(rentalService.findAllByReturnDateBetween(returnDateStart, returnDateEnd), RentalDto.class);
    }

    @Override
    public void deleteById(Long id) {
        rentalService.deleteById(id);
    }

    @Override
    public void deleteAll() {
        rentalService.deleteAll();
    }

    @Override
    public void setRentalDate(Long id, LocalDate rentalDate) {
        rentalService.setRentalDate(id, rentalDate);
    }

    @Override
    public void setReturnDate(Long id, LocalDate returnDate) {
        rentalService.setReturnDate(id, returnDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boolean> checkMachineAvailabilityForRent(Long machineId, LocalDate rentalDate, LocalDate returnDate) {
        return rentalService.checkAvailabilityForRent(machineId, rentalDate, returnDate);
    }
}

package cz.muni.fi.pa165.dmbk.machinerental.service.rental;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.MachineRepository;
import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.repository.RentalRepository;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.service.RentalService;
import cz.muni.fi.pa165.dmbk.machinerental.service.RentalServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Lukas Krekan
 * */
@RunWith(SpringRunner.class)
public class RentalServiceTest {

    @MockBean private RentalRepository rentalRepository;
    @MockBean private MachineRepository machineRepository;
    @Autowired private RentalService rentalService;

    private Rental rental;

    @TestConfiguration
    static class RentalServiceTestConfiguration {
        @Bean public RentalService rentalService() {
            return new RentalServiceImpl();
        }
    }

    @Before
    public void setUp() {
        rental = getRentalEntity(1L);
    }

    @Test
    public void createRental(){
        when(rentalRepository.saveAndFlush(any(Rental.class)))
                .thenReturn(rental);
        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(getMachineEntity(1L)));
        var rentalId = rentalService.createRental(rental);
        Assert.assertNotNull(rentalId);
        Assert.assertEquals(rentalId, rental.getId());
    }

    @Test
    public void findById() {
        when(rentalRepository.findById(any(Long.class)))
                .thenReturn(Optional.ofNullable(rental));
        var foundRental = rentalService.findById(1L);
        Assert.assertTrue(foundRental.isPresent());
        Assert.assertEquals(rental, foundRental.get());
    }

    @Test
    public void findAll() {
        when(rentalRepository.findAll())
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAll();
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByMachineId() {
        when(rentalRepository.findAllByMachineId(1L))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByMachineId(1L);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByCustomerId() {
        when(rentalRepository.findAllByCustomerId(1L))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByCustomerId(1L);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByRentalDateBefore() {
        LocalDate rentalDate = LocalDate.of(2020, 4, 20);
        when(rentalRepository.findAllByRentalDateBefore(rentalDate))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByRentalDateBefore(rentalDate);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByRentalDateAfter() {
        LocalDate rentalDate = LocalDate.of(2020, 4, 20);
        when(rentalRepository.findAllByRentalDateAfter(rentalDate))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByRentalDateAfter(rentalDate);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByRentalDateBetween() {
        LocalDate rentalDateFrom = LocalDate.of(2020, 4, 20);
        LocalDate rentalDateTo = LocalDate.of(2020, 4, 22);
        when(rentalRepository.findAllByRentalDateBetween(rentalDateFrom, rentalDateTo))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByRentalDateBetween(rentalDateFrom, rentalDateTo);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByReturnDateBefore() {
        LocalDate returnDate = LocalDate.of(2020, 4, 20);
        when(rentalRepository.findAllByReturnDateBefore(returnDate))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByReturnDateBefore(returnDate);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByReturnDateAfter() {
        LocalDate returnDate = LocalDate.of(2020, 4, 20);
        when(rentalRepository.findAllByReturnDateAfter(returnDate))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByReturnDateAfter(returnDate);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void findAllByReturnDateBetween() {
        LocalDate returnDateFrom = LocalDate.of(2020, 4, 20);
        LocalDate returnDateTo = LocalDate.of(2020, 4, 22);
        when(rentalRepository.findAllByReturnDateBetween(returnDateFrom, returnDateTo))
                .thenReturn(List.of(rental));
        var foundRental = rentalService.findAllByReturnDateBetween(returnDateFrom, returnDateTo);
        Assert.assertEquals(1L, foundRental.size());
    }

    @Test
    public void deleteAll() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.ofNullable(rental));
        var foundRental = rentalService.findById(1L);
        Assert.assertTrue(foundRental.isPresent());
        Assert.assertEquals(rental, foundRental.get());
        rentalService.deleteAll();
        when(rentalRepository.findById(1L)).thenReturn(Optional.empty());
        var found = rentalService.findById(1L);
        Assert.assertTrue(found.isEmpty());
    }

    @Test
    public void deleteById() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.ofNullable(rental));
        var foundRental = rentalService.findById(1L);
        Assert.assertTrue(foundRental.isPresent());
        Assert.assertEquals(rental, foundRental.get());
        rentalService.deleteById(1L);
        when(rentalRepository.findById(1L)).thenReturn(Optional.empty());
        var found = rentalService.findById(1L);
        Assert.assertTrue(found.isEmpty());
    }

    @Test
    public void checkAvailabilityForRent() {
        LocalDate dateFrom = LocalDate.of(2020, 4, 20);
        LocalDate dateTo = LocalDate.of(2020, 4, 22);
        when(rentalRepository.findAllByRentalDateBetweenAndMachineId(any(LocalDate.class), any(LocalDate.class), any(Long.class)))
                .thenReturn(List.of());
        when(rentalRepository.findAllByReturnDateBetweenAndMachineId(any(LocalDate.class), any(LocalDate.class), any(Long.class)))
                .thenReturn(List.of());

        when(rentalRepository.findAllByMachineId(2L)).thenReturn(List.of());
        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(getMachineEntity(1L)));
        when(rentalRepository.findAllByMachineId(1L)).thenReturn(List.of(rental));
        var foundEmpty = rentalService.checkAvailabilityForRent(2L, dateFrom, dateTo);
        Assert.assertTrue(foundEmpty.isEmpty());

        var foundRental = rentalService.checkAvailabilityForRent(1L, dateFrom, dateTo);
        Assert.assertTrue(foundRental.isPresent());
        Assert.assertTrue(foundRental.get());
    }

    private Rental getRentalEntity(Long id){
        Rental rental = new Rental();
        rental.setId(id);
        rental.setRentalDate(LocalDate.of(2020, 4, 20));
        rental.setReturnDate(LocalDate.of(2020, 4, 21));
        rental.setCustomer(getCustomerEntity(1L));
        rental.setMachine(getMachineEntity(1L));
        return rental;
    }

    private Customer getCustomerEntity(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setLogin("CustomerX");
        customer.setEmail("custom@mer.com");
        customer.setLegalForm(LegalForm.INDIVIDUAL);
        customer.setPasswordHash("xyz6789xyz");
        return customer;
    }

    private Machine getMachineEntity(Long id) {
        Machine machine = new Machine();
        machine.setId(id);
        machine.setName("name");
        machine.setDescription("machineDescription");
        machine.setManufacturer("machineManufacturer");
        machine.setPrice(new BigDecimal(0));
        return machine;
    }
}

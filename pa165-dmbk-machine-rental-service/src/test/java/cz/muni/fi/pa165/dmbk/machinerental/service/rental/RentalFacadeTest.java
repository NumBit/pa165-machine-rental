package cz.muni.fi.pa165.dmbk.machinerental.service.rental;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.RentalFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto.RentalDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingService;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.dmbk.machinerental.service.RentalFacadeImpl;
import cz.muni.fi.pa165.dmbk.machinerental.service.RentalService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.dozermapper.core.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author Lukas Krekan
 **/
@RunWith(SpringRunner.class)
public class RentalFacadeTest {

    @MockBean private RentalService rentalService;
    @Autowired private RentalFacade rentalFacade;
    @MockBean private Mapper dozer;

    private Rental rental;
    private CustomerDto customerDto = new CustomerDto(1L, "CustomerA", "abc123456", LegalForm.INDIVIDUAL, "abc@def.com");
    private MachineDto machineDto = new MachineDto(1L, "MachineA", "description", "manufacturer", new BigDecimal(10));

    @TestConfiguration
    static class RentalFacadeImplTestConfiguration {

        @Bean public BeanMappingService beanMappingService() {
            return new BeanMappingServiceImpl();
        }

        @Bean public RentalFacade rentalFacade() {
            return new RentalFacadeImpl();
        }
    }

    @Before
    public void setUp() {
        var RentalDto = new RentalDto(
                1L,
                LocalDate.of(2020, 4, 20),
                LocalDate.of(2020, 4, 21),
                customerDto,
                machineDto,
                "abcdef"
        );
        when(dozer.map(any(Object.class), eq(RentalDto.class)))
               .thenReturn(RentalDto);
        rental = getRentalEntity(1L);
    }

    @Test
    public void createRental() {
        RentalCreateDto rentalCreateDto = new RentalCreateDto(
                LocalDate.of(2020, 4, 20),
                LocalDate.of(2020, 4, 21),
                customerDto,
                machineDto,
                "abcdef"
        );
        when(rentalService.createRental(any(Rental.class)))
                .thenReturn(2L);
        var createdRental = rentalFacade.createRental(rentalCreateDto);
        assertThat(createdRental.equals(2L));
    }

    @Test
    public void findAll() {
        when(rentalService.findAll())
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAll();
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByMachineId() {
        when(rentalService.findAllByMachineId(any(Long.class)))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByMachineId(1L);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByCustomerId() {
        when(rentalService.findAllByCustomerId(any(Long.class)))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByCustomerId(1L);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByRentalDateAfter() {
        var date = LocalDate.of(2020, 4, 20);
        when(rentalService.findAllByRentalDateAfter(any(LocalDate.class)))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByRentalDateAfter(date);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByRentalDateBefore() {
        var date = LocalDate.of(2020, 4, 22);
        when(rentalService.findAllByRentalDateBefore(any(LocalDate.class)))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByRentalDateBefore(date);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByRentalDateBetween() {
        var dateFrom = LocalDate.of(2020, 4, 20);
        var dateTo = LocalDate.of(2020, 4, 22);
        when(rentalService.findAllByRentalDateBetween(dateFrom, dateTo))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByRentalDateBetween(dateFrom, dateTo);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByReturnDateAfter() {
        var date = LocalDate.of(2020, 4, 20);
        when(rentalService.findAllByReturnDateAfter(any(LocalDate.class)))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByReturnDateAfter(date);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByReturnDateBefore() {
        var date = LocalDate.of(2020, 4, 20);
        when(rentalService.findAllByReturnDateBefore(any(LocalDate.class)))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByReturnDateBefore(date);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void findAllByReturnDateBetween() {
        var dateFrom = LocalDate.of(2020, 4, 20);
        var dateTo = LocalDate.of(2020, 4, 22);
        when(rentalService.findAllByReturnDateBetween(dateFrom, dateTo))
                .thenReturn(List.of(rental));
        var foundRentals = rentalFacade.findAllByReturnDateBetween(dateFrom, dateTo);
        Assert.assertEquals(1L, foundRentals.size());
        Assert.assertEquals(Long.valueOf(1), foundRentals.get(0).getId());
    }

    @Test
    public void deleteById(){
        when(rentalService.findById(any(Long.class)))
                .thenReturn(Optional.of(rental));
        var foundRental = rentalFacade.findById(1L);
        Assert.assertTrue(foundRental.isPresent());
        rentalFacade.deleteById(1L);
        when(rentalService.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        foundRental = rentalFacade.findById(1L);
        Assert.assertTrue(foundRental.isEmpty());
    }

    @Test
    public void deleteAll() {
        when(rentalService.findById(any(Long.class)))
                .thenReturn(Optional.of(rental));
        var foundRental = rentalFacade.findById(1L);
        Assert.assertTrue(foundRental.isPresent());
        rentalFacade.deleteAll();
        when(rentalService.findById(any(Long.class)))
                .thenReturn(Optional.empty());
        foundRental = rentalFacade.findById(1L);
        Assert.assertTrue(foundRental.isEmpty());
    }

    @Test
    public void checkAvailability() {
        var dateFrom = LocalDate.of(2020, 4, 20);
        var dateTo = LocalDate.of(2020, 4, 22);
        when(rentalService.checkAvailabilityForRent(any(Long.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Optional.of(true));
        var found = rentalFacade.checkMachineAvailabilityForRent(1L, dateFrom, dateTo);
        Assert.assertTrue(found.isPresent());
        Assert.assertTrue(found.get());
        when(rentalService.checkAvailabilityForRent(any(Long.class), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Optional.of(false));
        found = rentalFacade.checkMachineAvailabilityForRent(2L, dateFrom, dateTo);
        Assert.assertTrue(found.isPresent());
        Assert.assertFalse(found.get());
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

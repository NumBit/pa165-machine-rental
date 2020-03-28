package cz.muni.fi.pa165.dmbk.machinerental.rental;

import com.sun.istack.NotNull;
import cz.muni.fi.pa165.dmbk.machinerental.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.machine.MachineRepository;
import cz.muni.fi.pa165.dmbk.machinerental.rental.dao.Rental;
import cz.muni.fi.pa165.dmbk.machinerental.rental.repository.RentalRepository;
import cz.muni.fi.pa165.dmbk.machinerental.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.user.dao.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RentalDataLayerTest {

    @Autowired private RentalRepository rentalRepository;
    @Autowired private MachineRepository machineRepository;
    @Autowired private UserRepository userRepository;

    @Before
    public void setUp() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("1"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("1"));
        var rental = getBasicRentalWithAppendix("1");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.saveAndFlush(rental);

        storedCustomerId = customer.getId();
        storedMachineId = machine.getId();
        storedRentalId = rental.getId();
    }

    private static Long storedCustomerId;
    private static Long storedMachineId;
    private static Long storedRentalId;

    @Test
    @Rollback
    public void createRental() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.saveAndFlush(rental);

        Assert.assertNotNull(customer.getId());
        Assert.assertNotNull(machine.getId());
        Assert.assertNotNull(rental.getId());

        Assert.assertEquals(2L, rentalRepository.findAll().size());
        var optionalFoundRental = rentalRepository.findById(rental.getId());
        Assert.assertTrue(optionalFoundRental.isPresent());
        var foundRental = optionalFoundRental.get();
        Assert.assertEquals(rental.getId(), foundRental.getId());

        Assert.assertEquals(customer.getId(), foundRental.getCustomer().getId());
        Assert.assertEquals(machine.getId(), foundRental.getMachine().getId());
        Assert.assertEquals("description2", foundRental.getDescription());

        // Hibernate must map obtained objects to opened EntityManager sessions
        Assert.assertSame(customer, foundRental.getCustomer());
        Assert.assertSame(machine, foundRental.getMachine());
    }

    @Test
    @Rollback
    public void findById() {
        var optionalFoundRental = rentalRepository.findById(storedRentalId);
        Assert.assertTrue(optionalFoundRental.isPresent());
        var foundRental = optionalFoundRental.get();

        Assert.assertEquals(storedCustomerId, foundRental.getCustomer().getId());
        Assert.assertEquals(storedMachineId, foundRental.getMachine().getId());

        Assert.assertEquals("description1", foundRental.getDescription());
        Assert.assertEquals("login1", foundRental.getCustomer().getLogin());
        Assert.assertEquals("machine1", foundRental.getMachine().getName());

        var optionalStoredMachine = machineRepository.findById(storedMachineId);
        Assert.assertTrue(optionalStoredMachine.isPresent());
        var storedMachine = optionalStoredMachine.get();

        var optionalStoredCustomer = userRepository.findById(storedCustomerId);
        Assert.assertTrue(optionalStoredCustomer.isPresent());
        var storedCustomer = (Customer) optionalStoredCustomer.get();

        // Hibernate must map obtained objects to opened EntityManager sessions
        Assert.assertSame(storedMachine, foundRental.getMachine());
        Assert.assertSame(storedCustomer, foundRental.getCustomer());
    }

    @Test
    @Rollback
    public void findByIdNegative() {
        var optionalFoundRental = rentalRepository.findById(-1L);
        Assert.assertTrue(optionalFoundRental.isEmpty());
    }

    @Test
    @Rollback
    public void findRentalsForCustomerId() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.saveAndFlush(rental);

        var foundRentals = rentalRepository.findAllByCustomerId(customer.getId());
        Assert.assertFalse(foundRentals.isEmpty());

        // Hibernate must map obtained objects to opened EntityManager sessions
        // using customer stored during this test
        Assert.assertTrue(foundRentals.stream().allMatch(storedRental ->
            storedRental.getCustomer().equals(customer)
                && storedRental.getDescription().equals("description2")
        ));
    }

    @Test
    @Rollback
    public void findRentalsForCustomerIdNegative() {
        var foundRentals = rentalRepository.findAllByCustomerId(-1L);
        Assert.assertTrue(foundRentals.isEmpty());
    }

    @Test
    @Rollback
    public void findRentalsForMachineId() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.saveAndFlush(rental);

        var foundRentals = rentalRepository.findAllByMachineId(storedMachineId);
        Assert.assertFalse(foundRentals.isEmpty());

        // Hibernate must map obtained objects to opened EntityManager sessions
        // using machine stored during setUp() method
        var storedMachine = machineRepository.getOne(storedMachineId);
        Assert.assertTrue(foundRentals.stream().allMatch(storedRental ->
            storedRental.getMachine().equals(storedMachine)
                && storedRental.getDescription().equals("description1")
        ));
    }

    @Test
    @Rollback
    public void findRentalsForMachineIdNegative() {
        var foundRentals = rentalRepository.findAllByMachineId(-1L);
        Assert.assertTrue(foundRentals.isEmpty());
    }

    @Test
    @Rollback
    public void deleteRentalById() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.saveAndFlush(rental);
        rentalRepository.deleteById(storedRentalId);

        // Ensure that correct rental was deleted
        Assert.assertFalse(rentalRepository.findAll().isEmpty());
        Assert.assertTrue(rentalRepository.findAll().stream().allMatch(storedRental ->
                storedRental.equals(rental)));

        // Ensure that customer and machine associated with deleted rental are still present
        var optionalFoundCustomer = userRepository.findById(storedCustomerId);
        Assert.assertTrue(optionalFoundCustomer.isPresent());
        var optionalFoundMachine = machineRepository.findById(storedMachineId);
        Assert.assertTrue(optionalFoundMachine.isPresent());
    }

    @Test
    @Rollback
    public void deleteRentalByIdNegative() {
        Assertions.assertThrows(
                EmptyResultDataAccessException.class,
                () -> rentalRepository.deleteById(-1L));
    }

    @Test
    @Rollback
    public void deleteAllRentals() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.saveAndFlush(rental);

        // make sure all rentals were deleted
        rentalRepository.deleteAll();
        Assert.assertEquals(0L, rentalRepository.count());

        // make sure associated customers and machines were not deleted
        var optionalFoundMachine = machineRepository.findById(machine.getId());
        var optionalFoundCustomer = userRepository.findById(customer.getId());
        Assert.assertTrue(optionalFoundMachine.isPresent());
        Assert.assertTrue(optionalFoundCustomer.isPresent());
        var storedOptionalMachine = machineRepository.findById(storedMachineId);
        var storedOptionalCustomer = userRepository.findById(storedCustomerId);
        Assert.assertTrue(storedOptionalCustomer.isPresent());
        Assert.assertTrue(storedOptionalMachine.isPresent());
    }

    @Test
    @Rollback
    public void updateRental() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rental.setId(storedRentalId);

        // Check rental attributes before update
        var optionalStoredRental = rentalRepository.findById(storedRentalId);
        Assert.assertTrue(optionalStoredRental.isPresent());
        Assert.assertEquals("description1", optionalStoredRental.get().getDescription());

        // make update
        Assert.assertEquals(1L, rentalRepository.count());
        rentalRepository.saveAndFlush(rental);
        Assert.assertEquals(1L, rentalRepository.count());
        Assert.assertEquals(rental, optionalStoredRental.get());

        // Hibernate must map updated objects to opened EntityManager sessions
        // Check update changed stored rental attributes
        Assert.assertSame(customer, optionalStoredRental.get().getCustomer());
        Assert.assertSame(machine, optionalStoredRental.get().getMachine());
        Assert.assertEquals("description2", optionalStoredRental.get().getDescription());
    }

    @Test
    @Rollback
    public void updateRentalNegative() {
        var machine = machineRepository.saveAndFlush(getBasicMachineWithAppendix("2"));
        var customer = userRepository.saveAndFlush(getBasicCustomerWithAppendix("2"));
        var rental = getBasicRentalWithAppendix("2");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rental.setId(Long.MAX_VALUE);
        rentalRepository.saveAndFlush(rental);

        // make sure already contained data were not updated
        var optionalStoredRental = rentalRepository.findById(storedRentalId);
        Assert.assertTrue(optionalStoredRental.isPresent());
        var storedRental = optionalStoredRental.get();
        Assert.assertNotSame(rental, storedRental);

        Assert.assertNotEquals(machine.getId(), storedRental.getMachine().getId());
        Assert.assertNotEquals(customer.getId(), storedRental.getCustomer().getId());
        Assert.assertNotSame(machine, storedRental.getMachine());
        Assert.assertNotSame(customer, storedRental.getCustomer());
    }

    private Machine getBasicMachineWithAppendix(@NotNull String appendix) {
        Machine machine = new Machine();
        machine.setId(null);
        machine.setName("machine" + appendix);
        machine.setManufacturer("manufacturer" + appendix);
        machine.setDescription("description" + appendix);
        machine.setPrice(new BigDecimal(0));
        return machine;
    }

    private Customer getBasicCustomerWithAppendix(@NotNull String appendix) {
        return Customer.builder()
                .id(null).login("login" + appendix)
                .email("email" + appendix)
                .passwordHash("passwordHsh" + appendix)
                .legalForm(LegalForm.INDIVIDUAL)
                .build();
    }

    private Rental getBasicRentalWithAppendix(@NotNull String appendix) {
        Rental rental = new Rental();
        rental.setId(null);
        rental.setDescription("description" + appendix);
        rental.setRentalDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusMonths(1));
        return rental;
    }
}

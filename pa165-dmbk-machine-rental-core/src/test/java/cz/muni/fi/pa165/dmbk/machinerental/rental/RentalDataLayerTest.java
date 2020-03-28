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
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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

    @BeforeAll
    public void setUp() {/*
        var machine = getBasicMachineWithAppendix("1");
        var customer = getBasicCustomerWithAppendix("1");
        machineRepository.save(machine);
        userRepository.save(customer);

        var rental = getBasicRentalWithAppendix("1");
        rental.setCustomer(customer);
        rental.setMachine(machine);
        rentalRepository.save(rental);*/
    }

    @Test
    @Rollback
    public void createRental() {
        var machine = machineRepository.save(getBasicMachineWithAppendix("1"));
        var customer = userRepository.save(getBasicCustomerWithAppendix("1"));
        var rental = getBasicRentalWithAppendix("1");

        rental.setMachine(machine);
        rental.setCustomer(customer);
        rentalRepository.save(rental);

        Assert.assertNotNull(customer.getId());
        Assert.assertNotNull(machine.getId());
        Assert.assertNotNull(rental.getId());

        var optionalFoundRental = rentalRepository.findById(rental.getId());
        Assert.assertTrue(optionalFoundRental.isPresent());
        var foundRental = optionalFoundRental.get();
        Assert.assertEquals(rental.getId(), foundRental.getId());

        Assert.assertEquals(customer.getId(), foundRental.getCustomer().getId());
        Assert.assertEquals(machine.getId(), foundRental.getMachine().getId());
        Assert.assertEquals("description1", foundRental.getDescription());
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

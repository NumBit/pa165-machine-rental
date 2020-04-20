package cz.muni.fi.pa165.dmbk.machinerental.dao.user;

import com.sun.istack.NotNull;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Tests of user DAL.
 *
 * @author Peter Baltazarovič
 */


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDataLayerTest {

    @Autowired
    private UserRepository userRepository;

    private static Long storedCustomerId;
    private static Long storedAdminId;


    @Before
    public void setUp() {
        var customer = getBasicCustomerWithAppendix("1");
        var admin = getBasicAdminWithAppendix("1");
        userRepository.saveAndFlush(customer);
        userRepository.saveAndFlush(admin);

        storedCustomerId = customer.getId();
        storedAdminId = admin.getId();
    }

    @Test
    @Rollback
    public void createCustomer() {
        var customer = getBasicCustomerWithAppendix("2");
        userRepository.saveAndFlush(customer);

        Assert.assertNotNull(customer.getId());

        var optionalFoundCustomer = userRepository.findById(customer.getId());
        Assert.assertTrue(optionalFoundCustomer.isPresent());
        var foundCustomer = optionalFoundCustomer.get();
        Assert.assertEquals(customer, foundCustomer);
    }

    @Test
    @Rollback
    public void createAdmin() {
        var admin = getBasicAdminWithAppendix("2");
        userRepository.saveAndFlush(admin);

        Assert.assertNotNull(admin.getId());

        var optionalFoundAdmin = userRepository.findById(admin.getId());
        Assert.assertTrue(optionalFoundAdmin.isPresent());
        var foundAdmin = optionalFoundAdmin.get();
        Assert.assertEquals(admin, foundAdmin);
    }

    @Test
    @Rollback
    public void findCustomerById() {
        var optionalFoundCustomer = userRepository.findById(storedCustomerId);
        Assert.assertTrue(optionalFoundCustomer.isPresent());
        var foundCustomer = optionalFoundCustomer.get();
        Assert.assertEquals(storedCustomerId, foundCustomer.getId());

        Assert.assertEquals("loginC1", foundCustomer.getLogin());
        Assert.assertEquals("passwordHsh1", foundCustomer.getPasswordHash());

        Assert.assertTrue(foundCustomer instanceof Customer);
        var castedFoundCustomer = (Customer) foundCustomer;

        Assert.assertEquals("email1", castedFoundCustomer.getEmail());
        Assert.assertEquals(LegalForm.INDIVIDUAL, castedFoundCustomer.getLegalForm());

    }

    @Test
    @Rollback
    public void findAdminById() {
        var optionalFoundAdmin = userRepository.findById(storedAdminId);
        Assert.assertTrue(optionalFoundAdmin.isPresent());
        var foundAdmin = optionalFoundAdmin.get();
        Assert.assertEquals(storedAdminId, foundAdmin.getId());

        Assert.assertEquals("loginA1", foundAdmin.getLogin());
        Assert.assertEquals("passwordHsh1", foundAdmin.getPasswordHash());

        Assert.assertTrue(foundAdmin instanceof Admin);
        var castedFoundAdmin = (Admin) foundAdmin;

        Assert.assertEquals("name1", castedFoundAdmin.getName());
        Assert.assertEquals("sureName1", castedFoundAdmin.getSureName());
    }


    @Test
    @Rollback
    public void findByIdNegative() {
        var optionalFoundUser = userRepository.findById(-1L);
        Assert.assertTrue(optionalFoundUser.isEmpty());
    }


    @Test
    @Rollback
    public void deleteUserById() {
        var customer = getBasicCustomerWithAppendix("2");
        userRepository.saveAndFlush(customer);
        Assert.assertEquals(3L, userRepository.findAll().size());
        userRepository.deleteById(customer.getId());

        Assert.assertEquals(2L, userRepository.findAll().size());
        Assert.assertTrue(userRepository.findById(customer.getId()).isEmpty());

    }

    @Test
    @Rollback
    public void deleteAll() {
        var customer = getBasicCustomerWithAppendix("2");
        userRepository.saveAndFlush(customer);
        Assert.assertEquals(3L, userRepository.findAll().size());

        userRepository.deleteAll();
        Assert.assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    @Rollback
    public void updateCustomer() {
        var customer = getBasicCustomerWithAppendix("2");
        userRepository.saveAndFlush(customer);

        var optionalFoundCustomer = userRepository.findById(customer.getId());
        Assert.assertTrue(optionalFoundCustomer.isPresent());
        var foundCustomer = optionalFoundCustomer.get();

        Assert.assertTrue(foundCustomer instanceof Customer);
        var castedFoundCustomer = (Customer) foundCustomer;

        Assert.assertEquals("email2", castedFoundCustomer.getEmail());
        customer.setEmail("updatedEmail");
        userRepository.saveAndFlush(customer);
        Assert.assertEquals("updatedEmail", ((Customer) foundCustomer).getEmail());
        Assert.assertEquals(customer, optionalFoundCustomer.get());

    }

    @Test
    @Rollback
    public void updateAdmin() {
        var admin = getBasicAdminWithAppendix("2");
        userRepository.saveAndFlush(admin);

        var optionalFoundAdmin = userRepository.findById(admin.getId());
        Assert.assertTrue(optionalFoundAdmin.isPresent());
        var foundAdmin = optionalFoundAdmin.get();

        Assert.assertTrue(foundAdmin instanceof Admin);
        var castedFoundAdmin = (Admin) foundAdmin;

        Assert.assertEquals("name2", castedFoundAdmin.getName());
        admin.setName("updatedName");
        userRepository.saveAndFlush(admin);
        Assert.assertEquals("updatedName", ((Admin) foundAdmin).getName());
        Assert.assertEquals(admin, optionalFoundAdmin.get());

    }

    @Test
    @Rollback
    public void findCustomersByLegalForm() {
        var customer2 = getBasicCustomerWithAppendix("2");
        var customer3 = getBasicCustomerWithAppendix("3");
        userRepository.saveAndFlush(customer2);
        userRepository.saveAndFlush(customer3);

        var foundCustomers = userRepository.findCustomersByLegalForm(LegalForm.INDIVIDUAL);
        Assert.assertFalse(foundCustomers.isEmpty());
        Assert.assertEquals(3L, foundCustomers.size());
        Assert.assertTrue(foundCustomers.contains(customer2));
        Assert.assertTrue(foundCustomers.contains(customer3));
        Assert.assertTrue(foundCustomers.contains(userRepository.findById(storedCustomerId).get()));

    }


    @Test
    @Rollback
    public void findCustomersByLegalFormNegative() {
        var foundCustomers = userRepository.findCustomersByLegalForm(LegalForm.CORPORATION);
        Assert.assertTrue(foundCustomers.isEmpty());
    }

    @Test
    @Rollback
    public void findCustomerByEmail() {
        var customer = getBasicCustomerWithAppendix("2");
        userRepository.saveAndFlush(customer);

        var optionalFoundCustomer = userRepository.findCustomerByEmail("email2");
        Assert.assertTrue(optionalFoundCustomer.isPresent());
        var foundCustomer = optionalFoundCustomer.get();
        Assert.assertEquals(customer, foundCustomer);
    }

    @Test
    @Rollback
    public void findCustomerByEmailNegative() {
        var optionalFoundCustomer = userRepository.findCustomerByEmail("notUsedEmail");
        Assert.assertTrue(optionalFoundCustomer.isEmpty());
    }

    @Test
    @Rollback
    public void findAdminsBySureName() {
        var admin2 = getBasicAdminWithAppendix("2");
        var admin3 = getBasicAdminWithAppendix("3");
        admin2.setSureName("sameSureName");
        admin3.setSureName("sameSureName");
        userRepository.saveAndFlush(admin2);
        userRepository.saveAndFlush(admin3);

        var foundAdmins = userRepository.findAdminsBySureName("sameSureName");
        Assert.assertEquals(2L, foundAdmins.size());
        Assert.assertTrue(foundAdmins.contains(admin2));
        Assert.assertTrue(foundAdmins.contains(admin3));
    }

    @Test
    @Rollback
    public void findAdminsBySureNameNegative() {
        var foundAdmins = userRepository.findAdminsBySureName("notUsedSureName");
        Assert.assertTrue(foundAdmins.isEmpty());
    }


    private Customer getBasicCustomerWithAppendix(@NotNull String appendix) {
        return Customer.builder()
                .id(null).login("loginC" + appendix)
                .email("email" + appendix)
                .passwordHash("passwordHsh" + appendix)
                .legalForm(LegalForm.INDIVIDUAL)
                .build();
    }

    private Admin getBasicAdminWithAppendix(@NotNull String appendix) {
        return Admin.builder()
                .id(null).login("loginA" + appendix)
                .passwordHash("passwordHsh" + appendix)
                .name("name" + appendix)
                .sureName("sureName" + appendix)
                .build();
    }
}


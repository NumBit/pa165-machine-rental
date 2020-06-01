package cz.muni.fi.pa165.dmbk.machinerental.service.user;

import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.repository.UserRepository;
import cz.muni.fi.pa165.dmbk.machinerental.service.CustomDataAccessException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private Customer customer;
    private Admin admin;

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Before
    public void initUsers() {
        customer = CustomerEntity(1L);
        admin = AdminEntity(2L);
    }

    @Test
    public void exceptionHandling() {
        when(userRepository.save(null)).thenThrow(new InvalidJpaQueryMethodException("Unexpected null"));
        assertThrows(CustomDataAccessException.class, () -> userService.persistUser(null));
    }

    @Test
    public void persistUsers() {
        when(userRepository.save(any(Customer.class))).thenReturn(customer);
        when(userRepository.save(any(Admin.class))).thenReturn(admin);
        var customerId = userService.persistUser(customer);
        var adminId = userService.persistUser(admin);
        Assert.assertNotNull(customerId);
        Assert.assertEquals(customer.getId(), customerId);
        Assert.assertNotNull(adminId);
        Assert.assertEquals(admin.getId(), adminId);
    }

    @Test
    public void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        var foundUser = userService.findById(1L);
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customer, foundUser.get());
    }

    @Test
    public void findByLogin() {
        when(userRepository.findByLogin(any(String.class))).thenReturn(Optional.ofNullable(admin));
        var foundUser = userService.findByLogin("BestAdmin");
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(admin, foundUser.get());
    }

    @Test
    public void isAdmin() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(admin));
        var falseAdmin = userService.isAdmin(customer);
        var trueAdmin = userService.isAdmin(admin);
        Assert.assertTrue(falseAdmin.isPresent());
        Assert.assertFalse(falseAdmin.get());
        Assert.assertTrue(trueAdmin.isPresent());
        Assert.assertTrue(trueAdmin.get());
    }

    @Test
    public void promoteToAdmin() {
        when(userRepository.save(CustomerEntity(1L))).thenReturn(CustomerEntity(1L));
        when(userRepository.findById(1L)).thenReturn(Optional.of(CustomerEntity(1L)));
        var newAdmin = Admin.builder()
                .id(6L)
                .login(CustomerEntity(1L).getLogin())
                .passwordHash(CustomerEntity(1L).getPasswordHash())
                .name("newName")
                .sureName("newSureName")
                .build();
        when(userRepository.save(newAdmin)).thenReturn(newAdmin);
        when(userRepository.findById(6L)).thenReturn(Optional.of(newAdmin));

        userService.persistUser(CustomerEntity(1L));
        Assert.assertTrue(userService.findById(customer.getId()).isPresent());
        var newAdminId = userService.promoteToAdmin(customer.getId()).orElse(null).getId();
        Assert.assertTrue(userService.findById(newAdminId).orElse(null) instanceof Admin);
        Assert.assertTrue(userService.isAdmin(userService.findById(newAdminId).orElse(null)).orElse(null));
    }

    @Test
    public void findCustomersByLegalForm() {
        when(userRepository.findCustomersByLegalForm(LegalForm.INDIVIDUAL)).thenReturn(List.of(customer));
        when(userRepository.findCustomersByLegalForm(LegalForm.CORPORATION)).thenReturn(new ArrayList<>());
        var individuals = userService.findCustomersByLegalForm(LegalForm.INDIVIDUAL);
        var corporations = userService.findCustomersByLegalForm(LegalForm.CORPORATION);
        Assert.assertFalse(individuals.isEmpty());
        Assert.assertEquals(1, individuals.size());
        Assert.assertTrue(individuals.contains(customer));
        Assert.assertTrue(corporations.isEmpty());
    }

    @Test
    public void findCustomerByEmail() {
        when(userRepository.findCustomerByEmail(any(String.class))).thenReturn(Optional.ofNullable(customer));
        var foundUser = userService.findCustomerByEmail(customer.getEmail());
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customer, foundUser.get());
    }

    @Test
    public void findAdminsBySureName() {
        when(userRepository.findAdminsBySureName(any(String.class))).thenReturn(List.of(admin));
        var admins = userService.findAdminsBySureName(admin.getSureName());
        Assert.assertFalse(admins.isEmpty());
        Assert.assertEquals(1, admins.size());
        Assert.assertTrue(admins.contains(admin));
    }

    @Test
    public void deleteUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        var foundUser = userService.findById(1L);
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customer, foundUser.get());
        userService.deleteUserById(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        foundUser = userService.findById(1L);
        Assert.assertFalse(foundUser.isPresent());
    }

    @Test
    public void deleteAllUsers() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        var foundUser = userService.findById(1L);
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customer, foundUser.get());
        userService.deleteAllUsers();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        foundUser = userService.findById(1L);
        Assert.assertFalse(foundUser.isPresent());
    }

    private Customer CustomerEntity(Long id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setLogin("CustomerX");
        customer.setEmail("custom@mer.com");
        customer.setLegalForm(LegalForm.INDIVIDUAL);
        customer.setPasswordHash("xyz6789xyz");
        return customer;
    }

    private Admin AdminEntity(Long id) {
        Admin admin = new Admin();
        admin.setId(id);
        admin.setLogin("BestAdmin");
        admin.setPasswordHash("hardxpass77");
        admin.setName("Wal");
        admin.setSureName("Do");
        return admin;
    }
}

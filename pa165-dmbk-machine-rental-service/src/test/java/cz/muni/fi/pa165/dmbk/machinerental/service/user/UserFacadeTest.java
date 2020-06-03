package cz.muni.fi.pa165.dmbk.machinerental.service.user;

import com.github.dozermapper.core.Mapper;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.UserFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.AdminDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingService;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
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
public class UserFacadeTest {

    @Autowired
    private UserFacade userFacade;
    @MockBean
    private UserService userService;

    private Customer customer;
    private Admin admin;
    private CustomerDto customerDto = new CustomerDto(1L, "CustomerX", "xyz6789xyz", LegalForm.INDIVIDUAL, "custom@mer.com");
    private AdminDto adminDto = new AdminDto(2L, "BestAdmin", "hardxpass77", "Wal", "Do");

    @TestConfiguration
    static class UserFacadeTestContextConfiguration {
        @Bean public UserFacade userFacade() {
            return new UserFacadeImpl();
        }
        @MockBean public Mapper mapper;
        @Bean public BeanMappingService beanMappingService() {
            return new BeanMappingServiceImpl();
        }
    }

    @Before
    public void initUsers() {
        customer = CustomerEntity(1L);
        admin = AdminEntity(2L);
    }

    @Test
    public void exceptionHandling() {
        when(userService.persistUser(null)).thenThrow(new NullPointerException("Unexpected null"));
        // asserting null pointer exception because parsing of customer/admin can not be done on null
        assertThrows(NullPointerException.class, () -> userFacade.persistUser(null));
    }

    @Test
    public void persistUsers() {
        when(userService.persistUser(any(Customer.class))).thenReturn(customerDto.getId());
        when(userService.persistUser(any(Admin.class))).thenReturn(adminDto.getId());
        var customerId = userFacade.persistUser(customerDto);
        var adminId = userFacade.persistUser(adminDto);
        Assert.assertNotNull(customerId);
        Assert.assertEquals(customer.getId(), customerId);
        Assert.assertNotNull(adminId);
        Assert.assertEquals(admin.getId(), adminId);
    }

    @Test
    public void findByLogin() {
        when(userService.findByLogin(any(String.class))).thenReturn(Optional.ofNullable(admin));
        var foundUser = userFacade.findByLogin("BestAdmin");
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(adminDto, foundUser.get());
    }

    @Test
    public void isAdmin() {
        when(userService.isAdmin(customer)).thenReturn(Optional.of(false));
        when(userService.isAdmin(admin)).thenReturn(Optional.of(true));
        var falseAdmin = userFacade.isAdmin(customerDto);
        var trueAdmin = userFacade.isAdmin(adminDto);
        Assert.assertTrue(falseAdmin.isPresent());
        Assert.assertFalse(falseAdmin.get());
        Assert.assertTrue(trueAdmin.isPresent());
        Assert.assertTrue(trueAdmin.get());
    }

    @Test
    public void findCustomersByLegalForm() {
        when(userService.findCustomersByLegalForm(LegalForm.INDIVIDUAL)).thenReturn(List.of(customer));
        when(userService.findCustomersByLegalForm(LegalForm.CORPORATION)).thenReturn(new ArrayList<>());
        var individuals = userFacade.findCustomersByLegalForm(LegalForm.INDIVIDUAL);
        var corporations = userFacade.findCustomersByLegalForm(LegalForm.CORPORATION);
        Assert.assertFalse(individuals.isEmpty());
        Assert.assertEquals(1, individuals.size());
        Assert.assertTrue(individuals.contains(customerDto));
        Assert.assertTrue(corporations.isEmpty());
    }

    @Test
    public void findCustomerByEmail() {
        when(userService.findCustomerByEmail(any(String.class))).thenReturn(Optional.ofNullable(customer));
        var foundUser = userFacade.findCustomerByEmail(customer.getEmail());
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customerDto, foundUser.get());
    }

    @Test
    public void findAdminsBySureName() {
        when(userService.findAdminsBySureName(any(String.class))).thenReturn(List.of(admin));
        var admins = userFacade.findAdminsBySureName(admin.getSureName());
        Assert.assertFalse(admins.isEmpty());
        Assert.assertEquals(1, admins.size());
        Assert.assertTrue(admins.contains(adminDto));
    }

    @Test
    public void deleteUserById() {
        when(userService.findByLogin(customerDto.getLogin())).thenReturn(Optional.ofNullable(customer));
        var foundUser = userFacade.findByLogin(customerDto.getLogin());
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customerDto, foundUser.get());
        userFacade.deleteUserById(customerDto.getId());
        when(userService.findByLogin(customerDto.getLogin())).thenReturn(Optional.empty());
        foundUser = userFacade.findByLogin(customerDto.getLogin());
        Assert.assertFalse(foundUser.isPresent());
    }

    @Test
    public void deleteAllUsers() {
        when(userService.findByLogin(customerDto.getLogin())).thenReturn(Optional.ofNullable(customer));
        var foundUser = userFacade.findByLogin(customerDto.getLogin());
        Assert.assertTrue(foundUser.isPresent());
        Assert.assertEquals(customerDto, foundUser.get());
        userFacade.deleteAllUsers();
        when(userService.findByLogin(customerDto.getLogin())).thenReturn(Optional.empty());
        foundUser = userFacade.findByLogin(customerDto.getLogin());
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

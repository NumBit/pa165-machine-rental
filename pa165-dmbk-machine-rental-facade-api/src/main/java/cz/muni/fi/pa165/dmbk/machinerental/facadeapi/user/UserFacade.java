package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user;

import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.AdminDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * This interface declares functionality over
 * user service and DAO layer. It works with user
 * DTO classes which will be mapped to user DAO classes
 * which can be stored, retrieved, etc. from database.
 * This interface must be used across system when accessing
 * user database functionality.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Component
public interface UserFacade {
    void persistUser(UserDto user);
    Optional<UserDto> findByLogin(String login);
    Optional<Boolean> isAdmin(UserDto user);
    List<CustomerDto> findCustomersByLegalForm(LegalForm legalForm);
    Optional<CustomerDto> findCustomerByEmail(String email);
    List<AdminDto> findAdminsBySureName(String sureName);
    void deleteUserById(Long id);
    void deleteAllUsers();
}

package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * User service interface declaring access into data layer
 * functionality of user entity. {@link CustomDataAccessException}
 * can be thrown from its persist method but in almost all cases this
 * its using Optional or List type operation for which exception is never
 * thrown.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Service
public interface UserService {
    void persistUser(User user);
    Optional<User> findById(Long id);
    Optional<User> findByLogin(String login);
    Optional<Boolean> isAdmin(User user);
    List<Customer> findCustomersByLegalForm(LegalForm legalForm);
    Optional<Customer> findCustomerByEmail(String email);
    List<Admin> findAdminsBySureName(String sureName);
    void deleteUserById(Long id);
    void deleteAllUsers();
}

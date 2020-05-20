package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.AbstractUser;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.User;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Implementation of {@link UserService} interface.
 * This service is working with user DAO classes
 * and JPA repositories. It encapsulates all exceptions
 * thrown in data-layer into one {@link CustomDataAccessException}.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;

    public Long persistUser(User user) {
        // safe cast since repository cannot work with user interface
        return exceptionCatcher(() -> userRepository.save((AbstractUser) user)).getId();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(Function.identity());
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<Boolean> isAdmin(User user) {
        return findById(user.getId())
                .map(foundUser -> foundUser instanceof Admin);
    }

    public List<Customer> findCustomersByLegalForm(LegalForm legalForm) {
        return userRepository.findCustomersByLegalForm(legalForm);
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return userRepository.findCustomerByEmail(email);
    }

    public List<Admin> findAdminsBySureName(String sureName) {
        return userRepository.findAdminsBySureName(sureName);
    }

    public List<Customer> findAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    private static <T> T exceptionCatcher(Callable<T> thrower) {
        try { return thrower.call(); }
        catch (Exception exception) {
            throw new CustomDataAccessException(exception.getMessage(), exception);
        }
    }
}

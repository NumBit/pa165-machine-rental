package cz.muni.fi.pa165.dmbk.machinerental.user.repository;

import cz.muni.fi.pa165.dmbk.machinerental.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.user.dao.AbstractUser;
import cz.muni.fi.pa165.dmbk.machinerental.user.dao.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.user.dao.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface defining user DAOs JPA operations,
 * over application database. These operations are
 * implemented by spring boot framework, as well as
 * managing entity managers, so this interface will
 * work even without custom implementation. CRUD
 * operations are implemented by default, thus dont
 * need to define them.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Repository
public interface UserRepository extends JpaRepository<AbstractUser, Long> {
    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM Customer u WHERE u.legalForm = ?1")
    List<Customer> findCustomersByLegalForm(LegalForm legalForm);

    @Query("SELECT u FROM Customer u WHERE u.email = ?1")
    Optional<Customer> findCustomerByEmail(String email);

    @Query("SELECT u FROM Admin u WHERE u.sureName = ?1")
    List<Admin> findAdminsBySureName(String sureName);
}

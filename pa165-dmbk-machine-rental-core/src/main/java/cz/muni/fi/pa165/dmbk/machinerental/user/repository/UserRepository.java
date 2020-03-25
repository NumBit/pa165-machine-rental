package cz.muni.fi.pa165.dmbk.machinerental.user.repository;

import cz.muni.fi.pa165.dmbk.machinerental.user.dao.AbstractUser;
import cz.muni.fi.pa165.dmbk.machinerental.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}

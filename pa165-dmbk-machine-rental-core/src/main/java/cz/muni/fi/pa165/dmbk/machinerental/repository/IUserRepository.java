package cz.muni.fi.pa165.dmbk.machinerental.repository;

import cz.muni.fi.pa165.dmbk.machinerental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}

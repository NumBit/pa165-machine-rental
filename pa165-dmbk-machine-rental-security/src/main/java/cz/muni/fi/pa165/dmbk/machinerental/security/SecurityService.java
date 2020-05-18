package cz.muni.fi.pa165.dmbk.machinerental.security;

import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Interface for custom implemented
 * security service.
 *
 * @author Norbert Dopjera
 */
@Service
public interface SecurityService {
    Optional<String> findLoggedInUsername();
    boolean authenticate(String userName, String password);
    Optional<UserDto> findLoggedInUser();
}

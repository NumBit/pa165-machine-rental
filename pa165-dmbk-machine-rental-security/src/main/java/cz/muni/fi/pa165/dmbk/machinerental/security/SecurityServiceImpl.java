package cz.muni.fi.pa165.dmbk.machinerental.security;

import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.UserFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Custom security service providing authentication
 * and services to find authenticated identity name
 * or object. This service is thread-safe, so various
 * threads of server can simultaneously access
 * authentication functionality.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Slf4j
@Service
class SecurityServiceImpl implements SecurityService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsServiceImpl userDetailsService;
    @Autowired private UserFacade userFacade;

    @Override
    public Optional<String> findLoggedInUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object userDetails = authentication.getPrincipal();
        return Optional.ofNullable((userDetails instanceof UserDetails)
                ? ((UserDetails) userDetails).getUsername() : null);
    }

    @Override
    public Optional<UserDto> findLoggedInUser() {
        return userFacade
                .findByLogin(findLoggedInUsername().orElse(""));
    }

    /**
     * This method authenticates identity by given username
     * and raw password. For password validation it uses authentication
     * manager which is configured inside security configuration class.
     *
     * @param userName login of identity to be authenticated
     * @param password raw password of identity to be authenticated
     * @return boolean identifying result of authentication.
     */
    @Override
    public boolean authenticate(String userName, String password) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if (usernamePasswordAuthenticationToken.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.debug(String.format("Auto login %s successfully!", userName));
                return true;
            }
        } catch (AuthenticationException ignored) { return false; }
        return false;
    }
}

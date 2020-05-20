package cz.muni.fi.pa165.dmbk.machinerental.security;

import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.UserFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.AdminDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom User details service providing functionality
 * to obtain user details from database with relevant
 * roles. This service is thread-safe since it only reads
 * from database.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired private UserFacade userFacade;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName) {
        SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority("ADMIN");
        SimpleGrantedAuthority customerRole = new SimpleGrantedAuthority("USER");

        UserDto user = userFacade.findByLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (user instanceof AdminDto) grantedAuthorities.addAll(Arrays.asList(adminRole, customerRole));
        if (user instanceof CustomerDto) grantedAuthorities.addAll(Collections.singletonList(customerRole));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPasswordHash(), grantedAuthorities
        );
    }
}

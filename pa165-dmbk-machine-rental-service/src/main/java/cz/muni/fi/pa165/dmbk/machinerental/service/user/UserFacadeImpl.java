package cz.muni.fi.pa165.dmbk.machinerental.service.user;

import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.User;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.UserFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.AdminDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingService;
import cz.muni.fi.pa165.dmbk.machinerental.service.CustomDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserFacade} interface which is used
 * by system to access its service and data layer user functionality.
 * This implementation is mapping user DTO classes to user DAO classes
 * which are used by service and data layer. Implementation will be
 * picked automatically by spring boot component scanning, so one can
 * just use @Autowired {@link UserFacade} to obtain this implementation.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Component
@Transactional(rollbackFor = CustomDataAccessException.class)
public class UserFacadeImpl implements UserFacade {

    @Autowired private BeanMappingService beanMappingService;

    @Autowired private UserService userService;

    @Override
    public Long persistUser(UserDto userDto) {
        return userService.persistUser(userDtoToEntity(userDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> findByLogin(@NonNull String login) {
        // if not valid login is provided userService will return empty optional
        // no validation is required
        return userService.findByLogin(login)
                .map(UserFacadeImpl::entityToUserDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Boolean> isAdmin(@NonNull UserDto userDto) {
        // only user ID is required for this functionality to work
        return (userDto.getId() == null)
                ? Optional.of(false)
                : userService.isAdmin(userDtoToEntity(userDto));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> findCustomersByLegalForm(@NonNull LegalForm legalForm) {
        // if legal from does not contain valid information return empty list
        if (!legalForm.equals(LegalForm.CORPORATION) && !legalForm.equals(LegalForm.INDIVIDUAL))
            return List.of();
        return userService.findCustomersByLegalForm(legalForm)
                .stream()
                .map(UserFacadeImpl::entityToUserDto)
                .map(userDto -> (CustomerDto) userDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDto> findCustomerByEmail(@NonNull String email) {
        // if not valid email is provided userService will return empty optional
        // no validation is required
        return userService.findCustomerByEmail(email)
                .map(UserFacadeImpl::entityToUserDto)
                .map(userDto -> (CustomerDto) userDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> findAllCustomers() {
        return beanMappingService.mapTo(userService.findAllCustomers(), CustomerDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDto> findAdminsBySureName(@NonNull String sureName) {
        // if not valid sureName is provided userService will return empty optional
        // no validation is required
        return userService.findAdminsBySureName(sureName)
                .stream()
                .map(UserFacadeImpl::entityToUserDto)
                .map(userDto -> (AdminDto) userDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserById(@NonNull Long id) {
        userService.deleteUserById(id);
    }

    @Override
    public void deleteAllUsers() {
        userService.deleteAllUsers();
    }

    private static User userDtoToEntity(UserDto userDto) {
        return (userDto instanceof AdminDto)
                ? Admin.builder()
                       .id(userDto.getId())
                       .login(userDto.getLogin())
                       .passwordHash(userDto.getPasswordHash())
                       .name(((AdminDto) userDto).getName())
                       .sureName(((AdminDto) userDto).getSureName())
                       .build()
                : Customer.builder()
                       .id(userDto.getId())
                       .login(userDto.getLogin())
                       .passwordHash(userDto.getPasswordHash())
                       .email(((CustomerDto) userDto).getEmail())
                       .legalForm(((CustomerDto) userDto).getLegalForm())
                       .build();
    }

    private static UserDto entityToUserDto(User user) {
        return (user instanceof Admin)
                ? AdminDto.builder()
                          .withId(user.getId())
                          .withLogin(user.getLogin())
                          .withPasswordHash(user.getPasswordHash())
                          .withName(((Admin) user).getName())
                          .withSureName(((Admin) user).getSureName())
                          .build()
                : CustomerDto.builder()
                             .withId(user.getId())
                             .withLogin(user.getLogin())
                             .withPasswordHash(user.getPasswordHash())
                             .withEmail(((Customer) user).getEmail())
                             .withLegalForm(((Customer) user).getLegalForm())
                             .build();
    }
}

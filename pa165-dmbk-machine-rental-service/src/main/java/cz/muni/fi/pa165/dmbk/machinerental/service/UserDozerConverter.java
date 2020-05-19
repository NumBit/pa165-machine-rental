package cz.muni.fi.pa165.dmbk.machinerental.service;

import com.github.dozermapper.core.CustomConverter;
import com.github.dozermapper.core.MappingException;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Admin;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.User;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.AdminDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;

public class UserDozerConverter implements CustomConverter {
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue == null){
            return null;
        };
        if (sourceFieldValue instanceof User) {
            return entityToUserDto((User) sourceFieldValue);
        }
        else if (sourceFieldValue instanceof UserDto) {
            return userDtoToEntity((UserDto) sourceFieldValue);
        }
        else {
            throw new MappingException("Converter TestCustomConverter "
                    + "used incorrectly. Arguments passed in were:"
                    + existingDestinationFieldValue
                    + " and "
                    + sourceFieldValue);
        }
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

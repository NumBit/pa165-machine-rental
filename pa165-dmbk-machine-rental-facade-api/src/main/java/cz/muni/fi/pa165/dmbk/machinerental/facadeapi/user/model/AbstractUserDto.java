package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Abstract base type for all user DTO types which
 * will be used across whole system. This class provides
 * base user data, functionality and can be used for
 * serialization and deserialization within inheritance
 * but {@link UserDto} interface or specific user types,
 * e.g {@link AdminDto} are more preferable and therefore
 * is declared as package private. This class is <b>immutable</b>.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@JsonIgnoreProperties(value={ "passwordHash" }, allowSetters = true)
abstract class AbstractUserDto implements UserDto {
    protected final Long id;
    protected final String login;
    protected final String passwordHash;
}

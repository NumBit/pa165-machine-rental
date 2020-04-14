package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This interface declares shared common property
 * of user DTO classes which will be used across system.
 * Jackson annotations allows usage of inheritance when
 * serializing and de-serializing instances of this interface.
 * That allows simple usage of its subclasses in JSON web responses,
 * messages etc. All subclasses are meant to be <b>immutable</b>.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@JsonSubTypes({
        @JsonSubTypes.Type(value = AdminDto.class),
        @JsonSubTypes.Type(value = CustomerDto.class)})
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
public interface UserDto {
    Long getId();
    String getLogin();
    String getPasswordHash();
}

package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents Customer user type DTO which will be
 * used across whole system. This class and its
 * parents are configured to serialize and deserialize
 * with jackson mapper properly and therefore can be
 * used to create JSON responses messages etc. This
 * class is <b>immutable</b>.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = CustomerDto.Builder.class)
public class CustomerDto extends AbstractUserDto {

    private final LegalForm legalForm;
    private final String email;

    @lombok.Builder(builderClassName = "Builder", setterPrefix = "with")
    public CustomerDto(Long id, String login, String passwordHash,
                       LegalForm legalForm, String email) {
        super(id, login, passwordHash);
        this.legalForm = legalForm;
        this.email = email;
    }
}

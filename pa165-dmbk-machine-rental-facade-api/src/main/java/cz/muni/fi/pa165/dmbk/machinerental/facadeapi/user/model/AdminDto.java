package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Represents Admin user type DTO which will be
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
@JsonDeserialize(builder = AdminDto.Builder.class)
public class AdminDto extends AbstractUserDto {

    private final String name;
    private final String sureName;

    @lombok.Builder(builderClassName = "Builder", setterPrefix = "with")
    public AdminDto(Long id, String login, String passwordHash, String name, String sureName) {
        super(id, login, passwordHash);
        this.name = name;
        this.sureName = sureName;
    }
}

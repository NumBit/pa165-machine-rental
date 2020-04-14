package cz.muni.fi.pa165.dmbk.machinerental.dao.user.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This DAO class represents admin table inside
 * application database. Table will hold foreign
 * key, as reference to ID inside a table representing
 * parent class {@link AbstractUser}. This way
 * inheritance is mapped to the application database.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "ADMIN")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Admin extends AbstractUser {

    @Column(nullable = false) private String name;
    @Column(nullable = false) private String sureName;

    @lombok.Builder(builderClassName = "Builder", toBuilder = true)
    public Admin(Long id, String login, String passwordHash,
                 String name, String sureName) {
        super(id, login, passwordHash);
        this.name = name;
        this.sureName = sureName;
    }
}

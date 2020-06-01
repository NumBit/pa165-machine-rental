package cz.muni.fi.pa165.dmbk.machinerental.dao.user.model;

import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * This DAO class represents customer table inside
 * application database. Table will hold foreign
 * key, as reference to ID inside a table representing
 * parent class {@link AbstractUser}. This way
 * inheritance is mapped to the application database.
 * {@link EqualsAndHashCode} and {@link ToString} annotations
 * are explicitly declared forcing call to {@link AbstractUser}
 * same annotations.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "CUSTOMER")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends AbstractUser {

    @Column(name = "legal_form") private LegalForm legalForm;
    @Column(nullable = false, unique = true) private String email;

    @lombok.Builder(builderClassName = "Builder", toBuilder = true)
    public Customer(Long id, String login, String passwordHash,
                    String email, LegalForm legalForm) {
        super(id, login, passwordHash);
        this.legalForm = legalForm;
        this.email = email;
    }
}

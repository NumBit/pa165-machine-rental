package cz.muni.fi.pa165.dmbk.machinerental.dao.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Abstract base type for all user types which
 * will be stored in the database. Data defined
 * by this abstract class will be stored in database
 * inside table representing this entity. This allows
 * to use inheritance of entities without redundant data.
 * {@link EqualsAndHashCode} annotation is explicitly
 * declared to force exclusion of ID field. See
 * {@link Data} annotation.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table(name = "ABSTRACT_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractUser implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String login;

    @Column(nullable = false, name = "password_hash")
    protected String passwordHash;
}

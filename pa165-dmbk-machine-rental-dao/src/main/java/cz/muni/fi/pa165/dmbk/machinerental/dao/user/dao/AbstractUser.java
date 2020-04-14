package cz.muni.fi.pa165.dmbk.machinerental.dao.user.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
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
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ABSTRACT_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractUser implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false, unique = true)
    protected String login;

    @Column(nullable = false)
    protected String passwordHash;
}

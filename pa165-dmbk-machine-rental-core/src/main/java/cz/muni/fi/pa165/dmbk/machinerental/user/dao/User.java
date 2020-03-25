package cz.muni.fi.pa165.dmbk.machinerental.user.dao;

/**
 * Common interface for all user types which
 * will be stored in the database. This interface
 * defines contract on data layer of application,
 * similar interfaces can be used in presentation
 * layer, service layer, etc.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
public interface User {
    Long getId();
    String getLogin();
    String getPasswordHash();
}

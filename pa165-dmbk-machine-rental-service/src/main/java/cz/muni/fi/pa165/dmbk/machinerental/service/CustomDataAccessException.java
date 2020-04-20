package cz.muni.fi.pa165.dmbk.machinerental.service;

import org.springframework.dao.DataAccessException;

/**
 * All exceptions from data layer will be mapped
 * into this one exception.
 *
 * @author Norbert Dopjera 456355@mail.muni.cz
 */
public class CustomDataAccessException extends DataAccessException {

    public CustomDataAccessException(String msg) {
        super(msg);
    }

    public CustomDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

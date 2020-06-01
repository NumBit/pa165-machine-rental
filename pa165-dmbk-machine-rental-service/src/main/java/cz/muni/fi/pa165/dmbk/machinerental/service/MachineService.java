package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Provides definitions for functions used in Machine Service
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Service
public interface MachineService {
    /**
     * Persist machine
     * @param machine machine entity
     * @return Long id
     */
    Long persistMachine(Machine machine);

    /**
     * Find machine by id
     * @param id long
     * @return machine
     */
    Optional<Machine> findById(Long id);

    /**
     * Returns all machines
     * @return machine list
     */
    List<Machine> findAll();

    /**
     * Finds machine by exact name
     * @param name string
     * @return machine list
     */
    List<Machine> findByName(String name);

    /**
     * Finds by machine name like
     * @param name string
     * @return machine list
     */
    List<Machine> findByNameLike(String name);

    /**
     * Find by manufacturer name
     * @param manufacturer string
     * @return machine list
     */
    List<Machine> findByManufacturer(String manufacturer);

    /**
     * Find by manufacturer name like
     * @param manufacturer string
     * @return machine list
     */
    List<Machine> findByManufacturerLike(String manufacturer);

    /**
     * Delete machine by id
     * @param id of machine to be deleted
     */
    void deleteMachineById(Long id);
}

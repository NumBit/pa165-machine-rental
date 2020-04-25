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
    Long persistMachine(Machine machine);
    Optional<Machine> findById(Long id);
    List<Machine> findByName(String name);
    List<Machine> findByNameLike(String name);
    List<Machine> findByManufacturer(String manufacturer);
    List<Machine> findByManufacturerLike(String manufacturer);
    void deleteMachineById(Long id);
}

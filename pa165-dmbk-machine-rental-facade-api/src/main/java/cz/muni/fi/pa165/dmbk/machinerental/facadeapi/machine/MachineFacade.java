package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine;

import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;

import java.util.List;
import java.util.Optional;

/**
 * Provides definitions for functions used in Machine Facade
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

public interface MachineFacade {
    Long persistMachine(MachineDto machine);

    Optional<MachineDto> findById(Long id);

    List<MachineDto> findAll();

    List<MachineDto> findByName(String name);
    List<MachineDto> findByNameLike(String name);
    List<MachineDto> findByManufacturer(String manufacturer);
    List<MachineDto> findByManufacturerLike(String manufacturer);
    boolean deleteMachineById(Long id);
}

package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.LegalForm;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.AdminDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.UserDto;

import java.util.List;
import java.util.Optional;

/**
 * Provides definitions for functions used in Machine Facade
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

public interface MachineFacade {
    Long persistMachine(MachineDto machine);
    List<MachineDto> findByName(String name);
    List<MachineDto> findByNameLike(String name);
    List<MachineDto> findByManufacturer(String manufacturer);
    List<MachineDto> findByManufacturerLike(String manufacturer);
    void deleteMachineById(Long id);
}

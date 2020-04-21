package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.MachineFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of machine facade interface
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Component
@Transactional(rollbackFor = CustomDataAccessException.class)
public class MachineFacadeImpl implements MachineFacade {

    @Autowired
    private MachineService machineService;

    @Override
    public void persistMachine(MachineDto machine) {
        //machineService.persistMachine(machine));
    }

    @Override
    public List<Machine> findByName(String name) {
        return null;
    }

    @Override
    public List<Machine> findByNameLike(String name) {
        return null;
    }

    @Override
    public List<Machine> findByManufacturer(String manufacturer) {
        return null;
    }

    @Override
    public List<Machine> findByManufacturerLike(String manufacturer) {
        return null;
    }

    @Override
    public void deleteMachineById(Long id) {

    }
}

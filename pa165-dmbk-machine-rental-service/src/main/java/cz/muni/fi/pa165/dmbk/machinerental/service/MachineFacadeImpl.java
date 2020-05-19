package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.MachineFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of machine facade interface
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Component
@Transactional(rollbackFor = CustomDataAccessException.class)
public class MachineFacadeImpl implements MachineFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private MachineService machineService;

    @Override
    public Long persistMachine(MachineDto machine) {
        return machineService.persistMachine(beanMappingService.mapTo(machine, Machine.class));
    }

    @Override
    public Optional<MachineDto> findById(Long id) {
        var machine = machineService.findById(id);
        return machine.map(value -> beanMappingService.mapTo(value, MachineDto.class));
    }

    @Override
    public List<MachineDto> findAll() {
        return beanMappingService.mapTo(machineService.findAll(), MachineDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineDto> findByName(String name) {
        return beanMappingService.mapTo(machineService.findByName(name), MachineDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineDto> findByNameLike(String name) {
        return beanMappingService.mapTo(machineService.findByNameLike(name), MachineDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineDto> findByManufacturer(String manufacturer) {
        return beanMappingService.mapTo(machineService.findByManufacturer(manufacturer), MachineDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MachineDto> findByManufacturerLike(String manufacturer) {
        return beanMappingService.mapTo(machineService.findByManufacturerLike(manufacturer), MachineDto.class);
    }

    @Override
    public void deleteMachineById(Long id) {
        machineService.deleteMachineById(id);
    }
}

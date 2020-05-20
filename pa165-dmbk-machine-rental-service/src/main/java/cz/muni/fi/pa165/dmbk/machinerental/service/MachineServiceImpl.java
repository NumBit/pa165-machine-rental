package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Implementation of machine service interface
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Service
public class MachineServiceImpl implements MachineService {

    @Autowired
    private MachineRepository machineRepository;

    @Override
    public Long persistMachine(Machine machine) {
        return exceptionCatcher(() -> machineRepository.save(machine)).getId();
    }

    @Override
    public List<Machine> findAll() {
        return machineRepository.findAll();
    }

    @Override
    public Optional<Machine> findById(Long id) {
        return machineRepository.findById(id);
    }

    @Override
    public List<Machine> findByName(String name) {
        return machineRepository.findByName(name);
    }

    @Override
    public List<Machine> findByNameLike(String name) {
        return machineRepository.findByNameContaining(name);
    }

    @Override
    public List<Machine> findByManufacturer(String manufacturer) {
        return machineRepository.findByManufacturer(manufacturer);
    }

    @Override
    public List<Machine> findByManufacturerLike(String manufacturer) {
        return machineRepository.findByManufacturerContaining(manufacturer);
    }

    @Override
    public void deleteMachineById(Long id) {
        machineRepository.deleteById(id);
    }

    private static <T> T exceptionCatcher(Callable<T> thrower) {
        try { return thrower.call(); }
        catch (Exception exception) {
            throw new CustomDataAccessException(exception.getMessage(), exception);
        }
    }
}

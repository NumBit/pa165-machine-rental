package cz.muni.fi.pa165.dmbk.machinerental.service.machine;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.MachineRepository;
import cz.muni.fi.pa165.dmbk.machinerental.service.CustomDataAccessException;
import cz.muni.fi.pa165.dmbk.machinerental.service.MachineService;
import cz.muni.fi.pa165.dmbk.machinerental.service.MachineServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Norbert Dopjera 456355
 */
@RunWith(SpringRunner.class)
public class MachineServiceTest {

    @MockBean private MachineRepository machineRepository;
    @Autowired private MachineService machineService;

    @TestConfiguration
    static class MachineServiceTestContextConfiguration {
        @Bean public MachineService machineFacade() {
            return new MachineServiceImpl();
        }
    }

    @Test
    public void saveMachine() {
        var machine = getSimpleMachineDao();
        var persistedMachine = getSimpleMachineDao();
        persistedMachine.setId(2L);
        when(machineRepository.save(any(Machine.class)))
                .thenReturn(persistedMachine);

        var machineId = machineService.persistMachine(machine);
        Assert.assertNotNull(machineId);
        Assert.assertEquals(Long.valueOf(2), machineId);
    }

    @Test
    public void dataAccessException() {
        when(machineRepository.save(null))
                .thenThrow(new InvalidJpaQueryMethodException("message"));
        // in case of any exception on data layer custom data exception must be thrown
        assertThrows(CustomDataAccessException.class, () -> machineService.persistMachine(null));
    }

    @Test
    public void findByName() {
        when(machineRepository.findByName(any(String.class)))
                .thenReturn(List.of(getMachineDaoWithId()));

        var foundMachines = machineService.findByName("machineName");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    @Test
    public void findByMachineNameLike() {
        when(machineRepository.findByNameLike(any(String.class)))
                .thenReturn(List.of(getMachineDaoWithId()));
        var foundMachines = machineService.findByNameLike("machineName");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    @Test
    public void findByManufacturer() {
        when(machineRepository.findByManufacturer(any(String.class)))
                .thenReturn(List.of(getMachineDaoWithId()));
        var foundMachines = machineService.findByManufacturer("machineManufacturer");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    @Test
    public void findByManufacturerLike() {
        when(machineRepository.findByManufacturerLike(any(String.class)))
                .thenReturn(List.of(getMachineDaoWithId()));
        var foundMachines = machineService.findByManufacturerLike("machineManufacturer");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    private Machine getSimpleMachineDao() {
        Machine simpleMachine = new Machine();
        simpleMachine.setId(null);
        simpleMachine.setName("machineName");
        simpleMachine.setDescription("machineDescription");
        simpleMachine.setManufacturer("machineManufacturer");
        simpleMachine.setPrice(new BigDecimal(0));
        return simpleMachine;
    }

    private Machine getMachineDaoWithId() {
        Machine simpleMachine = new Machine();
        simpleMachine.setId(2L);
        simpleMachine.setName("machineName");
        simpleMachine.setDescription("machineDescription");
        simpleMachine.setManufacturer("machineManufacturer");
        simpleMachine.setPrice(new BigDecimal(0));
        return simpleMachine;
    }
}

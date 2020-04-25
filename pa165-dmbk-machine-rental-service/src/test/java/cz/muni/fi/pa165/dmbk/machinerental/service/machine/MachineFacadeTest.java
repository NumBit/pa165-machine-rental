package cz.muni.fi.pa165.dmbk.machinerental.service.machine;

import com.github.dozermapper.core.Mapper;
import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.MachineFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingService;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingServiceImpl;
import cz.muni.fi.pa165.dmbk.machinerental.service.MachineFacadeImpl;
import cz.muni.fi.pa165.dmbk.machinerental.service.MachineService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Norbert Dopjera 456355
 */
@RunWith(SpringRunner.class)
public class MachineFacadeTest {

    @MockBean private MachineService machineService;
    @Autowired private BeanMappingService beanMappingService;
    @Autowired private MachineFacade machineFacade;
    @MockBean private Mapper dozer;

    @TestConfiguration
    static class MachineFacadeImplTestContextConfiguration {

        @Bean public BeanMappingService beanMappingService() {
            return new BeanMappingServiceImpl();
        }

        @Bean public MachineFacade machineFacade() {
            return new MachineFacadeImpl();
        }
    }

    @Test
    public void persistMachine() {
        MachineDto machineDto = new MachineDto(null, "machine", "description",
                                               "manufacturer", new BigDecimal(0));
        when(machineService.persistMachine(any(Machine.class)))
                .thenReturn(2L);
        var createdMachine = machineFacade.persistMachine(machineDto);
        assertThat(createdMachine.equals(2L));
    }

    @Test
    public void findByMachineName() {
        when(machineService.findByName(any(String.class)))
                .thenReturn(List.of(getSimpleMachineDao()));
        var foundMachines = machineFacade.findByName("machineName");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    @Test
    public void findByMachineNameLike() {
        when(machineService.findByNameLike(any(String.class)))
                .thenReturn(List.of(getSimpleMachineDao()));
        var foundMachines = machineFacade.findByNameLike("machineName");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    @Test
    public void findByManufacturer() {
        when(machineService.findByManufacturer(any(String.class)))
                .thenReturn(List.of(getSimpleMachineDao()));
        var foundMachines = machineFacade.findByManufacturer("machineManufacturer");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    @Test
    public void findByManufacturerLike() {
        when(machineService.findByManufacturerLike(any(String.class)))
                .thenReturn(List.of(getSimpleMachineDao()));
        var foundMachines = machineFacade.findByManufacturerLike("machineManufacturer");
        Assert.assertEquals(1, foundMachines.size());
        Assert.assertEquals(Long.valueOf(2), foundMachines.get(0).getId());
    }

    private Machine getSimpleMachineDao() {
        Machine simpleMachine = new Machine();
        simpleMachine.setId(2L);
        simpleMachine.setName("machineName");
        simpleMachine.setDescription("machineDescription");
        simpleMachine.setManufacturer("machineManufacturer");
        simpleMachine.setPrice(new BigDecimal(0));
        return simpleMachine;
    }
}

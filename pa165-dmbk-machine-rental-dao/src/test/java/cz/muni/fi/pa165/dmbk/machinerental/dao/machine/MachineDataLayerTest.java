package cz.muni.fi.pa165.dmbk.machinerental.dao.machine;

import com.sun.istack.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * Test for machine DAO
 * @author Lukas Krekan
 * **/
@RunWith(SpringRunner.class)
@DataJpaTest
public class MachineDataLayerTest {

    @Autowired
    private MachineRepository machineRepository;

    @Before
    public void setUp() {
        var machine = machineRepository.saveAndFlush(getMachine("1"));
        storedMachineId = machine.getId();
    }

    private static Long storedMachineId;

    @Test
    @Rollback
    public void createMachine(){
        var machine = machineRepository.saveAndFlush(getMachine("2"));

        Assert.assertEquals(2L, machineRepository.count());

        var optionalMachine = machineRepository.findById(machine.getId());
        Assert.assertTrue(optionalMachine.isPresent());
        var foundMachine = optionalMachine.get();

        Assert.assertEquals(machine.getId(), foundMachine.getId());
        Assert.assertEquals("description2", foundMachine.getDescription());
        Assert.assertEquals("manufacturer2", foundMachine.getManufacturer());
        Assert.assertEquals("machine2", foundMachine.getName());
        Assert.assertEquals(new BigDecimal(2),foundMachine.getPrice());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void createMachineWithNull() {
        Machine machine = getMachineWithNull();
        machineRepository.saveAndFlush(machine);
    }

    @Test
    @Rollback
    public void createMachineWithEmpty() {
        var machine = new Machine();
        machine.setId(null);
        machine.setName("");
        machine.setManufacturer("manufacturer");
        machine.setDescription("description");
        machine.setPrice(new BigDecimal(1));

        machineRepository.saveAndFlush(machine);

        var optionalMachine = machineRepository.findById(machine.getId());
        Assert.assertTrue(optionalMachine.isPresent());
    }


    @Test
    @Rollback
    public void findById() {
        var optionalMachine = machineRepository.findById(storedMachineId);
        Assert.assertTrue(optionalMachine.isPresent());
    }

    @Test
    @Rollback
    public void findByIdNegative() {
        var optionalMachine = machineRepository.findById(-1L);
        Assert.assertTrue(optionalMachine.isEmpty());
    }

    @Test
    @Rollback
    public void updateMachine(){
        var machine = getMachine("2");
        machine.setId(storedMachineId);

        //check before update
        var optionalMachine = machineRepository.findById(storedMachineId);
        Assert.assertTrue(optionalMachine.isPresent());
        var foundMachine = optionalMachine.get();
        Assert.assertEquals("machine1", foundMachine.getName());

        //update
        Assert.assertEquals(1L, machineRepository.count());
        machineRepository.saveAndFlush(machine);

        //check after update
        Assert.assertEquals(1L, machineRepository.count());
        Assert.assertEquals(machine, optionalMachine.get());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void updateMachineWithNull() {
        var machine = getMachineWithNull();
        machine.setId(storedMachineId);

        machineRepository.saveAndFlush(machine);
    }

    @Test
    @Rollback
    public void removeMachineById() {
        var machine = getMachine("2");
        machineRepository.saveAndFlush(machine);
        machineRepository.deleteById(storedMachineId);

        Assert.assertFalse(machineRepository.findAll().isEmpty());

        var optionalMachine = machineRepository.findById(machine.getId());
        Assert.assertTrue(optionalMachine.isPresent());
        Assert.assertEquals(1L, machineRepository.count());
        Assert.assertEquals(machine, optionalMachine.get());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    @Rollback
    public void removeMachineByIdNegative() {
        machineRepository.deleteById(-1L);
    }

    @Test
    @Rollback
    public void removeAllMachines() {
        var machine = getMachine("2");
        machineRepository.saveAndFlush(machine);

        machineRepository.deleteAll();
        Assert.assertEquals(0L, machineRepository.count());
    }

    @Test
    @Rollback
    public void findByName(){
        var optionalMachine = machineRepository.findByName("machine1");
        Assert.assertEquals(1L, optionalMachine.size());
        Assert.assertTrue(machineRepository.findById(storedMachineId).isPresent());
        var storedMachine = machineRepository.findById(storedMachineId).get();
        Assert.assertEquals(optionalMachine.get(0), storedMachine);

        var optionalMachineNonExist = machineRepository.findByName("maxine1");
        Assert.assertEquals(0L, optionalMachineNonExist.size());
    }

    @Test
    @Rollback
    public void findByManufacturer() {
        var optionalMachine = machineRepository.findByManufacturer("manufacturer1");
        Assert.assertEquals(1L, optionalMachine.size());
        Assert.assertTrue(machineRepository.findById(storedMachineId).isPresent());
        var storedMachine = machineRepository.findById(storedMachineId).get();
        Assert.assertEquals(optionalMachine.get(0), storedMachine);

        var optionalMachineNonExist = machineRepository.findByManufacturer("masdeiasdi1");
        Assert.assertEquals(0L, optionalMachineNonExist.size());
    }


    @Test
    @Rollback
    public void findByNameLike() {
        var machine2 = getMachine("2");
        var machine22 = getMachine("22");

        machineRepository.saveAndFlush(machine2);
        machineRepository.saveAndFlush(machine22);

        var optionalResultMachine = machineRepository.findByNameContaining("machine");
        Assert.assertEquals(3L, optionalResultMachine.size());

        var optionalResultNumber = machineRepository.findByNameContaining("2");
        Assert.assertEquals(2L,optionalResultNumber.size());

        var optionalResultNonExist = machineRepository.findByNameContaining("bib");
        Assert.assertEquals(0L, optionalResultNonExist.size());

    }

    @Test
    @Rollback
    public void findByManufacturerLike() {
        var machine2 = getMachine("2");
        var machine22 = getMachine("22");

        machineRepository.saveAndFlush(machine2);
        machineRepository.saveAndFlush(machine22);

        var optionalResultManufacturer = machineRepository.findByManufacturerContaining("manufacturer");
        Assert.assertEquals(3L, optionalResultManufacturer.size());

        var optionalResultNumber = machineRepository.findByManufacturerContaining("2");
        Assert.assertEquals(2L,optionalResultNumber.size());

        var optionalResultNonExist = machineRepository.findByManufacturerContaining("bib");
        Assert.assertEquals(0L, optionalResultNonExist.size());
    }

    private Machine getMachine(@NotNull String appendix) {
        Machine machine = new Machine();
        machine.setId(null);
        machine.setName("machine" + appendix);
        machine.setManufacturer("manufacturer" + appendix);
        machine.setDescription("description" + appendix);
        machine.setPrice(new BigDecimal(appendix));
        return machine;
    }

    private Machine getMachineWithNull() {
        var machine = new Machine();
        machine.setId(null);
        machine.setName(null);
        machine.setManufacturer("manufacturer");
        machine.setDescription("description");
        machine.setPrice(new BigDecimal(1));
        return machine;
    }

}

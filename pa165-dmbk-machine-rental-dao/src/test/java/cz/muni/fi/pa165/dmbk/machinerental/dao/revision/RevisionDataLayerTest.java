package cz.muni.fi.pa165.dmbk.machinerental.dao.revision;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.MachineRepository;
import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.repository.RevisionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Test revision entity and its repository
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class RevisionDataLayerTest {

    private static Long storedMachineId;
    private static Long storedRevisionId;
    private static Machine storedMachine;
    private static Revision storedRevision;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private RevisionRepository revisionRepository;

    @Before
    public void setUp() {
        var machine = machineRepository.saveAndFlush(getNewMachine("Machine 3000", "33.59"));
        var revision = revisionRepository.saveAndFlush(getNewRevision(machine));
        storedMachine = machine;
        storedRevision = revision;
        storedMachineId = machine.getId();
        storedRevisionId = revision.getId();
    }

    //region CRUD tests

    @Test
    @Rollback
    public void createRevision() {
        Assert.assertEquals(1, machineRepository.findAll().size());
        Assert.assertEquals(1, revisionRepository.findAll().size());

        var machine = machineRepository.saveAndFlush(getNewMachine("Trusty Hammer", "495.55"));

        Assert.assertNotNull(machine.getId());
        Assert.assertEquals(new BigDecimal("495.55"), machine.getPrice());

        Assert.assertEquals(2, machineRepository.findAll().size());
        Assert.assertEquals(1, revisionRepository.findAll().size());
        Assert.assertEquals(machine, machineRepository.findById(machine.getId()).get());

        var revision = revisionRepository.saveAndFlush(getNewRevision(machine));

        Assert.assertNotNull(revision.getId());
        Assert.assertEquals(2, revisionRepository.findAll().size());
        Assert.assertEquals(revision, revisionRepository.findById(revision.getId()).get());
    }

    @Test
    public void readRevision() {
        var revision = revisionRepository.findById(storedRevisionId);
        var machine = machineRepository.findById(storedMachineId);

        Assert.assertTrue(machine.isPresent());
        Assert.assertTrue(revision.isPresent());
        Assert.assertEquals(storedMachine, revision.get().getMachine());
        Assert.assertEquals(storedRevision, revision.get());
    }

    @Test
    @Rollback
    public void updateRevision() {
        var revision = revisionRepository.findById(storedRevisionId);
        Assert.assertTrue(revision.isPresent());

        var newNote = "This is changed note.";
        revision.get().setNote(newNote);
        var revisionTmp = revisionRepository.findById(storedRevisionId);

        // check for no changes until save and flush
        Assert.assertTrue(revisionTmp.isPresent());
        Assert.assertEquals(storedRevision, revisionTmp.get());
        Assert.assertEquals(revision.get(), revisionTmp.get());

        // save and flush and check correct update
        revisionRepository.saveAndFlush(revision.get());
        revision = revisionRepository.findById(storedRevisionId);
        Assert.assertTrue(revision.isPresent());
        Assert.assertEquals(newNote, revision.get().getNote());
    }

    @Test
    @Rollback
    public void deleteRevision() {
        Assert.assertEquals(1, machineRepository.findAll().size());
        Assert.assertEquals(1, revisionRepository.findAll().size());

        revisionRepository.deleteById(storedRevisionId);

        //check if deleted and machine not affected
        Assert.assertEquals(1, machineRepository.findAll().size());
        Assert.assertEquals(0, revisionRepository.findAll().size());
    }

    //endregion

    //region Repository find tests

    @Test
    public void findRevisionByNotExistingId() {
        var revision = revisionRepository.findById(-1L);
        Assert.assertTrue(revision.isEmpty());
    }

    @Test
    @Rollback
    public void findAllRevisionsByMachineId() {
        revisionRepository.saveAndFlush(getNewRevision(storedMachine));
        revisionRepository.saveAndFlush(getNewRevision(storedMachine));
        var revisions = revisionRepository.findAllByMachineId(storedMachineId);
        Assert.assertEquals(3, revisions.size());
    }

    @Test
    @Rollback
    public void findAllByDate() {
        var newRevision = getNewRevision(storedMachine);
        var date = LocalDate.of(2018, 8, 10);
        newRevision.setRevisionDate(date);
        revisionRepository.saveAndFlush(newRevision);
        var foundByDate = revisionRepository.findAllByRevisionDate(date);
        Assert.assertEquals(1, foundByDate.size());
    }

    @Test
    @Rollback
    public void findAllByDateAfter() {
        var newRevision = getNewRevision(storedMachine);
        var date = LocalDate.of(2018, 8, 10);
        var dateAfter = LocalDate.of(2017, 5, 5);
        var dateLate = LocalDate.of(2019, 5, 5);
        newRevision.setRevisionDate(date);
        revisionRepository.saveAndFlush(newRevision);
        var foundByDateAfter = revisionRepository.findAllByRevisionDateAfter(dateAfter);
        Assert.assertEquals(1, foundByDateAfter.size());
        foundByDateAfter = revisionRepository.findAllByRevisionDateAfter(dateLate);
        Assert.assertEquals(0, foundByDateAfter.size());
    }

    @Test
    @Rollback
    public void findAllByDateBefore() {
        var newRevision = getNewRevision(storedMachine);
        var date = LocalDate.of(2018, 8, 10);
        var dateBefore = LocalDate.of(2017, 5, 5);
        var dateLate = LocalDate.of(2010, 5, 5);
        newRevision.setRevisionDate(date);
        revisionRepository.saveAndFlush(newRevision);
        var foundByDateBefore = revisionRepository.findAllByRevisionDateBefore(dateBefore);
        Assert.assertEquals(1, foundByDateBefore.size());
        foundByDateBefore = revisionRepository.findAllByRevisionDateBefore(dateLate);
        Assert.assertEquals(0, foundByDateBefore.size());
    }

    @Test
    @Rollback
    public void findAllByDateBetween() {
        var newRevision = getNewRevision(storedMachine);
        var date = LocalDate.of(2018, 8, 10);
        var dateBefore = LocalDate.of(2014, 5, 5);
        var dateAfter = LocalDate.of(2019, 10, 2);
        var dateLate = LocalDate.of(2010, 7, 17);
        newRevision.setRevisionDate(date);
        revisionRepository.saveAndFlush(newRevision);
        var foundByDateBetween = revisionRepository.findAllByRevisionDateBetween(dateBefore, dateAfter);
        Assert.assertEquals(2, foundByDateBetween.size());
        foundByDateBetween = revisionRepository.findAllByRevisionDateBetween(dateBefore, dateLate);
        Assert.assertEquals(0, foundByDateBetween.size());
    }

    //endregion

    private Revision getNewRevision(Machine machine) {
        var revision = new Revision();
        revision.setId(null);
        revision.setMachine(machine);
        revision.setRevisionDate(LocalDate.of(2015, 6, 26));
        revision.setRevisionTime(Time.valueOf(LocalTime.of(13, 52, 21)));
        revision.setNote("Some generic test about revision details");
        return revision;
    }

    private Machine getNewMachine(String name, String value) {
        var machine = new Machine();
        machine.setId(null);
        machine.setName(name);
        machine.setDescription("This machine will be best for you!");
        machine.setManufacturer("Machinerum Corp");
        machine.setPrice(new BigDecimal(value));
        return machine;
    }
}

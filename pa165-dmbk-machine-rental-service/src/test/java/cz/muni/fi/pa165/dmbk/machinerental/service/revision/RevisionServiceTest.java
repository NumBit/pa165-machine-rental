package cz.muni.fi.pa165.dmbk.machinerental.service.revision;

import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.repository.RevisionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Peter Baltazarovič
 */
@RunWith(SpringRunner.class)
public class RevisionServiceTest {
    @MockBean
    private RevisionRepository revisionRepository;
    @Autowired
    private RevisionService revisionService;

    private final static Time TEST_TIME = Time.valueOf(LocalTime.now());
    private final static LocalDate TEST_DATE = LocalDate.now();

    @MockBean
    private Revision mockedRevision;
    private Revision revision;
    private Revision persistedRevision;

    @TestConfiguration
    static class RevisionServiceTestContextConfiguration {
        @Bean public RevisionService revisionService() {
            return new RevisionServiceImpl();
        }
    }

    @Before
    public void init() {

        revision = getNewRevision();
        persistedRevision = getNewRevision();
        persistedRevision.setId(1L);
    }


    @Test
    public void createRevision() {
        when(revisionRepository.saveAndFlush(any(Revision.class))).thenReturn(persistedRevision);
        var repositoryId = revisionService.createRevision(revision);
        Assert.assertEquals(1L, (long) repositoryId);
    }

    @Test
    public void updateRevision() {
        revisionService.updateRevision(persistedRevision);
        verify(revisionRepository, times(1)).save(persistedRevision);
    }

    @Test
    public void findById() {
        when(revisionRepository.findById(1L)).thenReturn(Optional.of(persistedRevision));
        var foundRevision = revisionService.findById(1L);
        Assert.assertTrue(foundRevision.isPresent());
        Assert.assertEquals(1L, (long) foundRevision.get().getId());
    }

    @Test
    public void findByIdNotPresent() {
        when(revisionRepository.findById(2L)).thenReturn(Optional.empty());
        var foundRevision = revisionService.findById(2L);
        Assert.assertTrue(foundRevision.isEmpty());
    }

    @Test
    public void findAll() {
        var revisionsList = List.of(persistedRevision, revision);
        when(revisionRepository.findAll()).thenReturn(revisionsList);
        var allRevisions = revisionService.findAll();
        Assert.assertFalse(allRevisions.isEmpty());
        Assert.assertEquals(2L, allRevisions.size());
        Assert.assertTrue(allRevisions.contains(persistedRevision));
    }

    @Test
    public void findAllByMachineId() {
        var revisionsList = List.of(persistedRevision, revision);
        when(revisionRepository.findAllByMachineId(1L)).thenReturn(revisionsList);
        var allRevisions = revisionService.findAllByMachineId(1L);
        Assert.assertFalse(allRevisions.isEmpty());
        Assert.assertEquals(2L, allRevisions.size());
        Assert.assertTrue(allRevisions.contains(persistedRevision));
    }

    @Test
    public void findAllByDate() {
        when(revisionRepository.findAllByRevisionDate(any(LocalDate.class))).thenReturn(List.of(persistedRevision));
        var allRevisions = revisionService.findAllByDate(TEST_DATE);
        Assert.assertFalse(allRevisions.isEmpty());
        Assert.assertEquals(1L, allRevisions.size());
        Assert.assertTrue(allRevisions.contains(persistedRevision));
    }

    @Test
    public void findAllByDateAfter() {
        when(revisionRepository.findAllByRevisionDateAfter(any(LocalDate.class))).thenReturn(List.of(persistedRevision));
        var allRevisions = revisionService.findAllByDateAfter(TEST_DATE);
        Assert.assertFalse(allRevisions.isEmpty());
        Assert.assertEquals(1L, allRevisions.size());
        Assert.assertTrue(allRevisions.contains(persistedRevision));
    }

    @Test
    public void findAllByDateBefore() {
        when(revisionRepository.findAllByRevisionDateBefore(any(LocalDate.class))).thenReturn(List.of(persistedRevision));
        var allRevisions = revisionService.findAllByDateBefore(TEST_DATE);
        Assert.assertFalse(allRevisions.isEmpty());
        Assert.assertEquals(1L, allRevisions.size());
        Assert.assertTrue(allRevisions.contains(persistedRevision));
    }

    @Test
    public void findAllByDateBetween() {
        when(revisionRepository.findAllByRevisionDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(persistedRevision));
        var allRevisions = revisionService.findAllByDateBetween(TEST_DATE, TEST_DATE);
        Assert.assertFalse(allRevisions.isEmpty());
        Assert.assertEquals(1L, allRevisions.size());
        Assert.assertTrue(allRevisions.contains(persistedRevision));
    }

    @Test
    public void deleteById() {
        revisionService.deleteById(1L);
        verify(revisionRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteAll() {
        revisionService.deleteAll();
        verify(revisionRepository, times(1)).deleteAll();
    }

    @Test
    public void setRevisionDate() {
        when(revisionRepository.findById(1L)).thenReturn(Optional.of(mockedRevision));
        revisionService.setRevisionDate(1L, TEST_DATE);
        verify(revisionRepository, times(1)).findById(1L);
        verify(revisionRepository, times(1)).saveAndFlush(mockedRevision);
        verify(mockedRevision, times(1)).setRevisionDate(TEST_DATE);
    }

    @Test
    public void setRevisionNote() {
        when(revisionRepository.findById(1L)).thenReturn(Optional.of(mockedRevision));
        var note = "TestNote";
        revisionService.setRevisionNote(1L, note);
        verify(revisionRepository, times(1)).findById(1L);
        verify(revisionRepository, times(1)).saveAndFlush(mockedRevision);
        verify(mockedRevision, times(1)).setNote(note);
    }


    private Revision getNewRevision(){
        var newRevision = new Revision();
        newRevision.setNote("note");
        newRevision.setRevisionDate(TEST_DATE);
        newRevision.setRevisionTime(TEST_TIME);
        return newRevision;
    }
}

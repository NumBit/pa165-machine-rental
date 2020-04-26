package cz.muni.fi.pa165.dmbk.machinerental.service.revision;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.RevisionFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
public class RevisionFacadeTest {

    @MockBean
    private RevisionService revisionService;
    @Autowired private RevisionFacade revisionFacade;

    private final static LocalDate TEST_DATE = LocalDate.now();

    private Revision revision;
    private RevisionDto revisionDto;
    private RevisionDto persistedRevisionDto;
    private RevisionCreateDto revisionCreateDto;
    private MachineDto machineDto;

    @TestConfiguration
    static class MachineFacadeImplTestContextConfiguration {

        @Bean
        public BeanMappingService beanMappingService() {
            return new BeanMappingServiceImpl();
        }
        @Bean
        public RevisionFacade revisionFacade() {
            return new RevisionFacadeImpl();
        }
        @Bean
        public Mapper dozer() {return DozerBeanMapperBuilder.buildDefault();}
    }

    @Before
    public void setUp() {
        machineDto = getNewMachineDto();
        revision = getNewRevision();
        revisionDto = getNewRevisionDto();
        revisionCreateDto = getNewRevisionCreateDto();
        persistedRevisionDto = getNewRevisionDto();
        persistedRevisionDto.setId(1L);
    }

    @Test
    public void createRevision() {
        when(revisionService.createRevision(any(Revision.class))).thenReturn(1L);
        var revisionId = revisionFacade.createRevision(revisionCreateDto);
        Assert.assertEquals(1L, (long) revisionId);
        verify(revisionService, times(1)).createRevision(any(Revision.class));
    }

    @Test
    public void findById() {
        when(revisionService.findById(1L)).thenReturn(Optional.of(revision));
        var foundRevision = revisionFacade.findById(1L);
        Assert.assertTrue(foundRevision.isPresent());
        Assert.assertEquals(1L, (long) foundRevision.get().getId());
        verify(revisionService, times(1)).findById(1L);
    }

    @Test
    public void findAll() {
        when(revisionService.findAll()).thenReturn(List.of(revision));
        var foundRevisions = revisionFacade.findAll();
        Assert.assertFalse(foundRevisions.isEmpty());
        Assert.assertTrue(foundRevisions.contains(persistedRevisionDto));
        verify(revisionService, times(1)).findAll();
    }


    @Test
    public void findAllByMachineId() {
        when(revisionService.findAllByMachineId(1L)).thenReturn(List.of(revision));
        var foundRevisions = revisionFacade.findAllByMachineId(1L);
        Assert.assertFalse(foundRevisions.isEmpty());
        Assert.assertTrue(foundRevisions.contains(persistedRevisionDto));
        verify(revisionService, times(1)).findAllByMachineId(1L);

    }

    @Test
    public void findAllByDate() {
        when(revisionService.findAllByDate(any(LocalDate.class))).thenReturn(List.of(revision));
        var foundRevisions = revisionFacade.findAllByDate(TEST_DATE);
        Assert.assertFalse(foundRevisions.isEmpty());
        Assert.assertTrue(foundRevisions.contains(persistedRevisionDto));
        verify(revisionService, times(1)).findAllByDate(any(LocalDate.class));
    }

    @Test
    public void findAllByDateAfter() {
        when(revisionService.findAllByDateAfter(any(LocalDate.class))).thenReturn(List.of(revision));
        var foundRevisions = revisionFacade.findAllByDateAfter(TEST_DATE);
        Assert.assertFalse(foundRevisions.isEmpty());
        Assert.assertTrue(foundRevisions.contains(persistedRevisionDto));
        verify(revisionService, times(1)).findAllByDateAfter(any(LocalDate.class));
    }

    @Test
    public void findAllByDateBefore() {
        when(revisionService.findAllByDateBefore(any(LocalDate.class))).thenReturn(List.of(revision));
        var foundRevisions = revisionFacade.findAllByDateBefore(TEST_DATE);
        Assert.assertFalse(foundRevisions.isEmpty());
        Assert.assertTrue(foundRevisions.contains(persistedRevisionDto));
        verify(revisionService, times(1)).findAllByDateBefore(any(LocalDate.class));
    }

    @Test
    public void findAllByDateBetween() {
        when(revisionService.findAllByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(revision));
        var foundRevisions = revisionFacade.findAllByDateBetween(TEST_DATE, TEST_DATE);
        Assert.assertFalse(foundRevisions.isEmpty());
        Assert.assertTrue(foundRevisions.contains(persistedRevisionDto));
        verify(revisionService, times(1)).findAllByDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    public void deleteById() {
        revisionFacade.deleteById(1L);
        verify(revisionService, times(1)).deleteById(1L);
    }

    @Test
    public void deleteAll() {
        revisionFacade.deleteAll();
        verify(revisionService, times(1)).deleteAll();
    }

    @Test
    public void setRevisionDate() {
        revisionFacade.setRevisionDate(1L, TEST_DATE);
        verify(revisionService, times(1)).setRevisionDate(1L, TEST_DATE);
    }

    @Test
    public void setRevisionNote() {
        var note = "TestNote";
        revisionFacade.setRevisionNote(1L, note);
        verify(revisionService, times(1)).setRevisionNote(1L, note);
    }


    private Revision getNewRevision(){
        var newRevision = new Revision();
        newRevision.setId(1L);
        newRevision.setNote("note");
        newRevision.setRevisionDate(TEST_DATE);
        newRevision.setRevisionTime(Time.valueOf(LocalTime.now()));
        return newRevision;
    }

    private RevisionDto getNewRevisionDto(){
        var newRevisionDto = new RevisionDto();
        newRevisionDto.setNote("note");
        newRevisionDto.setRevisionDate(TEST_DATE);
        newRevisionDto.setRevisionTime(Time.valueOf(LocalTime.now()));
        return newRevisionDto;
    }

    private RevisionCreateDto getNewRevisionCreateDto(){
        return new RevisionCreateDto(TEST_DATE, Time.valueOf(LocalTime.now()), machineDto, "note");
    }
    private MachineDto getNewMachineDto(){
        return MachineDto.builder().withDescription("description").withManufacturer("manufacturer").withName("name").withPrice(new BigDecimal(100)).withId(1L).build();
    }

}

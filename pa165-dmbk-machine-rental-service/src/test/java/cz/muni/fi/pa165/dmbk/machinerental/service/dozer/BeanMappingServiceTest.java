package cz.muni.fi.pa165.dmbk.machinerental.service.dozer;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Peter Baltazarovič
 */
@RunWith(SpringRunner.class)
public class BeanMappingServiceTest {

    @Autowired
    private BeanMappingService beanMappingService;
    private final static Time TEST_TIME = Time.valueOf(LocalTime.now());
    private final static LocalDate TEST_DATE = LocalDate.now();
    private Revision revision;
    private RevisionDto revisionDto;
    private RevisionCreateDto revisionCreateDto;
    private Machine machine;
    private MachineDto machineDto;

    @Before
    public void init() {
        machineDto = getNewMachineDto();
        machine = getNewMachine();
        revision = getNewRevision();
        revisionDto = getNewRevisionDto();
        revisionCreateDto = getNewRevisionCreateDto();
    }


    @TestConfiguration
    static class MachineFacadeImplTestContextConfiguration {

        @Bean
        public BeanMappingService beanMappingService() {
            return new BeanMappingServiceImpl();
        }
        @Bean
        public Mapper dozer() {return DozerBeanMapperBuilder.buildDefault();}
    }

    @Test
    public void mappingRevisionToRevisionDto() {
        var mappedRevisionDto = beanMappingService.mapTo(revision, RevisionDto.class);
        Assert.assertEquals(mappedRevisionDto, revisionDto);
    }

    @Test
    public void mappingRevisionDtoToRevision() {
        var mappedRevision = beanMappingService.mapTo(revisionDto, Revision.class);
        Assert.assertEquals(mappedRevision, revision);
    }

    @Test
    public void mappingRevisionCreateDtoToRevision() {
        var mappedRevision = beanMappingService.mapTo(revisionCreateDto, Revision.class);
        var revisionWithoutId = getNewRevision();
        revisionWithoutId.setId(null);
        revisionWithoutId.setMachine(machine);
        Assert.assertEquals(mappedRevision, revisionWithoutId);
    }

    @Test
    public void mappingRevisionsListToRevisionDtosList() {
        var mappedRevisionDtosList = beanMappingService.mapTo(List.of(revision), RevisionDto.class);
        Assert.assertEquals(mappedRevisionDtosList, List.of(revisionDto));
    }

    @Test
    public void mappingRevisionDtosListToRevisionsList() {
        var mappedRevision = beanMappingService.mapTo(List.of(revisionDto), Revision.class);
        Assert.assertEquals(mappedRevision, List.of(revision));
    }

    private Revision getNewRevision(){
        var newRevision = new Revision();
        newRevision.setId(1L);
        newRevision.setNote("note");
        newRevision.setRevisionDate(TEST_DATE);
        newRevision.setRevisionTime(TEST_TIME);
        return newRevision;
    }

    private RevisionDto getNewRevisionDto(){
        var newRevisionDto = new RevisionDto();
        newRevisionDto.setId(1L);
        newRevisionDto.setNote("note");
        newRevisionDto.setRevisionDate(TEST_DATE);
        newRevisionDto.setRevisionTime(TEST_TIME);
        return newRevisionDto;
    }

    private RevisionCreateDto getNewRevisionCreateDto(){
        return new RevisionCreateDto(TEST_DATE, TEST_TIME, machineDto, "note");
    }

    private Machine getNewMachine() {
        var newMachine = new Machine();
        newMachine.setId(1L);
        newMachine.setName("name");
        newMachine.setManufacturer("manufacturer");
        newMachine.setDescription("description");
        newMachine.setPrice(new BigDecimal(100));
        return newMachine;
    }

    private MachineDto getNewMachineDto(){
        return MachineDto.builder().withDescription("description").withManufacturer("manufacturer").withName("name").withPrice(new BigDecimal(100)).withId(1L).build();
    }


}

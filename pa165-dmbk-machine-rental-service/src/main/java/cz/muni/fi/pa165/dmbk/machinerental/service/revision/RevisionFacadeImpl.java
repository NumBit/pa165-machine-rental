package cz.muni.fi.pa165.dmbk.machinerental.service.revision;

import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.RevisionFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionDto;
import cz.muni.fi.pa165.dmbk.machinerental.service.BeanMappingService;
import cz.muni.fi.pa165.dmbk.machinerental.service.CustomDataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Lukas Krekan
 * */
@Component
@Transactional(rollbackFor = CustomDataAccessException.class)
public class RevisionFacadeImpl implements RevisionFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private RevisionService revisionService;

    @Override
    public Long createRevision(RevisionCreateDto revision) {
        return revisionService.createRevision(beanMappingService.mapTo(revision, Revision.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RevisionDto> findById(Long id) {
        var revision = revisionService.findById(id);
        return revision.map(value -> beanMappingService.mapTo(value, RevisionDto.class));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionDto> findAll() {
        return beanMappingService.mapTo(revisionService.findAll(), RevisionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionDto> findAllByMachineId(Long machineId) {
        return beanMappingService.mapTo(revisionService.findAllByMachineId(machineId), RevisionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionDto> findAllByDate(LocalDate revisionDate) {
        return beanMappingService.mapTo(revisionService.findAllByDate(revisionDate), RevisionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionDto> findAllByDateAfter(LocalDate revisionDate) {
        return beanMappingService.mapTo(revisionService.findAllByDateAfter(revisionDate), RevisionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionDto> findAllByDateBefore(LocalDate revisionDate) {
        return beanMappingService.mapTo(revisionService.findAllByDateBefore(revisionDate), RevisionDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RevisionDto> findAllByDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo) {
        return beanMappingService.mapTo(revisionService.findAllByDateBetween(revisionDateFrom, revisionDateTo), RevisionDto.class);
    }

    @Override
    public void deleteById(Long id) {
        revisionService.deleteById(id);
    }

    @Override
    public void deleteAll() {
        revisionService.deleteAll();
    }

    @Override
    public void setRevisionDate(Long id, LocalDate revisionDate) {
        revisionService.setRevisionDate(id, revisionDate);
    }

    @Override
    public void setRevisionNote(Long id, String note) {
        revisionService.setRevisionNote(id, note);
    }
}

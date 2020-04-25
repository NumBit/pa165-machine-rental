package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision;

import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Lukas Krekan
 * */
@Component
public interface RevisionFacade {
    Long createRevision(RevisionCreateDto revision);
    Optional<RevisionDto> findById(Long id);
    List<RevisionDto> findAll();
    List<RevisionDto> findAllByMachineId(Long machineId);
    List<RevisionDto> findAllByDate(LocalDate revisionDate);
    List<RevisionDto> findAllByDateAfter(LocalDate revisionDate);
    List<RevisionDto> findAllByDateBefore(LocalDate revisionDate);
    List<RevisionDto> findAllByDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo);
    void deleteById(Long id);
    void deleteAll();
    void setRevisionDate(Long id, LocalDate revisionDate);
    void setRevisionNote(Long id, String note);
}

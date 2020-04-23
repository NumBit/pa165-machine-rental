package cz.muni.fi.pa165.dmbk.machinerental.service;

import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Lukas Krekan
 * */
@Service
public interface RevisionService {
    Long createRevision(Revision revision);
    void updateRevision(Revision revision);
    Optional<Revision> findById(Long id);
    List<Revision> findAll();
    List<Revision> findAllByMachineId(Long machineId);
    List<Revision> findAllByDate(LocalDate revisionDate);
    List<Revision> findAllByDateAfter(LocalDate revisionDate);
    List<Revision> findAllByDateBefore(LocalDate revisionDate);
    List<Revision> findAllByDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo);
    void deleteById(Long id);
    void deleteAll();
    void setRevisionDate(Long id, LocalDate revisionDate);
    void setRevisionNote(Long id, String note);
}

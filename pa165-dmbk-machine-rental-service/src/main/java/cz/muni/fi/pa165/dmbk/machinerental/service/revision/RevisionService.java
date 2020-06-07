package cz.muni.fi.pa165.dmbk.machinerental.service.revision;

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

    /**
     * Create revision
     * @param revision revision to be created
     * @return long id of created revision
     */
    Long createRevision(Revision revision);

    /**
     * Update revision
     * @param revision revision to be updated
     */
    void updateRevision(Revision revision);

    /**
     * Find revision by id
     * @param id of revision
     * @return revision
     */
    Optional<Revision> findById(Long id);

    /**
     * Find all revisions
     * @return list of revisions
     */
    List<Revision> findAll();

    /**
     * Find by machine ID
     * @param machineId id of machine
     * @return list of revisions
     */
    List<Revision> findAllByMachineId(Long machineId);

    /**
     * Find by date
     * @param revisionDate date
     * @return list of revisions
     */
    List<Revision> findAllByDate(LocalDate revisionDate);

    /**
     * Find by date after
     * @param revisionDate date after
     * @return list of revisions
     */
    List<Revision> findAllByDateAfter(LocalDate revisionDate);

    /**
     * Find by date before
     * @param revisionDate date before
     * @return list of revisions
     */
    List<Revision> findAllByDateBefore(LocalDate revisionDate);

    /**
     * Find by date between
     * @param revisionDateFrom date from, revisionDateTo date to
     * @return list of revisions
     */
    List<Revision> findAllByDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo);

    /**
     * Delete revision by id
     * @param id of revision
     * */
    void deleteById(Long id);

    /**
     * Delete all revisions
     * */
    void deleteAll();

    /**
     * set Revision date
     * @param id of revision, revisionDate date of revision
     * */
    void setRevisionDate(Long id, LocalDate revisionDate);

    /**
     * set Revision note
     * @param id of revision, note text of note
     * */
    void setRevisionNote(Long id, String note);
}

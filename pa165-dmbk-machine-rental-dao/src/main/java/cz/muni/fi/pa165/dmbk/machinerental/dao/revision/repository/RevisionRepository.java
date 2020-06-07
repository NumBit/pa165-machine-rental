package cz.muni.fi.pa165.dmbk.machinerental.dao.revision.repository;

import cz.muni.fi.pa165.dmbk.machinerental.dao.revision.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository for Revision DAO
 * @author Lukas Krekan
 **/
@Repository
public interface RevisionRepository extends JpaRepository<Revision, Long> {

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
    List<Revision> findAllByRevisionDate(LocalDate revisionDate);

    /**
     * Find by date after
     * @param revisionDate date after
     * @return list of revisions
     */
    List<Revision> findAllByRevisionDateAfter(LocalDate revisionDate);

    /**
     * Find by date before
     * @param revisionDate date before
     * @return list of revisions
     */
    List<Revision> findAllByRevisionDateBefore(LocalDate revisionDate);

    /**
     * Find by date between
     * @param revisionDateFrom date from, revisionDateTo date to
     * @return list of revisions
     */
    List<Revision> findAllByRevisionDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo);
}

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

    List<Revision> findAllByMachineId(Long machineId);

    List<Revision> findAllByRevisionDate(LocalDate revisionDate);

    List<Revision> findAllByRevisionDateAfter(LocalDate revisionDate);

    List<Revision> findAllByRevisionDateBefore(LocalDate revisionDate);

    List<Revision> findAllByRevisionDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo);
}

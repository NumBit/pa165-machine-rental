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

    List<Revision> findAllByDate(LocalDate revisionDate);

    List<Revision> findAllByDateAfter(LocalDate revisionDate);

    List<Revision> findAllByDateBefore(LocalDate revisionDate);

    List<Revision> findAllByDateBetween(LocalDate revisionDateFrom, LocalDate revisionDateTo);
}

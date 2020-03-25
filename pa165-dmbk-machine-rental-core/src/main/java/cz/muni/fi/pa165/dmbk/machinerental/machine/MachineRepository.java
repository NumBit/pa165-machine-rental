package cz.muni.fi.pa165.dmbk.machinerental.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository declaration for Machine DAO providing JPA
 *
 * @author Márius Molčány
 */


@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

}
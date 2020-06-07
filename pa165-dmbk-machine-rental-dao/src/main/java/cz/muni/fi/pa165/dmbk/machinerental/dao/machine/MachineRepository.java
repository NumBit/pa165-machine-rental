package cz.muni.fi.pa165.dmbk.machinerental.dao.machine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository declaration for Machine DAO providing JPA
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */


@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
    /**
     * Find by name
     * @param name string
     * @return machine list
     */
    List<Machine> findByName(String name);

    /**
     * Find by name like
     * @param name string
     * @return machine list
     */
    List<Machine> findByNameContaining(String name);

    /**
     * Find by manufacturer name
     * @param manufacturer string
     * @return machine list
     */
    List<Machine> findByManufacturer(String manufacturer);

    /**
     * Find by manufacturer name like
     * @param manufacturer string
     * @return machine list
     */
    List<Machine> findByManufacturerContaining(String manufacturer);
}
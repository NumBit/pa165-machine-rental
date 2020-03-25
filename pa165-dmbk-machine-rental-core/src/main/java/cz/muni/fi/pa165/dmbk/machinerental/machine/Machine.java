package cz.muni.fi.pa165.dmbk.machinerental.machine;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Class representing persistent entity machine
 *
 * @author Márius Molčány
 */

@Data
@Entity
public class Machine {
    @Id
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String manufacturer;

    @Column
    protected BigDecimal price;
}

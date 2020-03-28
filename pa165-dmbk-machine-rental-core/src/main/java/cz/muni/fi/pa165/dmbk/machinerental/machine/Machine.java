package cz.muni.fi.pa165.dmbk.machinerental.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Class representing persistent entity machine
 *
 * @author Márius Molčány
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MACHINE")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected String description;

    @Column(nullable = false)
    protected String manufacturer;

    @Column
    protected BigDecimal price;
}

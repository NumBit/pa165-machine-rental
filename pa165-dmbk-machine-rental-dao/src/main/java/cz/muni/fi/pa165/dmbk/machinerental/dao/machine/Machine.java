package cz.muni.fi.pa165.dmbk.machinerental.dao.machine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Class representing persistent entity machine
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MACHINE")
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
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

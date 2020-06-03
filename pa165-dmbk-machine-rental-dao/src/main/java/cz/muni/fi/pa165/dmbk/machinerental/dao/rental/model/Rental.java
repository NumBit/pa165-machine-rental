package cz.muni.fi.pa165.dmbk.machinerental.dao.rental.model;

import cz.muni.fi.pa165.dmbk.machinerental.dao.machine.Machine;
import cz.muni.fi.pa165.dmbk.machinerental.dao.user.model.Customer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


/**
 * Rental class representing Rental entity.
 *
 * @author Peter Baltazarovič
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RENTAL")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long  id;

    @Column(nullable = false, name = "rental_date")
    private LocalDate rentalDate;

    @Column(nullable = false, name = "return_date")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    private String description;
}

package cz.muni.fi.pa165.dmbk.machinerental.rental.dao;

import cz.muni.fi.pa165.dmbk.machinerental.user.dao.Customer;
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
@ToString
@EqualsAndHashCode
@Table(name = "RENTAL")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long  id;

    @Column(nullable = false)
    private LocalDate rentalDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

//    @ManyToOne
//    @JoinColumn(name = "machine_id", nullable = false)
//    private Machine machine;

    private String description;
}

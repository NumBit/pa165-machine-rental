package cz.muni.fi.pa165.dmbk.machinerental.revision.dao;

import cz.muni.fi.pa165.dmbk.machinerental.machine.Machine;
import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;


/**
 * Class representing Machines' revision
 * @author Lukas Krekan
 *
 **/

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "REVISION")
public class Revision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column (nullable = false)
    private Time time;

    @ManyToOne
    @JoinColumn(name = "machine_id",nullable = false)
    private Machine machine;

    private String note;

}

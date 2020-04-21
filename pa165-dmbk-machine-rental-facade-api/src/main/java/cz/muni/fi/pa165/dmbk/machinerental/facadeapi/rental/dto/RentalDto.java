package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import lombok.*;

import java.time.LocalDate;

/**
 * @author Peter Baltazarovič
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class RentalDto {
    private Long Id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private CustomerDto customer;
    // private MachineDto machine;
    private String description;
}

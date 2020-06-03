package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Peter Baltazarovič
 */
@Data
@AllArgsConstructor
@JsonDeserialize
public class RentalUpdateDto {
    private Long Id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private String description;
}

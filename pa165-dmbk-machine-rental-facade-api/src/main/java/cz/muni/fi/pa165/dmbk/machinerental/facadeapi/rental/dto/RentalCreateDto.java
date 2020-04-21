package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.rental.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.user.model.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * @author Peter Baltazarovič
 */
@Data
@AllArgsConstructor
@JsonDeserialize
public class RentalCreateDto {
    @NonNull
    private LocalDate rentalDate;
    @NonNull
    private LocalDate returnDate;
    @NonNull
    private CustomerDto customer;
    // @NonNull
    // private MachineDto machine;
    private String description;
}

package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Machine data transfer object - immutable
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Data
@NoArgsConstructor
@JsonDeserialize(builder = MachineDto.Builder.class)
public class MachineDto {

    private Long id;
    private String name;
    private String description;
    private String manufacturer;
    private BigDecimal price;

    @lombok.Builder(builderClassName = "Builder", setterPrefix = "with")
    public MachineDto(Long id, String name, String description, String manufacturer, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manufacturer = manufacturer;
        this.price = price;
    }
}

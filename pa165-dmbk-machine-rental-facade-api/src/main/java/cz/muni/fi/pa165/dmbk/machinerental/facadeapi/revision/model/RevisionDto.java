package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

/**
 * @author Lukas Krekan
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class RevisionDto {
    private Long id;
    private LocalDate revisionDate;
    private Time revisionTime;
    //private MachineDto machine;
    private String note;

}

package cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Time;
import java.time.LocalDate;

/**
 * @author Lukas Krekan
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
public class RevisionCreateDto {
    @NonNull
    private LocalDate revisionDate;
    @NonNull
    private Time revisionTime;
    @NonNull
    private MachineDto machine;
    private String note;
}

package cz.muni.fi.pa165.dmbk.machinerental.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.RevisionFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionCreateDto;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.revision.model.RevisionDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "${spring.custom.rest-api.contextPath}")
public class RevisionRestController {

    @Autowired
    private RevisionFacade revisionFacade;

    @PostMapping("${spring.rest-api.revisionsPath}/create")
    public ResponseEntity<Long> createRevision(@Valid @RequestBody RevisionCreateDto revisionDto) {
        return ResponseEntity.ok(revisionFacade.createRevision(revisionDto));
    }

    @GetMapping("${spring.rest-api.revisionsPath}/{id}")
    public ResponseEntity<RevisionDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(revisionFacade.findById(id));
    }

    @GetMapping("${spring.rest-api.revisionsPath}/all")
    public ResponseEntity<List<RevisionDto>> findAll() {
        return ResponseEntity.ok(revisionFacade.findAll());
    }

    @GetMapping("${spring.rest-api.revisionsPath}/machine/{id}")
    public ResponseEntity<List<RevisionDto>> findByMachineId(@PathVariable Long id) {
        return ResponseEntity.ok(revisionFacade.findAllByMachineId(id));
    }

    @PostMapping("${spring.rest-api.revisionsPath}/revisionDate")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDate(@RequestBody LocalDate revisionDate) {
        return ResponseEntity.ok(revisionFacade.findAllByDateAfter(revisionDate));
    }

    @PostMapping("${spring.rest-api.revisionsPath}/revisionDateAfter")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDateAfter(@RequestBody LocalDate revisionDate) {
        return ResponseEntity.ok(revisionFacade.findAllByDateAfter(revisionDate));
    }

    @PostMapping("${spring.rest-api.revisionsPath}/revisionDateBefore")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDateBefore(@RequestBody LocalDate revisionDate) {
        return ResponseEntity.ok(revisionFacade.findAllByDateBefore(revisionDate));
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @JsonDeserialize(builder = Dates.Builder.class)
    @Builder(builderClassName = "Builder", toBuilder = true, setterPrefix = "with")
    static class  Dates {
        private final LocalDate dateFrom;
        private final LocalDate dateTo;
    }

    @PostMapping("${spring.rest-api.revisionsPath}/revisionDateBetween")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDateBetween(@RequestBody Dates dates) {
        return ResponseEntity.ok(revisionFacade.findAllByDateBetween(dates.dateFrom, dates.dateTo));
    }

    @DeleteMapping("${spring.rest-api.revisionsPath}/{id}")
    public ResponseEntity<?> deleteById(Long id) {
        revisionFacade.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("${spring.rest-api.revisionsPath}/all")
    public ResponseEntity<?> deleteAllRevisions() {
        revisionFacade.deleteAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("${spring.rest-api.revisionsPath}/setRevisionNote/{id}")
    public ResponseEntity<?> setRevisionNote(@RequestBody Long id, String note){
        revisionFacade.setRevisionNote(id, note);
        return revisionFacade.findById(id).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

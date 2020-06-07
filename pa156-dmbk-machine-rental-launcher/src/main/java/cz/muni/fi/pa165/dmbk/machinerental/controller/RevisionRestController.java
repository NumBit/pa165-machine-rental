package cz.muni.fi.pa165.dmbk.machinerental.controller;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

/**
 * @author Lukas Krekan
 * */
@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "${spring.custom.rest-api.contextPath}")

public class RevisionRestController {

    @Autowired
    private RevisionFacade revisionFacade;

    /**
     * Create Revision
     * @param revisionDto to be created
     * @return Long id of revision dto
     */
    @PostMapping("${spring.rest-api.revisionsPath}/create")
    public ResponseEntity<Long> createRevision(@Valid @RequestBody RevisionCreateDto revisionDto) {
        return ResponseEntity.ok(revisionFacade.createRevision(revisionDto));
    }

    /**
     * Find revision by id
     * @param id Long
     * @return revisionDto
     */
    @GetMapping("${spring.rest-api.revisionsPath}/{id}")
    public ResponseEntity<RevisionDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(revisionFacade.findById(id));
    }

    /**
     * Find all revisions
     * @return list of all revisions
     * */
    @GetMapping("${spring.rest-api.revisionsPath}/all")
    public ResponseEntity<List<RevisionDto>> findAll() {
        return ResponseEntity.ok(revisionFacade.findAll());
    }

    /**
     * Find revision by machine ID
     * @param id of machine
     * @return list of revisions
     * */
    @GetMapping("${spring.rest-api.revisionsPath}/machine/{id}")
    public ResponseEntity<List<RevisionDto>> findByMachineId(@PathVariable Long id) {
        return ResponseEntity.ok(revisionFacade.findAllByMachineId(id));
    }

    /**
     * Find revisions by date
     * @param revisionDate date
     * @return list of revisions
     * */
    @PostMapping("${spring.rest-api.revisionsPath}/revisionDate")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDate(@RequestBody LocalDate revisionDate) {
        return ResponseEntity.ok(revisionFacade.findAllByDate(revisionDate));
    }

    /**
     * Find revisions by date after
     * @param revisionDate date after
     * @return list of revisions
     * */
    @PostMapping("${spring.rest-api.revisionsPath}/revisionDateAfter")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDateAfter(@RequestBody LocalDate revisionDate) {
        return ResponseEntity.ok(revisionFacade.findAllByDateAfter(revisionDate));
    }

    /**
     * Find revisions by date before
     * @param revisionDate date before
     * @return list of revisions
     * */
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

    /**
     * Find revisions by date between
     * @param dates date after and date before
     * @return list of revisions
     * */
    @PostMapping("${spring.rest-api.revisionsPath}/revisionDateBetween")
    public ResponseEntity<List<RevisionDto>> findAllByRevisionDateBetween(@RequestBody Dates dates) {
        return ResponseEntity.ok(revisionFacade.findAllByDateBetween(dates.dateFrom, dates.dateTo));
    }


    /**
     * Delete revision by id
     * @param id of revision
     * @return OK response
     * */
    @DeleteMapping("${spring.rest-api.revisionsPath}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        revisionFacade.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete all revisions
     * @return OK response
     * */
    @DeleteMapping("${spring.rest-api.revisionsPath}/all")
    public ResponseEntity<?> deleteAllRevisions() {
        revisionFacade.deleteAll();
        return ResponseEntity.ok().build();
    }

    /**
     * update Revision note
     * @param id of revision, note text of note
     * @return OK response
     * */
    @PostMapping("${spring.rest-api.revisionsPath}/setRevisionNote/{id}")
    public ResponseEntity<?> setRevisionNote(@PathVariable Long id, @RequestBody String note){
        revisionFacade.setRevisionNote(id, note);
        return revisionFacade.findById(id).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

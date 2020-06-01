package cz.muni.fi.pa165.dmbk.machinerental.controller;

import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.MachineFacade;
import cz.muni.fi.pa165.dmbk.machinerental.facadeapi.machine.model.MachineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Machine REST controller
 *
 * @author Márius Molčány - UČO: 456350 - Github: overlordsvk
 */

@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "${spring.custom.rest-api.contextPath}")
public class MachineRestControlller {

    @Autowired
    private MachineFacade machineFacade;

    /**
     * Create machine
     * @param machineDto to be created
     * @return Long id of machine dto
     */
    @PostMapping("${spring.rest-api.machinePath}/")
    public ResponseEntity<Long> createMachine(@Valid @RequestBody MachineDto machineDto) {
        return ResponseEntity.ok(machineFacade.persistMachine(machineDto));
    }

    /**
     * Find machine by id
     * @param id Long
     * @return machineDto
     */
    @GetMapping("${spring.rest-api.machinePath}/{id}")
    public ResponseEntity<MachineDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(machineFacade.findById(id));
    }

    /**
     * Update machine by dto
     * @param machineDto machine dto
     * @return Long id as success
     */
    @PostMapping("${spring.rest-api.machinePath}/update/")
    public ResponseEntity<Long> updateMachine(@Valid @RequestBody MachineDto machineDto) {
        return ResponseEntity.ok(machineFacade.persistMachine(machineDto));
    }

    /**
     * Delete machine by id
     * @param id of machine to be deleted
     * @return Ok response
     */
    @DeleteMapping("${spring.rest-api.machinePath}/{id}")
    public ResponseEntity<?> deleteMachineById(@PathVariable Long id) {
        machineFacade.deleteMachineById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Returns all machines
     * @return list collection of machines dto
     */
    @GetMapping("${spring.rest-api.machinePath}/")
    public ResponseEntity<List<MachineDto>> findAll() {
        return ResponseEntity.ok(machineFacade.findAll());
    }

    /**
     * Find by exact machine name
     * @param name to find machine
     * @return machine dto list
     */
    @GetMapping("${spring.rest-api.machinePath}/name/{name}")
    public ResponseEntity<List<MachineDto>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(machineFacade.findByName(name));
    }

    /**
     * Find machine by name like
     * @param name to find machine
     * @return machine dto collection
     */
    @GetMapping("${spring.rest-api.machinePath}/namelike/{name}")
    public ResponseEntity<List<MachineDto>> findByNameLike(@PathVariable String name) {
        return ResponseEntity.ok(machineFacade.findByNameLike(name));
    }

    /**
     * Find by manufacturer name
     * @param manufacturer name
     * @return machine list
     */
    @GetMapping("${spring.rest-api.machinePath}/manufacturer/{manufacturer}")
    public ResponseEntity<List<MachineDto>> findByManufacturer(@PathVariable String manufacturer) {
        return ResponseEntity.ok(machineFacade.findByManufacturer(manufacturer));
    }

    /**
     * Find by manufacturer name like
     * @param manufacturer name
     * @return machine list
     */
    @GetMapping("${spring.rest-api.machinePath}/manufacturerlike/{manufacturer}")
    public ResponseEntity<List<MachineDto>> findByManufacturerLike(@PathVariable String manufacturer) {
        return ResponseEntity.ok(machineFacade.findByManufacturerLike(manufacturer));
    }
}

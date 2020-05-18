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

@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "${spring.custom.rest-api.contextPath}")
public class MachineRestControlller {

    @Autowired
    private MachineFacade machineFacade;

    @PostMapping("/machine/")
    public ResponseEntity<Long> createMachine(@Valid @RequestBody MachineDto machineDto) {
        return ResponseEntity.ok(machineFacade.persistMachine(machineDto));
    }

    @GetMapping("/machine/{id}")
    public ResponseEntity<MachineDto> findById(@PathVariable Long id) {
        return ResponseEntity.of(machineFacade.findById(id));
    }

    @PostMapping("/machine/")
    public ResponseEntity<Long> updateMachine(@Valid @RequestBody MachineDto machineDto) {
        return ResponseEntity.ok(machineFacade.persistMachine(machineDto));
    }

    @DeleteMapping("/machine/{id}")
    public ResponseEntity<?> deleteMachineById(@PathVariable Long id) {
        machineFacade.deleteMachineById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/machine/name/{name}")
    public ResponseEntity<List<MachineDto>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(machineFacade.findByName(name));
    }

    @GetMapping("/machine/name/{name}")
    public ResponseEntity<List<MachineDto>> findByNameLike(@PathVariable String name) {
        return ResponseEntity.ok(machineFacade.findByNameLike(name));
    }

    @GetMapping("/machine/manufacturer/{manufacturer}")
    public ResponseEntity<List<MachineDto>> findByManufacturer(@PathVariable String manufacturer) {
        return ResponseEntity.ok(machineFacade.findByManufacturer(manufacturer));
    }

    @GetMapping("/machine/manufacturer/{manufacturer}")
    public ResponseEntity<List<MachineDto>> findByManufacturerLike(@PathVariable String manufacturer) {
        return ResponseEntity.ok(machineFacade.findByManufacturerLike(manufacturer));
    }
}

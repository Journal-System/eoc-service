package kth.numi.eocservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.eocservice.service.Encounter.EncounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/encounter")
@Tag(name = "Encounter Controller", description = "Manage encounter data")
@PreAuthorize("hasRole('EOC')")
public class EncounterController {
    final private EncounterService encounterService;

    @Autowired
    public EncounterController(EncounterService encounterService) {
        this.encounterService = encounterService;
    }

    @GetMapping("/getAll/{id}")
    @Operation(summary = "Get all encounters by id",
            description = "Get all encounters by patient from the database")
    public ResponseEntity<?> getAllEncounters(@PathVariable Integer id) {
        return encounterService.getAllEncountersByPatientId(id);
    }

    @GetMapping("/getOne/{id}")
    @Operation(summary = "Get one encounter by id",
            description = "Get one encounter by encounter id from the database")
    public ResponseEntity<?> getOneEncounter(@PathVariable Integer id) {
        return encounterService.getOneEncounterByEncounterId(id);
    }

    @PostMapping("/addEncounter")
    @Operation(summary = "Add encounter",
            description = "Add new encounter to the database")
    public ResponseEntity<?> addEncounter(@RequestParam String reason, @RequestParam Integer patientId,
                                          @RequestParam Integer doctorId) {
        return encounterService.addEncounter(reason, patientId, doctorId);
    }

    @PostMapping("/addObservation")
    @Operation(summary = "Add observation",
            description = "Add new observation to a existing encounter")
    public ResponseEntity<?> addObservation(@RequestParam Integer observationId, @RequestParam Integer encounterId) {
        return encounterService.addObservationToEncounter(observationId, encounterId);
    }
}
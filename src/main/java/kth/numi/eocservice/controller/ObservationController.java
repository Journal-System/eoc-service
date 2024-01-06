package kth.numi.eocservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.eocservice.service.Observation.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/observation")
@Tag(name = "Observation Controller", description = "Manage observation data")
public class ObservationController {

    final private ObservationService observationService;

    @Autowired
    public ObservationController (ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/getAll/{id}")
    @Operation(summary = "Get all observations based on patientId",
            description = "Get all observations for one specific patient")
    @PreAuthorize("hasAnyRole('PATIENT', 'DOCTOR', 'STAFF')")
    public ResponseEntity<?> getPatientObservations(@PathVariable Integer id) {
        return observationService.getAllObservationsByPatientId(id);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one observation", description = "Get one specific observation based on observationId")
    @PreAuthorize("hasAnyRole('DOCTOR', 'STAFF')")
    public ResponseEntity<?> getOneObservation(@PathVariable Integer id) {
        return observationService.getOneObservationByObservationId(id);
    }

    @PostMapping("/add")
    @Operation(summary = "add a new observation",
            description = "Adds a new observation made by staff or doctor to a patient")
    @PreAuthorize("hasAnyRole('DOCTOR', 'STAFF')")
    public ResponseEntity<?> addOneObservation(@RequestParam String observation,
                                               @RequestParam Integer patientId, @RequestParam Integer staffOrDoctorId) {
        return observationService.addObservation(observation, patientId, staffOrDoctorId);
    }
}
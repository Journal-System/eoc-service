package kth.numi.eocservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kth.numi.eocservice.service.Condition.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/condition")
@Tag(name = "Condition Controller", description = "Manage patients conditions")
public class ConditionController {

    final private ConditionService conditionService;

    @Autowired
    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get one specific condition with a condition id",
            description = "Gets one condition from the database")
    @PreAuthorize("hasAnyRole('DOCTOR', 'STAFF')")
    public ResponseEntity<?> get(@PathVariable Integer id) {
        return conditionService.getConditionByConditionId(id);
    }

    @GetMapping("/getAll/{id}")
    @Operation(summary = "Get all conditions of a specific patient",
            description = "Get a list of all conditions for a specific patient from the database")
    @PreAuthorize("hasAnyRole('PATIENT', 'ADMIN')")
    public ResponseEntity<?> getPatientsConditions(@PathVariable Integer id) {
        return conditionService.getConditionsByUserId(id);
    }

    @PostMapping("/add")
    @Operation(summary = "Add a condition to a patient",
            description = "Adds a condition to the specified patient with that id")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addACondition(@RequestParam String condition, @RequestParam String description,
                                           @RequestParam Integer id) {
        return conditionService.saveConditionByUserId(condition, description, id);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a condition by id",
            description = "Delete a condition to a specified id")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCondition(@PathVariable Integer id) {
        return conditionService.deleteConditionByConditionId(id);
    }
}
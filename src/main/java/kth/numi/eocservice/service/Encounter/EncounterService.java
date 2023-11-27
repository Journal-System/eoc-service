package kth.numi.eocservice.service.Encounter;

import org.springframework.http.ResponseEntity;

public interface EncounterService {
    ResponseEntity<?> getAllEncountersByPatientId(Integer id);

    ResponseEntity<?> getOneEncounterByEncounterId(Integer id);
    ResponseEntity<?> addEncounter(String reason, Integer patientId, Integer doctorId);

    ResponseEntity<?> addObservationToEncounter(Integer observationId, Integer encounterId);
}
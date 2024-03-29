package kth.numi.eocservice.service.Encounter;

import kth.numi.eocservice.model.Encounter;
import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.repository.EncounterRepository;
import kth.numi.eocservice.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import static kth.numi.eocservice.dto.EncounterDto.convertEncounterToDto;

@Service
public class EncounterServiceImpl implements EncounterService {

    private final EncounterRepository encounterRepository;
    private final ObservationRepository observationRepository;

    @Autowired
    public EncounterServiceImpl(EncounterRepository encounterRepository, ObservationRepository observationRepository) {
        this.encounterRepository = encounterRepository;
        this.observationRepository = observationRepository;
    }
    @Override
    public ResponseEntity<?> getAllEncountersByPatientId(Integer id) {
        try {
            List<Encounter> encounterList = encounterRepository.getAllEncountersByPatientId(id);
            if (!encounterList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertEncounterToDto(encounterList));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Encounters by this id do not exist");
        } catch (Exception e) {
            e.printStackTrace(); // To show the details of the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> getOneEncounterByEncounterId(Integer id) {
        try {
            Encounter encounter = encounterRepository.findById(id).orElse(null);
            if (encounter != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertEncounterToDto(encounter));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find this encounter");
        } catch (Exception e) {
            e.printStackTrace(); // To show the details of the error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> addEncounter(String reason, Integer patientId, Integer doctorId) {
        if (patientId != null && doctorId != null) {
            Encounter encounter = new Encounter();
            encounter.setReason(reason);
            encounter.setPatientId(patientId);
            encounter.setDoctorId(doctorId);
            encounter.setObservationId(null);
            encounterRepository.save(encounter);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Encounter added");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Could not find patient and/or doctor");
    }

    @Override
    public ResponseEntity<?> addObservationToEncounter(Integer observationId, Integer encounterId) {
        Encounter encounter = encounterRepository.findById(encounterId).orElse(null);
        Observation observation = observationRepository.findById(observationId).orElse(null);

        if (encounter != null && observation != null) {
            encounter.setObservationId(observationId);
            encounterRepository.save(encounter); // might work
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertEncounterToDto(encounter));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Encounter is null");
    }
}
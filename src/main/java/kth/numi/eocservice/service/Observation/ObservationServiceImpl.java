package kth.numi.eocservice.service.Observation;

import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import static kth.numi.eocservice.dto.ObservationDto.convertObservationToDto;

@Service
public class ObservationServiceImpl implements ObservationService {

    private final ObservationRepository observationRepository;

    @Autowired
    public ObservationServiceImpl(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @Override
    public ResponseEntity<?> getAllObservationsByPatientId(Integer patientId) {
        try {
            if(patientId != null) {
                List<Observation> observationList = observationRepository.findObservationsByPatientId(patientId);
                if (!observationList.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.OK).body(convertObservationToDto(observationList));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Observation is null");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find patient");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> getOneObservationByObservationId(Integer observationId) {
        try {
            Observation observation = observationRepository.findById(observationId).orElse(null);

            if(observation != null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertObservationToDto(observation));
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find this observation");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> addObservation(String observation, Integer patientId, Integer userId) {
        try {
            if (patientId != null && userId != null) {
                Observation newObservation = new Observation();
                newObservation.setObservation(observation);
                newObservation.setPatientId(patientId);
                newObservation.setUserId(userId);
                observationRepository.save(newObservation);
                return ResponseEntity.status(HttpStatus.OK).body(convertObservationToDto(newObservation));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find patient or staff/doctor");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> deleteOneObservation(Integer id) {
        try {
            Observation observation = observationRepository.findById(id).orElse(null);
            if(observation != null) {
                observationRepository.delete(observation);
                return ResponseEntity.status(HttpStatus.OK).body("This observation is deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this observation");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
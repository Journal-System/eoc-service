package kth.numi.eocservice.service.Observation;

import org.springframework.http.ResponseEntity;

public interface ObservationService {

    ResponseEntity<?> getAllObservationsByPatientId(Integer id);

    ResponseEntity<?> getOneObservationByObservationId(Integer id);

    ResponseEntity<?> addObservation(String observation, Integer userId, Integer staffOrDoctorId);

    ResponseEntity<?> deleteOneObservation(Integer id);
}

package kth.numi.eocservice.repository;

import kth.numi.eocservice.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Integer> {
    List<Observation> findObservationsByPatientId(Integer patientId);
}

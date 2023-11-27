package kth.numi.eocservice.repository;

import kth.numi.eocservice.model.Encounter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EncounterRepository extends JpaRepository<Encounter, Integer> {
    List<Encounter> getAllEncountersByPatientId(Integer id);
}

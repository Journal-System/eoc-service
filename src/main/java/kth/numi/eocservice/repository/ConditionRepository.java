package kth.numi.eocservice.repository;

import kth.numi.eocservice.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConditionRepository extends JpaRepository<Condition, Integer> {
    List<Condition> findConditionByPatientId(Integer patientId);
}

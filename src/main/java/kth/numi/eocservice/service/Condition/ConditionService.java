package kth.numi.eocservice.service.Condition;

import org.springframework.http.ResponseEntity;

public interface ConditionService {

    ResponseEntity<?> getConditionByConditionId(Integer id);
    ResponseEntity<?> deleteConditionByConditionId(Integer id);
    ResponseEntity<?> getConditionsByUserId(Integer id);
    ResponseEntity<?> saveConditionByUserId(String condition, String description, Integer id);
}

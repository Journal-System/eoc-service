package kth.numi.eocservice.service.Condition;

import kth.numi.eocservice.model.Condition;
import kth.numi.eocservice.repository.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import static kth.numi.eocservice.dto.ConditionDto.convertConditionToDto;

@Service
public class ConditionServiceImpl implements ConditionService {

    final private ConditionRepository conditionRepository;

    @Autowired
    public ConditionServiceImpl(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public ResponseEntity<?> getConditionByConditionId(Integer id) {
        try {
            Optional<Condition> condition = conditionRepository.findById(id);
            if (condition.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Could not find this condition");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(convertConditionToDto(condition.get()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> deleteConditionByConditionId(Integer id) {
        try {
            Condition condition = conditionRepository.findById(id).orElse(null);

            if (condition != null) {
                conditionRepository.delete(condition);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Condition deleted");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find condition");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> getConditionsByUserId(Integer patientId) {
        try {
            if (patientId != null) {
                List<Condition> conditions = conditionRepository.findConditionByPatientId(patientId);
                if (conditions.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Could not find those conditions");
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertConditionToDto(conditions));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find patient");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }

    @Override
    public ResponseEntity<?> saveConditionByUserId(String condition, String description, Integer id) {
        try {
            if (id != null) {
                Condition newCondition = new Condition();
                newCondition.setCondition(condition);
                newCondition.setDescription(description);
                newCondition.setPatientId(id);
                conditionRepository.save(newCondition);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(convertConditionToDto(newCondition));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Could not find patient to put condition on");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred");
        }
    }
}
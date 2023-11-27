package kth.numi.eocservice.data;

import kth.numi.eocservice.model.Condition;
import kth.numi.eocservice.repository.ConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConditionDataLoader implements CommandLineRunner {
    private ConditionRepository conditionRepository;

    @Autowired
    public ConditionDataLoader(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    @Override
    public void run(String... args) {
        initializeConditions();
    }

    private void initializeConditions() {
        Condition condition1 = createCondition("Fever", "Elevated body temperature", 15);
        Condition condition2 = createCondition("Headache", "Persistent pain in the head", 16);
        Condition condition3 = createCondition("Allergies", "Sensitivity to certain substances", 17);
        Condition condition4 = createCondition("Diabetes", "High blood sugar levels", 18);
        Condition condition5 = createCondition("Hypertension", "Elevated blood pressure", 19);
        Condition condition6 = createCondition("Insomnia", "Difficulty falling asleep", 20);
        Condition condition7 = createCondition("Anxiety", "Excessive worry and fear", 17);
        Condition condition8 = createCondition("Asthma", "Respiratory condition", 18);

        conditionRepository.save(condition1);
        conditionRepository.save(condition2);
        conditionRepository.save(condition3);
        conditionRepository.save(condition4);
        conditionRepository.save(condition5);
        conditionRepository.save(condition6);
        conditionRepository.save(condition7);
        conditionRepository.save(condition8);
    }

    private Condition createCondition(String condition, String description, Integer patientId) {
        Condition newCondition = new Condition();
        newCondition.setCondition(condition);
        newCondition.setDescription(description);
        newCondition.setPatientId(patientId);
        return newCondition;
    }
}
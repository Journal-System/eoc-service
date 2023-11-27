package kth.numi.eocservice.data;

import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.repository.ObservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ObservationDataLoader implements CommandLineRunner {
    private ObservationRepository observationRepository;

    @Autowired
    public ObservationDataLoader(ObservationRepository observationRepository) {
        this.observationRepository = observationRepository;
    }

    @Override
    public void run(String... args) {
        initializeObservations();
    }

    private void initializeObservations() {
        Observation observation1 = createObservation("Blood pressure measured", 1, 15);
        Observation observation2 = createObservation("Weight recorded", 2, 16);
        Observation observation3 = createObservation("Temperature check", 3, 17);
        Observation observation4 = createObservation("Blood sugar level tested", 4, 18);
        Observation observation5 = createObservation("Cholesterol level checked", 15, 19);
        Observation observation6 = createObservation("Mood assessment", 16, 20);
        Observation observation7 = createObservation("Exercise routine documented", 17, 17);
        Observation observation8 = createObservation("Dietary intake recorded", 18, 16);

        observationRepository.save(observation1);
        observationRepository.save(observation2);
        observationRepository.save(observation3);
        observationRepository.save(observation4);
        observationRepository.save(observation5);
        observationRepository.save(observation6);
        observationRepository.save(observation7);
        observationRepository.save(observation8);
    }

    private Observation createObservation(String observation, Integer doctorOrStaffId, Integer patientId) {
        Observation newObservation = new Observation();
        newObservation.setObservation(observation);
        newObservation.setUserId(doctorOrStaffId);
        newObservation.setPatientId(patientId);
        newObservation.setTimestamp(LocalDateTime.now());
        return newObservation;
    }
}
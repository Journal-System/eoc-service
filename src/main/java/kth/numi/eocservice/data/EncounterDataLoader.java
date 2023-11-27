package kth.numi.eocservice.data;

import kth.numi.eocservice.model.Encounter;
import kth.numi.eocservice.repository.EncounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class EncounterDataLoader implements CommandLineRunner {
    private EncounterRepository encounterRepository;

    @Autowired
    public EncounterDataLoader(EncounterRepository encounterRepository) {
        this.encounterRepository = encounterRepository;
    }

    @Override
    public void run(String... args) {
        initializeEncounters();
    }

    private void initializeEncounters() {
        Encounter encounter1 = createEncounter("Routine checkup", 15, 1);
        Encounter encounter2 = createEncounter("Flu symptoms", 16, 2);
        Encounter encounter3 = createEncounter("Follow-up appointment", 17, 3);
        Encounter encounter4 = createEncounter("Emergency visit", 18, 4);
        Encounter encounter5 = createEncounter("Dental examination", 19, 5);
        Encounter encounter6 = createEncounter("Mental health consultation", 20, 6);
        Encounter encounter7 = createEncounter("Vaccination", 16, 7);
        Encounter encounter8 = createEncounter("Sports injury evaluation", 18, 8);

        encounterRepository.save(encounter1);
        encounterRepository.save(encounter2);
        encounterRepository.save(encounter3);
        encounterRepository.save(encounter4);
        encounterRepository.save(encounter5);
        encounterRepository.save(encounter6);
        encounterRepository.save(encounter7);
        encounterRepository.save(encounter8);
    }

    private Encounter createEncounter(String reason, Integer patientId, Integer doctorId) {
        Encounter encounter = new Encounter();
        encounter.setReason(reason);
        encounter.setTimestamp(LocalDateTime.now());
        encounter.setPatientId(patientId);
        encounter.setDoctorId(doctorId);
        return encounter;
    }
}
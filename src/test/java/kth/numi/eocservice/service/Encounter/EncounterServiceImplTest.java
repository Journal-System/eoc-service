package kth.numi.eocservice.service.Encounter;

import kth.numi.eocservice.model.Encounter;
import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.repository.EncounterRepository;
import kth.numi.eocservice.repository.ObservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kth.numi.eocservice.dto.EncounterDto.convertEncounterToDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(EncounterServiceImpl.class)
class EncounterServiceImplTest {

    @Autowired private EncounterServiceImpl encounterServiceImpl;
    @MockBean EncounterRepository encounterRepository;
    @MockBean ObservationRepository observationRepository;
    @BeforeEach
    void setUp() {
        encounterServiceImpl = new EncounterServiceImpl(encounterRepository, observationRepository);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToGetAllEncountersByPatientIdIfIdExists() {
        Encounter mockEncounter = createEncounter();
        when(encounterRepository.getAllEncountersByPatientId(1)).thenReturn(List.of(mockEncounter));

        ResponseEntity<?> response = encounterServiceImpl.getAllEncountersByPatientId(1);

        assertEquals(response.getBody(), List.of(convertEncounterToDto(mockEncounter)));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response, "Result should not be null if a body exists");
    }

    @Test
    void testToGetAllEncountersByPatientIdIfDoNotExist() {
        when(encounterRepository.getAllEncountersByPatientId(1)).thenReturn(List.of());

        ResponseEntity<?> response = encounterServiceImpl.getAllEncountersByPatientId(1);

        assertEquals(response.getBody(), "Encounters by this id do not exist");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    void testToGtAllEncountersByPatientIdIfItThrowsException() {
        when(encounterRepository.getAllEncountersByPatientId(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = encounterServiceImpl.getAllEncountersByPatientId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToGetOneEncounterByEncounterIdIfIdExists() {
        Encounter mockEncounter = createEncounter();

        when(encounterRepository.findById(1)).thenReturn(Optional.of(mockEncounter));

        ResponseEntity<?> response = encounterServiceImpl.getOneEncounterByEncounterId(1);

        assertNotNull(response, "Result should not be null since encounter exists with this id");
        assertEquals(response.getBody(), convertEncounterToDto(mockEncounter));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testToGetOneEncounterByEncounterIdIfIdDontExist() {
        when(encounterRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = encounterServiceImpl.getOneEncounterByEncounterId(1);

        assertEquals(response.getBody(), "Could not find this encounter");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetOneEncounterByEncounterIdIfItThrowsException() {
        when(encounterRepository.findById(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = encounterServiceImpl.getOneEncounterByEncounterId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToAddEncounterIfParametersAreValid() {
        Encounter mockEncounter = createEncounter();

        when(encounterRepository.save(any(Encounter.class))).thenReturn(mockEncounter);

        ResponseEntity<?> response = encounterServiceImpl
                .addEncounter("Headache", 1, 2);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), "Encounter added");

        verify(encounterRepository, times(1)).save(any(Encounter.class));
    }

    @Test
    void testToAddEncounterIfParametersAreNotValid() {
        when(encounterRepository.findById(1)).thenReturn(Optional.empty());

        when(encounterRepository.save(any(Encounter.class))).thenReturn(null);

        ResponseEntity<?> response = encounterServiceImpl
                .addEncounter("Test", null, null);

        assertEquals(response.getBody(), "Could not find patient and/or doctor");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        verify(encounterRepository, times(0)).save(any(Encounter.class));
    }

    @Test
    void testToAddObservationToEncounterIfObservationIdExists() {
        Encounter mockEncounter = createEncounter();
        Observation mockObservation = createObservation();

        when(observationRepository.findById(3)).thenReturn(Optional.of(mockObservation));
        when(encounterRepository.findById(1)) .thenReturn(Optional.of(mockEncounter));
        when(encounterRepository.save(any(Encounter.class))).thenReturn(mockEncounter);

        ResponseEntity<?> response = encounterServiceImpl.addObservationToEncounter(3, 1);

        assertEquals(response.getBody(), convertEncounterToDto(mockEncounter));
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(encounterRepository, times(1)).save(any(Encounter.class));

    }

    @Test
    void testToAddObservationToEncounterIfObservationIdDoNotExist() {
        when(encounterRepository.findById(2)).thenReturn(Optional.empty());
        when(encounterRepository.save(any(Encounter.class))).thenReturn(null);

        ResponseEntity<?> response = encounterServiceImpl.addObservationToEncounter(1, 1);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "Encounter is null");

        verify(encounterRepository, times(0)).save(any(Encounter.class));
    }
    private Encounter createEncounter() {
        Encounter encounter = new Encounter();
        encounter.setEncounterId(1);
        encounter.setReason("Headache");
        encounter.setTimestamp(LocalDateTime.parse("2023-11-27T15:03:57.247493"));
        encounter.setPatientId(1);
        encounter.setDoctorId(2);
        encounter.setObservationId(3);
        return encounter;
    }

    private Observation createObservation() {
        Observation observation = new Observation();
        observation.setObservationId(3);
        observation.setObservation("A lot of pain");
        observation.setTimestamp(LocalDateTime.parse("2023-11-27T15:03:57.247493"));
        observation.setPatientId(1);
        observation.setUserId(2);
        return observation;
    }
}
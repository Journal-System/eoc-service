package kth.numi.eocservice.controller;

import kth.numi.eocservice.model.Encounter;
import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.service.Encounter.EncounterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EncounterController.class)
class EncounterControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private EncounterService encounterService;
    @Test
    void testToGetAllEncountersIfIdIsValid() throws Exception {

        Encounter mockEncounter1 = createEncounter();
        Encounter mockEncounter2 = createEncounter2();

        List<Encounter> mockEncounterList = new ArrayList<>();
        mockEncounterList.add(mockEncounter1);
        mockEncounterList.add(mockEncounter2);

        given(encounterService.getAllEncountersByPatientId(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockEncounterList));

        mvc.perform(get("/encounter/getAll/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].encounterId").value(1))
                .andExpect(jsonPath("$[0].reason").value("Headache"))
                .andExpect(jsonPath("$[0].timestamp").value("2023-11-27T15:03:57"))
                .andExpect(jsonPath("$[0].patientId").value(1))
                .andExpect(jsonPath("$[0].doctorId").value(2))
                .andExpect(jsonPath("$[0].observationId").value(3))
                .andExpect(jsonPath("$[1].encounterId").value(2))
                .andExpect(jsonPath("$[1].reason").value("Fever"))
                .andExpect(jsonPath("$[1].timestamp").value("2023-11-29T10:25:27"))
                .andExpect(jsonPath("$[1].patientId").value(2))
                .andExpect(jsonPath("$[1].doctorId").value(3))
                .andExpect(jsonPath("$[1].observationId").value(4));

        verify(encounterService, times(1)).getAllEncountersByPatientId(1);
    }

    @Test
    void testToGetAllEncountersIfIdIsNotValid() throws Exception {
        given(encounterService.getAllEncountersByPatientId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounters by this id do not exist"));

        mvc.perform(get("/encounter/getAll/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Encounters by this id do not exist"));

        verify(encounterService, times(1)).getAllEncountersByPatientId(1);
    }

    @Test
    void testToGetOneEncounterByEncounterIdIfIdIsValid() throws Exception {
        Encounter mockEncounter = createEncounter();

        given(encounterService.getOneEncounterByEncounterId(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockEncounter));

        mvc.perform(get("/encounter/getOne/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("encounterId").value(1))
                .andExpect(jsonPath("reason").value("Headache"))
                .andExpect(jsonPath("timestamp").value("2023-11-27T15:03:57"))
                .andExpect(jsonPath("patientId").value(1))
                .andExpect(jsonPath("doctorId").value(2))
                .andExpect(jsonPath("observationId").value(3));

        verify(encounterService, times(1)).getOneEncounterByEncounterId(1);
    }

    @Test
    void testToGetOneEncounterByEncounterIdIfIdIsNotValid() throws Exception {
        given(encounterService.getOneEncounterByEncounterId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this encounter"));

        mvc.perform(get("/encounter/getOne/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find this encounter"));

        verify(encounterService, times(1)).getOneEncounterByEncounterId(1);
    }

    @Test
    void testToAddEncounterIfParameterIsValid() throws Exception {
        Encounter mockEncounter = createEncounter();

        given(encounterService.addEncounter("Headache", 1, 2))
                .willAnswer(invocation -> ResponseEntity.ok(mockEncounter));

        mvc.perform(post("/encounter/addEncounter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("reason", "Headache")
                        .param("patientId", "1")
                        .param("doctorId", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("encounterId").value(1))
                .andExpect(jsonPath("reason").value("Headache"))
                .andExpect(jsonPath("timestamp").value("2023-11-27T15:03:57"))
                .andExpect(jsonPath("patientId").value(1))
                .andExpect(jsonPath("doctorId").value(2))
                .andExpect(jsonPath("observationId").value(3));

        verify(encounterService, times(1)).addEncounter("Headache", 1, 2);
    }

    @Test
    void testToAddEncounterIfParameterIsNotValid() throws Exception {
        given(encounterService.addEncounter("Headache", 1,2))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find patient and/or doctor"));

        mvc.perform(post("/encounter/addEncounter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("reason", "Headache")
                        .param("patientId", "1")
                        .param("doctorId", "2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find patient and/or doctor"));
    }

    @Test
    void testToAddObservationToEncounterIfParameterIsValid() throws Exception {
        Encounter mockEncounter = createEncounter();
        Observation mockObservation = createObservation();

        given(encounterService.addObservationToEncounter(3, 1))
                .willAnswer(invocation -> ResponseEntity.ok(mockEncounter));

        mvc.perform(post("/encounter/addObservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .param("observationId", "3")
                        .param("encounterId", "1"))
                .andDo(print())
                .andExpect(jsonPath("encounterId").value(1))
                .andExpect(jsonPath("reason").value("Headache"))
                .andExpect(jsonPath("timestamp").value("2023-11-27T15:03:57"))
                .andExpect(jsonPath("patientId").value(1))
                .andExpect(jsonPath("doctorId").value(2))
                .andExpect(jsonPath("observationId").value(3));

        verify(encounterService, times(1)).addObservationToEncounter(3,1);
    }

    @Test
    void testToAddObservationToEncounterIfParameterIsNotValid() throws Exception {
        given(encounterService.addObservationToEncounter(1,3))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Encounter is null"));

        mvc.perform(post("/encounter/addObservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .param("observationId", "1")
                        .param("encounterId", "3"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Encounter is null"));
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

    private Encounter createEncounter2() {
        Encounter encounter = new Encounter();
        encounter.setEncounterId(2);
        encounter.setReason("Fever");
        encounter.setTimestamp(LocalDateTime.parse("2023-11-29T10:25:27.247493"));
        encounter.setPatientId(2);
        encounter.setDoctorId(3);
        encounter.setObservationId(4);
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
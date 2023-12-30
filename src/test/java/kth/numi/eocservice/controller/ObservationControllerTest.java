package kth.numi.eocservice.controller;

import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.service.Observation.ObservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import java.time.LocalDateTime;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ObservationController.class)
class ObservationControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private ObservationService observationService;
    @Test
    void testToGetPatientObservationsIfIdIsValid() throws Exception {
        Observation mockObservation = createObservation();

        given(observationService.getAllObservationsByPatientId(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockObservation));

        mvc.perform(get("/observation/getAll/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("observationId").value(0))
                .andExpect(jsonPath("observation").value("A lot of pain"))
                .andExpect(jsonPath("patientId").value(1))
                .andExpect(jsonPath("userId").value(2));

        verify(observationService, times(1)).getAllObservationsByPatientId(1);
    }

    @Test
    void testToGetPatientObservationsIfIdIsNotValid() throws Exception {
        given(observationService.getAllObservationsByPatientId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Observation is null"));

        mvc.perform(get("/observation/getAll/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Observation is null"));

        verify(observationService, times(1)).getAllObservationsByPatientId(1);
    }

    @Test
    void testToGetOneObservationIfIdIsValid() throws Exception {
        Observation mockObservation = createObservation();

        given(observationService.getOneObservationByObservationId(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockObservation));

        mvc.perform(get("/observation/get/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("observationId").value(0))
                .andExpect(jsonPath("observation").value("A lot of pain"))
                .andExpect(jsonPath("patientId").value(1))
                .andExpect(jsonPath("userId").value(2));

        verify(observationService, times(1)).getOneObservationByObservationId(1);
    }

    @Test
    void testToGetOneObservationIfIdIsNotValid() throws Exception {
        given(observationService.getOneObservationByObservationId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this observation"));

        mvc.perform(get("/observation/get/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find this observation"));

        verify(observationService, times(1)).getOneObservationByObservationId(1);
    }

    @Test
    void testToAddOneObservationIfParametersAreValid() throws Exception {
        Observation mockObservation = createObservation();

        given(observationService.addObservation("A lot of pain", 1, 2))
                .willAnswer(invocation -> ResponseEntity.ok(mockObservation));

        mvc.perform(post("/observation/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .param("observation", "A lot of pain")
                        .param("patientId", "1")
                        .param("staffOrDoctorId", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("observationId").value(0))
                .andExpect(jsonPath("observation").value("A lot of pain"))
                .andExpect(jsonPath("patientId").value(1))
                .andExpect(jsonPath("userId").value(2));

        verify(observationService, times(1)).addObservation("A lot of pain", 1, 2);
    }

    @Test
    void testToAddOneObservationIfParametersAreNotValid() throws Exception {
        given(observationService.addObservation("A lot of pain", 1, 2))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find patient or staff/doctor"));

        mvc.perform(post("/observation/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.jwt().jwt((jwt) -> jwt.subject("test@test.com")))
                        .param("observation", "A lot of pain")
                        .param("patientId", "1")
                        .param("staffOrDoctorId", "2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find patient or staff/doctor"));

        verify(observationService, times(1)).addObservation("A lot of pain", 1, 2);
    }

    private Observation createObservation() {
        Observation observation = new Observation();
        observation.setObservationId(0);
        observation.setObservation("A lot of pain");
        observation.setTimestamp(LocalDateTime.now());
        observation.setPatientId(1);
        observation.setUserId(2);
        return observation;
    }
}
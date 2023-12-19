package kth.numi.eocservice.service.Observation;

import kth.numi.eocservice.dto.ObservationDto;
import kth.numi.eocservice.model.Observation;
import kth.numi.eocservice.repository.ObservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static kth.numi.eocservice.dto.ObservationDto.convertObservationToDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(ObservationServiceImpl.class)
class ObservationServiceImplTest {

    @Autowired private ObservationServiceImpl observationServiceImpl;
    @MockBean ObservationRepository observationRepository;

    @BeforeEach
    void setUp() {
        observationServiceImpl = new ObservationServiceImpl(observationRepository);
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testToGetAllObservationsByPatientIdIfIdExist() {
        Observation mockObservation = createObservation();

        when(observationRepository.findObservationsByPatientId(1)).thenReturn(List.of(mockObservation));

        ResponseEntity<?> response = observationServiceImpl.getAllObservationsByPatientId(1);

        assertEquals(response.getBody(), List.of(convertObservationToDto(mockObservation)));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testToGetAllObservationsByPatientIdIfIdDontExist() {
        when(observationRepository.findObservationsByPatientId(1)).thenReturn(null);

        ResponseEntity<?> response = observationServiceImpl.getAllObservationsByPatientId(null);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(response.getBody(), "Could not find patient");
    }

    @Test
    void testToGetAllObservationsByPatientIdIfObservationListIsEmpty() {
        when(observationRepository.findObservationsByPatientId(1)).thenReturn(List.of());

        ResponseEntity<?> response = observationServiceImpl.getAllObservationsByPatientId(1);

        assertEquals(response.getBody(), "Observation is null");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetAllObservationsByPatientIdIfItThrowsException() {
        when(observationRepository.findObservationsByPatientId(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = observationServiceImpl.getAllObservationsByPatientId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToGetOneObservationByObservationIdIfIdExist() {
        Observation mockObservation = createObservation();

        when(observationRepository.findById(1)).thenReturn(Optional.of(mockObservation));

        ResponseEntity<?> response = observationServiceImpl.getOneObservationByObservationId(1);

        assertEquals(response.getBody(), convertObservationToDto(mockObservation));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testToGetOneObservationByObservationIdIfIdDontExist() {
        when(observationRepository.findById(1)).thenReturn(null);

        ResponseEntity<?> response = observationServiceImpl.getOneObservationByObservationId(null);

        assertEquals(response.getBody(), "Could not find this observation");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetOneObservationByObservationIdIfItThrowsException() {
        when(observationRepository.findById(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = observationServiceImpl.getOneObservationByObservationId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToAddObservationIfPatientIdExist() {
        Observation mockObservation = createObservation();

        when(observationRepository.save(any(Observation.class))).thenReturn(mockObservation);

        ResponseEntity<?> response = observationServiceImpl.addObservation("A lot of pain", 1, 2);

        ObservationDto responseBody = (ObservationDto) response.getBody();

        assertEquals(responseBody.getObservationId(), mockObservation.getObservationId());
        assertEquals(responseBody.getObservation(), mockObservation.getObservation());
        assertEquals(responseBody.getPatientId(), mockObservation.getPatientId());
        assertEquals(responseBody.getDoctorOrStaffId(), mockObservation.getUserId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(observationRepository, times(1)).save(any(Observation.class));
    }

    @Test
    void testToAddObservationIfPatientIdDontExist() {
        when(observationRepository.save(any(Observation.class))).thenReturn(null);

        ResponseEntity<?> response = observationServiceImpl.addObservation(null, null, null);

        assertEquals(response.getBody(), "Could not find patient or staff/doctor");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        verify(observationRepository, times(0)).save(any(Observation.class));
    }

    @Test
    void testToAddObservationIfItThrowsException() {
        when(observationRepository.save(any(Observation.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = observationServiceImpl.addObservation("Test", 1, 1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        verify(observationRepository, times(1)).save(any(Observation.class));
    }

    @Test
    void testToDeleteOneObservationIfIdExist() {
        Observation mockObservation = createObservation();

        when(observationRepository.findById(1)).thenReturn(Optional.of(mockObservation));

        ResponseEntity<?> response = observationServiceImpl.deleteOneObservation(1);

        assertEquals(response.getBody(), "This observation is deleted");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(observationRepository, times(1)).delete(any(Observation.class));
    }

    @Test
    void testToDeleteOneObservationIfIdDontExist() {
        when(observationRepository.findById(1)).thenReturn(null);

        ResponseEntity<?> response = observationServiceImpl.deleteOneObservation(null);

        assertEquals(response.getBody(), "Could not find this observation");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        verify(observationRepository, times(0)).delete(any(Observation.class));
    }

    @Test
    void testToDeleteOneObservationIfItThrowsException() {
        when(observationRepository.findById(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = observationServiceImpl.deleteOneObservation(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        verify(observationRepository, times(0)).save(any(Observation.class));
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
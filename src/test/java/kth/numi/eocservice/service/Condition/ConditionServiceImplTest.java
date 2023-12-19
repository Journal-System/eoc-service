package kth.numi.eocservice.service.Condition;

import kth.numi.eocservice.model.Condition;
import kth.numi.eocservice.repository.ConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static kth.numi.eocservice.dto.ConditionDto.convertConditionToDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(ConditionServiceImpl.class)
class ConditionServiceImplTest {
    @Autowired private ConditionServiceImpl conditionServiceImpl;
    @MockBean ConditionRepository conditionRepository;

    @BeforeEach
    void setUp() {
        conditionServiceImpl = new ConditionServiceImpl(conditionRepository);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToGetConditionByConditionIdIfIdExists() {
        Condition mockCondition = createCondition();
        when(conditionRepository.findById(1)).thenReturn(Optional.of(mockCondition));

        ResponseEntity<?> response = conditionServiceImpl.getConditionByConditionId(1);

        assertEquals(response.getBody(), convertConditionToDto(mockCondition));
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response, "Result should not be null if a body exists");
    }

    @Test
    void testToGetConditionByConditionIdIfIdDoNotExist() {
        when(conditionRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = conditionServiceImpl.getConditionByConditionId(1);

        assertEquals(response.getBody(), "Could not find this condition");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetConditionByConditionIdIfItThrowsException() {
        when(conditionRepository.findById(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = conditionServiceImpl.getConditionByConditionId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToDeleteConditionByConditionIdIfIdExists() {
        Condition mockCondition = createCondition();
        when(conditionRepository.findById(1)).thenReturn(Optional.of(mockCondition));

        ResponseEntity<?> response = conditionServiceImpl.deleteConditionByConditionId(1);

        assertEquals(response.getBody(), "Condition deleted");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(conditionRepository, times(1)).delete(any(Condition.class));
    }

    @Test
    void testToDeleteConditionByConditionIdDoNotExist() {
        when(conditionRepository.findById(1)).thenReturn(Optional.empty());

        ResponseEntity<?> response = conditionServiceImpl.deleteConditionByConditionId(1);

        assertEquals(response.getBody(), "Could not find condition");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        verify(conditionRepository, times(0)).delete(any(Condition.class));
    }

    @Test
    void testToDeleteConditionByConditionIfItThrowsException() {
        when(conditionRepository.findById(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = conditionServiceImpl.deleteConditionByConditionId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        verify(conditionRepository, times(0)).delete(any(Condition.class));
    }

    @Test
    void testToGetConditionsByUserIdIfIdExists() {

        List<Condition> conditionList = new ArrayList<>();
        Condition mockCondition1 = createCondition();
        Condition mockCondition2 = createCondition();

        conditionList.add(mockCondition1);
        conditionList.add(mockCondition2);

        when(conditionRepository.findConditionByPatientId(1)).thenReturn(conditionList);

        ResponseEntity<?> response = conditionServiceImpl.getConditionsByUserId(1);

        assertEquals(response.getBody(), convertConditionToDto(conditionList));
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(conditionRepository, times(1)).findConditionByPatientId(any(Integer.class));
    }

    @Test
    void testToGetConditionsByUserIdIfConditionsIsEmpty() {
        when(conditionRepository.findConditionByPatientId(1)).thenReturn(List.of());

        ResponseEntity<?> response = conditionServiceImpl.getConditionsByUserId(1);

        assertEquals(response.getBody(), "Could not find those conditions");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetConditionsByUserIdIfIdDontExist() {
        when(conditionRepository.findConditionByPatientId(1)).thenReturn(List.of());

        ResponseEntity<?> response = conditionServiceImpl.getConditionsByUserId(null);

        assertEquals(response.getBody(), "Could not find patient");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void testToGetConditionByUserIdIfItThrowsException() {
        when(conditionRepository.findConditionByPatientId(1)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = conditionServiceImpl.getConditionsByUserId(1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testToSaveConditionByUserIdIfIdExists() {
        Condition mockCondition = createCondition();

        when(conditionRepository.save(any(Condition.class))).thenReturn(mockCondition);

        ResponseEntity<?> response = conditionServiceImpl.saveConditionByUserId("Sick", "A lot of pain", 1);

        assertEquals(response.getBody(), convertConditionToDto(mockCondition));
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(conditionRepository, times(1)).save(any(Condition.class));
    }

    @Test
    void testToSaveConditionByUserIdIfIdDontExist() {
        when(conditionRepository.save(any(Condition.class))).thenReturn(null);

        ResponseEntity<?> response = conditionServiceImpl.saveConditionByUserId("Fever", "He is sick a little bit", null);

        assertEquals(response.getBody(), "Could not find patient to put condition on");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

        verify(conditionRepository, times(0)).save(any(Condition.class));
    }

    @Test
    void testToSaveConditionByUserIdIfItThrowsException() {
        when(conditionRepository.save(any(Condition.class))).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<?> response = conditionServiceImpl.saveConditionByUserId(null, null, 1);

        assertEquals(response.getBody(), "An error occurred");
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Condition createCondition() {
        Condition condition = new Condition();
        condition.setConditionId(0);
        condition.setPatientId(1);
        condition.setCondition("Sick");
        condition.setDescription("A lot of pain");
        return condition;
    }
}
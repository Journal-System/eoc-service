package kth.numi.eocservice.controller;

import kth.numi.eocservice.model.Condition;
import kth.numi.eocservice.service.Condition.ConditionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConditionController.class)
class ConditionControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private ConditionService conditionService;
    @Test
    void testToGetOneConditionByConditionIdIfIdValid() throws Exception {
        Condition mockCondition = createCondition();

        given(conditionService.getConditionByConditionId(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockCondition));

        mvc.perform(get("/condition/get/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("conditionId").value(0))
                .andExpect(jsonPath("condition").value("Sick"))
                .andExpect(jsonPath("description").value("A lot of pain"))
                .andExpect(jsonPath("patientId").value(1));

        verify(conditionService, times(1)).getConditionByConditionId(1);
    }
    @Test
    void testToGetOneConditionByConditionIdIfIdNotValid() throws Exception {
        given(conditionService.getConditionByConditionId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find this condition"));

        mvc.perform(get("/condition/get/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find this condition"));

        verify(conditionService, times(1)).getConditionByConditionId(1);
    }
    @Test
    void testToGetPatientsConditionsIfIdValid() throws Exception {
        Condition mockCondition1 = createCondition();
        Condition mockCondition2 = createCondition2();
        List<Condition> mockConditionList = new ArrayList<>();
        mockConditionList.add(mockCondition1);
        mockConditionList.add(mockCondition2);

        given(conditionService.getConditionsByUserId(1))
                .willAnswer(invocation -> ResponseEntity.ok(mockConditionList));

        mvc.perform(get("/condition/getAll/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].conditionId").value(0))
                .andExpect(jsonPath("$[0].condition").value("Sick"))
                .andExpect(jsonPath("$[0].description").value("A lot of pain"))
                .andExpect(jsonPath("$[0].patientId").value(1))
                .andExpect(jsonPath("$[1].conditionId").value(1))
                .andExpect(jsonPath("$[1].condition").value("Feel Sick"))
                .andExpect(jsonPath("$[1].description").value("A lot lot of pain"))
                .andExpect(jsonPath("$[1].patientId").value(2));

        verify(conditionService, times(1)).getConditionsByUserId(1);
    }
    @Test
    void testToGetPatientsConditionIfIdIsNotValid() throws Exception {
        given(conditionService.getConditionsByUserId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find patient"));

        mvc.perform(get("/condition/getAll/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find patient"));
    }
    @Test
    void testToAddAConditionIfParametersIsValid() throws Exception {
        Condition mockCondition = createCondition();

        given(conditionService.saveConditionByUserId("Sick", "A lot of pain", 1))
                .willAnswer(invocation -> ResponseEntity.ok(mockCondition));

        mvc.perform(post("/condition/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("condition", "Sick")
                        .param("description", "A lot of pain")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("conditionId").value(0))
                .andExpect(jsonPath("condition").value("Sick"))
                .andExpect(jsonPath("description").value("A lot of pain"))
                .andExpect(jsonPath("patientId").value(1));

        verify(conditionService, times(1)).saveConditionByUserId("Sick", "A lot of pain", 1);
    }

    @Test
    void testToAddAConditionIfParametersIsNotValid() throws Exception {
        given(conditionService.saveConditionByUserId("Sick", "A lot of pain", 1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find patient to put condition on"));

        mvc.perform(post("/condition/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("condition", "Sick")
                        .param("description", "A lot of pain")
                        .param("id", "1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find patient to put condition on"));

        verify(conditionService, times(1)).saveConditionByUserId("Sick", "A lot of pain", 1);
    }

    @Test
    void testToDeleteConditionIfIdIsValid() throws Exception {
        given(conditionService.deleteConditionByConditionId(1))
                .willAnswer(invocation -> ResponseEntity.ok().body("Condition deleted"));

        mvc.perform(delete("/condition/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Condition deleted"));

        verify(conditionService, times(1)).deleteConditionByConditionId(1);
    }

    @Test
    void testToDeleteConditionIfIdIsNotValid() throws Exception {
        given(conditionService.deleteConditionByConditionId(1))
                .willAnswer(invocation -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Could not find condition"));

        mvc.perform(delete("/condition/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find condition"));
    }

    private Condition createCondition() {
        Condition condition = new Condition();
        condition.setConditionId(0);
        condition.setPatientId(1);
        condition.setCondition("Sick");
        condition.setDescription("A lot of pain");
        return condition;
    }
    private Condition createCondition2() {
        Condition condition = new Condition();
        condition.setConditionId(1);
        condition.setPatientId(2);
        condition.setCondition("Feel Sick");
        condition.setDescription("A lot lot of pain");
        return condition;
    }
}
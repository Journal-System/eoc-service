package kth.numi.eocservice.dto;

import kth.numi.eocservice.model.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConditionDto {
    private Integer conditionId;
    private String condition;
    private String description;
    private Integer patientId;

    public static ConditionDto convertConditionToDto(Condition condition) {
        return new ConditionDto(
                condition.getConditionId(),
                condition.getCondition(),
                condition.getDescription(),
                condition.getPatientId()
        );
    }

    public static <T extends Condition> List<ConditionDto> convertConditionToDto(List<T> conditions) {
        List<ConditionDto> conditionDtoList = new ArrayList<>();
        for (T condition: conditions) {
            conditionDtoList.add(convertConditionToDto(condition));
        }
        return conditionDtoList;
    }
}

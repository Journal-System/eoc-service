package kth.numi.eocservice.dto;

import kth.numi.eocservice.model.Observation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObservationDto {
    private int observationId;
    private String observation;
    private LocalDateTime timestamp;
    private Integer doctorOrStaffId;
    private Integer patientId;

    public static ObservationDto convertObservationToDto (Observation observation) {
        return new ObservationDto(
                observation.getObservationId(),
                observation.getObservation(),
                observation.getTimestamp(),
                observation.getUserId(),
                observation.getPatientId()
        );
    }

    public static <T extends Observation> List<ObservationDto> convertObservationToDto(List<T> observations) {
        List<ObservationDto> observationDtoList = new ArrayList<>();
        for(T observation : observations) {
            observationDtoList.add(convertObservationToDto(observation));
        }
        return observationDtoList;
    }
}
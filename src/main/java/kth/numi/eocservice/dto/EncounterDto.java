package kth.numi.eocservice.dto;

import kth.numi.eocservice.model.Encounter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncounterDto {

    private Integer encounterId;
    private String reason;
    private LocalDateTime timestamp;
    private Integer patientId;
    private Integer doctorId;
    private Integer observationId;

    public EncounterDto(Integer encounterId, String reason, LocalDateTime timestamp, Integer patientId, Integer doctorId) {
        this.encounterId = encounterId;
        this.reason = reason;
        this.timestamp = timestamp;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public static EncounterDto convertEncounterToDto(Encounter encounter) {
        if (encounter.getObservationId() != null) {
            return new EncounterDto(
                    encounter.getEncounterId(),
                    encounter.getReason(),
                    encounter.getTimestamp(),
                    encounter.getPatientId(),
                    encounter.getDoctorId(),
                    encounter.getObservationId()
            );
        }
        return new EncounterDto(
                encounter.getEncounterId(),
                encounter.getReason(),
                encounter.getTimestamp(),
                encounter.getPatientId(),
                encounter.getDoctorId()
        );
    }

    public static <T extends Encounter> List<EncounterDto> convertEncounterToDto(List<T> encounters) {
        List<EncounterDto> encounterDtoList = new ArrayList<>();
        for(T encounter : encounters) {
            encounterDtoList.add(convertEncounterToDto(encounter));
        }
        return encounterDtoList;
    }
}
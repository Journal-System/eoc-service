package kth.numi.eocservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Encounter")
@Entity
public class Encounter {

    @Id
    @Column(name = "encounter_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private int encounterId;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "Timestamp", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(example = "1")
    private Integer patientId;

    @Schema(example = "2")
    private Integer doctorId;

    @Schema(example = "3")
    private Integer observationId;
}
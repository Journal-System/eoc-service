package kth.numi.eocservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Observation")
@Entity
public class Observation {

    @Id
    @Column(name = "observation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private int observationId;

    @Column(name = "observation", nullable = false)
    private String observation;

    @Column(name = "Timestamp", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    @Schema(name = "1")
    private Integer patientId;

    @Schema(name = "2")
    private Integer userId; // Staff or Doctor who made the observation
}

package kth.numi.eocservice.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`condition`")
@Entity
public class Condition {

    @Id
    @Column(name = "`condition_id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private int conditionId;

    @Column(name = "`condition`", nullable = false)
    @Schema(example = "Cancer")
    private String condition;

    @Column(name = "description")
    @Schema(example = "Stage 1, hopefully curable")
    private String description;

    @Schema(example = "1")
    private Integer patientId;
}

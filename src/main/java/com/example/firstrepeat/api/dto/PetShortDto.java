package com.example.firstrepeat.api.v1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link com.example.firstrepeat.model.Pet}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetShortDto {
    private String name;
    private LocalDate birthDate;
    private Long typeId;
    private List<VisitDto> visits;
    private Long ownerId;

    /**
     * DTO for {@link com.example.firstrepeat.model.Visit}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VisitDto {
        private Long id;
        private LocalDate visit_date;
        @NotBlank
        private String description;
        private Long assignedVetId;
    }
}
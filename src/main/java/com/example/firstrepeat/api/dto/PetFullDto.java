package com.example.firstrepeat.api.v1.dto;

import jakarta.validation.constraints.Digits;
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
public class PetFullDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private PetTypeDto type;
    private List<VisitDto> visits;
    private OwnerDto owner;

    /**
     * DTO for {@link com.example.firstrepeat.model.PetType}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PetTypeDto {
        private String name;
    }

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
        private Long petId;
        private Long assignedVetId;
    }

    /**
     * DTO for {@link com.example.firstrepeat.model.Owner}
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OwnerDto {
        @NotBlank
        private String firstName;
        @NotBlank
        private String lastName;
        @NotBlank
        private String address;
        @NotBlank
        private String city;
        @Digits(integer = 10, fraction = 0)
        @NotBlank
        private String telephone;
    }
}
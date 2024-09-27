package com.example.firstrepeat.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for {@link com.example.firstrepeat.model.Pet}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetCreateDto {
    private String name;
    private LocalDate birthDate;
    private Long typeId;
    private Long ownerId;
}
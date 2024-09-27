package com.example.firstrepeat.model;

import io.github.joselion.springr2dbcrelationships.annotations.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@Table("visits")
public class Visit {

    @Id
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate visit_date;

    @NotBlank
    private String description;

    private Long petId; // It's needed for MtO rel.

    @ManyToOne
    private Pet pet;

    @Column("assigned_vet_id")
    private Long assignedVetId; //It's needed for MtO rel.

    @ManyToOne(foreignKey = "assigned_vet_id")
    private Vet assignedVet;
}
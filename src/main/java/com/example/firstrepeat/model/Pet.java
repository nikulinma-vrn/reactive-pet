package com.example.firstrepeat.model;

import io.github.joselion.springr2dbcrelationships.annotations.ManyToOne;
import io.github.joselion.springr2dbcrelationships.annotations.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Table("pets")
public class Pet {

    @Id
    private Long id;

    @Column("name")
    private String name;


    @Column("birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Column("type_id")
    private Long typeId; // It is needed for MtO relationship

    @ManyToOne
    private PetType type;

    @OneToMany(mappedBy = "pet_id")
    private List<Visit> visits = new ArrayList<>();

    @Column("owner_id")
    private Long ownerId; //It's needed for MtO rel.

    @ManyToOne(foreignKey = "owner_id")
    private Owner owner;

}
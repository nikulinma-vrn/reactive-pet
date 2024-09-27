package com.example.firstrepeat.model;

import io.github.joselion.springr2dbcrelationships.annotations.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table("specialties")
public class Specialty {

    @Id
    private Long id;

    @Column("name")
    private String name;

    @ManyToMany
    private List<Vet> vets = new ArrayList<>();
}

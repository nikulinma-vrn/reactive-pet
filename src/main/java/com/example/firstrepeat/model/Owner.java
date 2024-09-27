package com.example.firstrepeat.model;


import io.github.joselion.springr2dbcrelationships.annotations.OneToMany;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Setter
@EqualsAndHashCode
@Table("owners")
@Getter
public class Owner {

    @Id
    private Long id;

    @Column("first_name")
    @NotBlank
    private String firstName;

    @Column("last_name")
    @NotBlank
    private String lastName;

    @Column("address")
    @NotBlank
    private String address;

    @Column("city")
    @NotBlank
    private String city;

    @Column("telephone")
    @Digits(integer = 10, fraction = 0)
    @NotBlank
    private String telephone;

    @OneToMany(mappedBy = "owner_id")
    private List<Pet> pets = new ArrayList<>();

    public @NotBlank String getFirstName() {
        return this.firstName;
    }

    public @NotBlank String getLastName() {
        return this.lastName;
    }

    public @NotBlank String getAddress() {
        return this.address;
    }

    public @NotBlank String getCity() {
        return this.city;
    }

    public @Digits(integer = 10, fraction = 0) @NotBlank String getTelephone() {
        return this.telephone;
    }
}
package com.example.firstrepeat.api.v1.mapper;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {
    PetShortDto toPetShortDto(Pet pet);

    PetFullDto toPetFullDto(Pet pet);

    Pet toEntity(PetCreateDto petCreateDto);

    Pet updateWithNull(PetFullDto petFullDto, @MappingTarget Pet pet);
}
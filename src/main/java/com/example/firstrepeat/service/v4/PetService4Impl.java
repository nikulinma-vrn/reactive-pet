package com.example.firstrepeat.service.v4;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.api.v1.mapper.PetMapper;
import com.example.firstrepeat.model.Pet;
import com.example.firstrepeat.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PetService4Impl implements PetService4 {

    private final PetMapper petMapper;

    private final PetRepository petRepository;

    @Override
    public Mono<Void> deleteById(Long id) {
        return petRepository.deleteById(id);
    }

    @Override
    public Flux<PetShortDto> findAll() {
        Flux<Pet> pet = petRepository.findAll();
        return pet.map(petMapper::toPetShortDto);
    }

    @Override
    public Mono<PetFullDto> findById(Long id) {
        Mono<Pet> pet = petRepository.findById(id);
        return pet.map(petMapper::toPetFullDto);
    }

    @Override
    public Mono<PetFullDto> save(PetCreateDto petCreateDto) {
        Pet pet = petMapper.toEntity(petCreateDto);
        Mono<Pet> resultPet = petRepository.save(pet);
        return resultPet.map(petMapper::toPetFullDto);
    }
}

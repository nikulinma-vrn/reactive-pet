package com.example.firstrepeat.service.v2;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.api.v1.mapper.PetMapper;
import com.example.firstrepeat.model.Pet;
import com.example.firstrepeat.repository.PetRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

    private final PetMapper petMapper;

    private final PetRepository petRepository;

    private final ObjectMapper objectMapper;

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
        // return pet.map(petMapper::toPetFullDto); // <- так генерируется, проблема с несуществующим id
        return pet.map(petMapper::toPetFullDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Id {" + id + "} doesn't exist")));
    }

    @Override
    public Mono<PetFullDto> save(PetCreateDto petCreateDto) {
        Pet pet = petMapper.toEntity(petCreateDto);
        Mono<Pet> resultPet = petRepository.save(pet);
        return resultPet.map(petMapper::toPetFullDto);
    }

//    Генерируется неработоспособный код.
//
//    @Override
//    public PetFullDto patch(Long id, JsonNode patchNode) throws IOException {
//        Pet pet = petRepository.findById(id);
//
//        PetFullDto petFullDto = petMapper.toPetFullDto(pet);
//        objectMapper.readerForUpdating(petFullDto).readValue(patchNode);
//        petMapper.updateWithNull(petFullDto, pet);
//
//        Mono<Pet> resultPet = petRepository.save(pet);
//        return petMapper.toPetFullDto(resultPet);
//    }

    //    ...вот так хотябы работает, тоже обрабатываем несуществующий id, иначе 200 ОК и пусто
    public Mono<PetFullDto> patch(@PathVariable Long id, @RequestBody JsonNode pathNode) {
        Mono<Pet> petMono = petRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Id {" + id + "} doesn't exist")));
        return petMono.flatMap((pet -> {
            try {
                objectMapper.readerForUpdating(pet).readValue(pathNode);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return petRepository.save(pet);
        })).map(petMapper::toPetFullDto);
    }
}

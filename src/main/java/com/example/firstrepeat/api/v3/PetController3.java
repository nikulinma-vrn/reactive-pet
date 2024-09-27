package com.example.firstrepeat.api.v3;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.api.v1.mapper.PetMapper;
import com.example.firstrepeat.model.Pet;
import com.example.firstrepeat.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v3/pets")
@RequiredArgsConstructor
public class PetController3 {

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteById(@PathVariable Long id) {
        Mono<Void> resultVoid = petRepository.deleteById(id);
        return ResponseEntity.ok(resultVoid);
    }

    @GetMapping
    public ResponseEntity<Flux<PetShortDto>> findAll() {
        Flux<Pet> pet = petRepository.findAll();
        Flux<PetShortDto> petShortDto = pet.map(petMapper::toPetShortDto);
        return ResponseEntity.ok(petShortDto);
    }


//    Работает, если id существует. Иначе пустое тело и 200 ОК
//    @GetMapping("/{id}")
//    public ResponseEntity<Mono<PetFullDto>> findById(@PathVariable Long id) {
//        Mono<Pet> pet = petRepository.findById(id);
//        Mono<PetFullDto> petFullDto = pet.map(petMapper::toPetFullDto);
//        return ResponseEntity.ok(petFullDto);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<PetFullDto>> findById(@PathVariable Long id) {
        Mono<Pet> pet = petRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Id {" + id + "} doesn't exist")));
        Mono<PetFullDto> petFullDto = pet.map(petMapper::toPetFullDto);
        return ResponseEntity.ok(petFullDto);
    }

//    ...при этом, если генерировать ResponseEntity внутри Mono, то можно вот так:
//    @GetMapping("/{id}")
//    public Mono<ResponseEntity<PetFullDto>> findById(@PathVariable Long id) {
//        return petRepository.findById(id)
//                .map(rb -> new ResponseEntity<>(petMapper.toPetFullDto(rb), HttpStatus.OK))
//                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    @PostMapping
    public ResponseEntity<Mono<PetFullDto>> save(@RequestBody PetCreateDto petCreateDto) {
        Pet pet = petMapper.toEntity(petCreateDto);
        Mono<Pet> resultPet = petRepository.save(pet);
        Mono<PetFullDto> petFullDto = resultPet.map(petMapper::toPetFullDto);
        return ResponseEntity.ok(petFullDto);
    }

    // С методом patch ситуация аналогичная, как и в двух предыдущих контроллерах.
}


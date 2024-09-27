package com.example.firstrepeat.api.v2;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.service.v2.PetService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api/v2/pets")
@RequiredArgsConstructor
public class PetController2 {

    private final PetService petService;

    // Работает корректно
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return petService.deleteById(id);
    }

    // Работает корректно
    @GetMapping
    public Flux<PetShortDto> findAll() {
        return petService.findAll();
    }

    // Работает корректно, если id существует.
    @GetMapping("/{id}")
    public Mono<PetFullDto> findById(@PathVariable Long id) {
        return petService.findById(id);
    }

    @PostMapping
    public Mono<PetFullDto> save(@RequestBody PetCreateDto petCreateDto) {
        return petService.save(petCreateDto);
    }

//    Сгенерировано так:
//    @PatchMapping("/{id}")
//    public PetFullDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
//        return petService.patch(id, patchNode);
//    }

    @PatchMapping("/{id}")
    public Mono<PetFullDto> patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        return petService.patch(id, patchNode);
    }
}


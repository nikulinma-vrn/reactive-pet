package com.example.firstrepeat.service.v4;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetService4 {
    Mono<Void> deleteById(Long id);

    Flux<PetShortDto> findAll();

    Mono<PetFullDto> findById(Long id);

    Mono<PetFullDto> save(PetCreateDto petCreateDto);
}

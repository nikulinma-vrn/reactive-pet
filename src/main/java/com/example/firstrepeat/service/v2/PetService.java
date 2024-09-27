package com.example.firstrepeat.service.v2;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetService {
    Mono<Void> deleteById(Long id);

    Flux<PetShortDto> findAll();

    Mono<PetFullDto> findById(Long id);

    Mono<PetFullDto> save(PetCreateDto petCreateDto);

//  Сгенерировался без Mono
//  PetFullDto patch(Long id, JsonNode patchNode) throws IOException;
    Mono<PetFullDto> patch(Long id, JsonNode patchNode);

}

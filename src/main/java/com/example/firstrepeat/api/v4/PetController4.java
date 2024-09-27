package com.example.firstrepeat.api.v4;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.service.v4.PetService4;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v4/pets")
@RequiredArgsConstructor
public class PetController4 {

    // Также требуется обработка несуществующих ID в сервисе
    // С patch проблемы те же.

    private final PetService4 petService4;

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteById(@PathVariable Long id) {
        Mono<Void> resultVoid = petService4.deleteById(id);
        return ResponseEntity.ok(resultVoid);
    }

    @GetMapping
    public ResponseEntity<Flux<PetShortDto>> findAll() {
        Flux<PetShortDto> petShortDto = petService4.findAll();
        return ResponseEntity.ok(petShortDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<PetFullDto>> findById(@PathVariable Long id) {
        Mono<PetFullDto> petFullDto = petService4.findById(id);
        return ResponseEntity.ok(petFullDto);
    }

    @PostMapping
    public ResponseEntity<Mono<PetFullDto>> save(@RequestBody PetCreateDto petCreateDto) {
        Mono<PetFullDto> petFullDto = petService4.save(petCreateDto);
        return ResponseEntity.ok(petFullDto);
    }
}


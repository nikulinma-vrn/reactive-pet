package com.example.firstrepeat.repository;

import com.example.firstrepeat.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PetRepository extends ReactiveCrudRepository<Pet, Long> {

}

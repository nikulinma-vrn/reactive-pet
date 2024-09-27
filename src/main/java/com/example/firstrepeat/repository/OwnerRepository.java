package com.example.firstrepeat.repository;

import com.example.firstrepeat.model.Owner;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends ReactiveCrudRepository<Owner, Long> {

}

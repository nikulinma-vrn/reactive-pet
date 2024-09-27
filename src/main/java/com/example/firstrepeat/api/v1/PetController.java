package com.example.firstrepeat.api.v1;

import com.example.firstrepeat.api.v1.dto.PetCreateDto;
import com.example.firstrepeat.api.v1.dto.PetFullDto;
import com.example.firstrepeat.api.v1.dto.PetShortDto;
import com.example.firstrepeat.api.v1.mapper.PetMapper;
import com.example.firstrepeat.model.Pet;
import com.example.firstrepeat.repository.PetRepository;
import com.example.firstrepeat.service.v2.PetService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    private final ObjectMapper objectMapper;

    private final PetService petService;

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return petRepository.deleteById(id);
    }

    //  Работает корректно.
    @GetMapping
    public Flux<PetShortDto> findAll() {
        Flux<Pet> pet = petRepository.findAll();
        return pet.map(petMapper::toPetShortDto);
    }

    //  Сгенерированный метод. Работает корректно, если id существует.
    //  Для несуществующего id возвращается пустой Mono со статусом 200 ОК
    //  @GetMapping("/{id}")
    // public Mono<PetFullDto> getOne(@PathVariable Long id) {
    //    Mono<Pet> pet = petRepository.findById(id);
    //    return pet.map(petMapper::toPetFullDto);
    // }

    // ...а так можно давать адекватный ответ при запросе в несуществующий id
    @GetMapping("/{id}")
    public Mono<PetFullDto> findById(@PathVariable Long id) {
        Mono<Pet> pet = petRepository.findById(id); // <-- генерируемый код
        return pet.map(petMapper::toPetFullDto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Id {" + id + "} doesn't exist")));
    }

    // При возврате DTO есть нюанс, что связные сущности не успевают подгрузиться до маппинга
    // Если возвращать просто объект (без маппинга), то всё возвращается корректно
    @PostMapping
    public Mono<PetFullDto> save(@RequestBody PetCreateDto petCreateDto) {
        Pet pet = petMapper.toEntity(petCreateDto);
        Mono<Pet> resultPet = petRepository.save(pet);
        return resultPet.map(petMapper::toPetFullDto);
    }

    //    В методе patch генерируется нерабочий код даже при наличии необходимых методов
    //    в репозитории. Если в репозитории "правильных" методов нет, то генерируется вообще
    //    вот такой странный результат.
    //
    //    @PatchMapping("/{id}")
    //    public PetFullDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
    //        Pet pet = petRepository.findById(id);
    //
    //        PetFullDto petFullDto = petMapper.toPetFullDto(pet);
    //        objectMapper.readerForUpdating(petFullDto).readValue(patchNode);
    //        petMapper.updateWithNull(petFullDto, pet);
    //
    //        Mono<Pet> resultPet = petRepository.save(pet);
    //        return petMapper.toPetFullDto(resultPet);
    //    }

    // Рабочий код. Тоже нужно обрабатывать несуществующие id, иначе 200 ОК
    // При возврате DTO есть нюанс, что связные сущности не подгружаются (null). При этом
    // если запросить Get по id, то видно что изменения сохранены корректно.
    // Если возвращать просто объект (без маппинга), то всё возвращается корректно
    @PatchMapping("/{id}")
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


    @GetMapping("/pg/{page}")
    public Mono<Page<Pet>> getAll(@PathVariable int page) {
        Mono<Page<Pet>> pet = petRepository.findBy(PageRequest.of(page, 10));
        return pet;
    }
}
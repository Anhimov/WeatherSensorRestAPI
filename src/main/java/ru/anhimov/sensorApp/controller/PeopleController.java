package ru.anhimov.sensorApp.controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anhimov.sensorApp.dto.PersonDTO;
import ru.anhimov.sensorApp.exception.PersonNotValidException;
import ru.anhimov.sensorApp.service.PeopleService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService,
                            ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople() {
        return peopleService
                .findAll()
                .stream()
                .map(peopleService::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return peopleService
                .convertToPersonDTO(peopleService.findOne(id)); // Jackson конвертирует в JSON
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createPerson(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            bindingResult
                    .getFieldErrors()
                    .forEach(error -> sb.append(error.getField())
                            .append(" - ")
                            .append(error.getDefaultMessage())
                            .append(";"));
            throw new PersonNotValidException(sb.toString());
        }
        peopleService.save(peopleService.convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}

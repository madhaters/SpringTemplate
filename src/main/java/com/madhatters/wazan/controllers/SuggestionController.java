package com.madhatters.wazan.controllers;

import com.madhatters.wazan.model.Person;
import com.madhatters.wazan.model.Suggestion;
import com.madhatters.wazan.repositories.SuggestionRepository;
import com.madhatters.wazan.utils.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suggestions")
@CrossOrigin(origins = "*")
public class SuggestionController implements CrudController<Suggestion> {
    private SuggestionRepository repository;


    @Autowired
    public SuggestionController(SuggestionRepository repository) {
        this.repository = repository;
    }

    @PostMapping(value = "/insert", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> insert(@RequestBody Suggestion suggestion) {
        Suggestion suggestion11 = repository.insert(suggestion);
        return ResponseEntity.ok(suggestion11);
    }

    @PutMapping(value = "/update/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Suggestion suggestion) {
        suggestion.setId(id);
        repository.save(suggestion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/delete/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> delete(@PathVariable String id) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "filter", required = false) String filter) {
        Page<Suggestion> suggestions = repository.findAll(pageable);
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping(value = "/get/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> getOne(@PathVariable String id) {
        Suggestion suggestion = repository.findOne(id);
        return ResponseEntity.ok(suggestion);
    }

    @PostMapping(value = "/getSuggestion", produces = MimeTypes.JSON)
    public ResponseEntity<?> findSuggestionForPerson(Pageable pageable, @RequestBody Person person) {
        FormulaCalulator.Gender gender = person.getGender().equalsIgnoreCase("male") ?
                FormulaCalulator.Gender.MALE : FormulaCalulator.Gender.FEMALE;

        double kcal = FormulaCalulator.calculate(person.getWeight(),
                person.getHeight(), person.getAge(), gender);
        double factor = -1;
        switch (person.getActivity()) {
            case Person.Activity.ACTIVE:
                factor = 1.5;
                break;
            case Person.Activity.NORMAL:
                factor = 1.3;
                break;
            case Person.Activity.VERY_ACTIVE:
                factor = 1.7;
                break;
        }
        if (factor == -1) {
            throw new IllegalArgumentException("Activity not found");
        }
        kcal = factor * kcal;
        Suggestion suggestion = repository.findFirstByMinCaloriesGreaterThanEqual(kcal);
        return ResponseEntity.ok(suggestion);

    }


}

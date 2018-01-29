package com.madhatters.wazan.controllers;

import com.madhatters.wazan.model.Person;
import com.madhatters.wazan.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
@CrossOrigin(origins = "*"
)
public class PersonController implements CrudController<Person> {
    private PersonRepository repository;

    @Autowired
    public PersonController(PersonRepository repository){
        this.repository=repository;
    }
    @Override
    public ResponseEntity<?> insert(Person person) {
        return null;
    }

    @Override
    public ResponseEntity<?> update(String id, Person person) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String filter) {
        return null;
    }

    @Override
    public ResponseEntity<?> getOne(String id) {
        return null;
    }
}

package com.madhatters.wazan.controllers;

import com.madhatters.wazan.model.Form;
import com.madhatters.wazan.repositories.FormRepository;
import com.madhatters.wazan.utils.MimeTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forms")
@CrossOrigin(origins = "*")

public class FormController implements CrudController<Form>{
    private FormRepository repository;

    @Autowired
    public FormController(FormRepository repository){
        this.repository=repository;
    }

    @PostMapping(value = "/insert", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> insert(@RequestBody Form form) {
        long hoursStamp = -1;
        if (form.getExpireHours() > 0) {
            hoursStamp = form.getExpireHours() * 60 * 60 * 1000;
            hoursStamp = System.currentTimeMillis() + hoursStamp;
        }
        form.setTimeStamp(hoursStamp);
        Form form1 = repository.insert(form);
        return ResponseEntity.ok(form1);
    }

    @PutMapping(value = "/update/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Form form) {
        form.setId(id);
        long hoursStamp = -1;
        if (form.getExpireHours() > 0) {
            hoursStamp = form.getExpireHours() * 60 * 60 * 1000;
            hoursStamp = System.currentTimeMillis() + hoursStamp;
        }
        form.setTimeStamp(hoursStamp);
        Form form1 = repository.save(form);
        return ResponseEntity.ok(form1);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable String id) {
        repository.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "filter", required = false) String filter) {
        Page<Form> forms;
        if (filter != null && filter.contains("expired") && filter.contains("true")) {
            forms = repository.findAll(pageable);
        } else {
            forms = repository.findAllByTimeStampGreaterThanOrTimeStampIs(pageable, System.currentTimeMillis(), -1);
        }
        return ResponseEntity.ok(forms);

    }

    @GetMapping(value = "/get/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return ResponseEntity.ok(repository.findOne(id));
    }
}

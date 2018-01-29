package com.madhatters.wazan.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CrudController<T> {
    ResponseEntity<?> insert(T t);
    ResponseEntity<?> update(String id,T t);
    ResponseEntity<?> delete(String id);
    ResponseEntity<?> getAll(Pageable pageable,String filter);
    ResponseEntity<?> getOne(String id);

}

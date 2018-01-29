package com.madhatters.wazan.controllers;

import com.madhatters.wazan.model.Clinic;
import com.madhatters.wazan.repositories.ClinicRepository;
import com.madhatters.wazan.utils.MimeTypes;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/clinics")
@CrossOrigin(origins = "*")
public class ClinicController implements CrudController<Clinic>{
    private ClinicRepository repository;

    @Autowired
    public ClinicController(ClinicRepository repository){
        this.repository = repository;
    }

    @PostMapping(value = "/insert", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> insert(@RequestBody Clinic clinic) {
        Clinic clinic1 = repository.insert(clinic);
        return ResponseEntity.ok(clinic1);
    }

    @PutMapping(value = "/update/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Clinic clinic) {
        clinic.setId(id);
        Clinic clinic1 = repository.save(clinic);
        return ResponseEntity.ok(clinic1);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> delete(@PathVariable String id) {
        repository.delete(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping(value = "/get", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String filter) {
        Page<Clinic> clinics;
        if (filter != null && !filter.equals("{}")) {
            clinics = repository.findAll(queryFromFilters(filter), pageable);
        } else {
            clinics = repository.findAll(pageable);
        }
        return ResponseEntity.ok(clinics);
    }

    private Query queryFromFilters(@NotNull String filters) throws JSONException {
        JSONObject rootObject = new JSONObject(filters);
        Iterator<String> keys = rootObject.keys();
        List<Criteria> criteriaList = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equals("name")) {
                String value = rootObject.getString(key);
                String regex = MongoRegexCreator.INSTANCE.toRegularExpression(value,
                        Part.Type.LIKE);
                criteriaList.add(Criteria.where(key).regex(regex));
                continue;
            } else if (key.equals("location")) {
                JSONObject location = rootObject.getJSONObject(key);
                double lat = location.getDouble("lat");
                double lon = location.getDouble("lon");
                double distance = location.getDouble("distance") / 6378.173;
                Point point = new Point(lon, lat);
                criteriaList.add(Criteria.where("location").nearSphere(point).maxDistance(distance));
                continue;
            }
            criteriaList.add(Criteria.where(key).is(rootObject.get(key)));
        }
        Criteria criteria = new Criteria().andOperator(criteriaList.toArray(
                new Criteria[criteriaList.size()]));
        return new Query().addCriteria(criteria);
    }


    @GetMapping(value = "/get/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return ResponseEntity.ok().body(repository.findOne(id));
    }
}

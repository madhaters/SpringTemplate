package com.madhatters.wazan.controllers;

import com.madhatters.wazan.model.News;
import com.madhatters.wazan.repositories.NewsRepository;
import com.madhatters.wazan.utils.MimeTypes;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/news")
@CrossOrigin(origins = "*")

public class NewsController implements CrudController<News>{
    private NewsRepository repository;

    @Autowired
    public NewsController(NewsRepository repository) {
        this.repository = repository;
    }

    @PostMapping(value = "/insert", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> insert(@RequestBody News news) {
        news.setTimeStamp(System.currentTimeMillis());
        News news1 = repository.insert(news);
        return ResponseEntity.ok(news1);
    }

    @PutMapping(value = "/update/{id}", produces = MimeTypes.JSON)
    @Override
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody News news) {
        news.setId(id);
        News news1 = repository.save(news);
        return ResponseEntity.ok(news1);
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
        Page<News> newsPage;
        if (filter != null && !filter.equals("{}")) {
            newsPage = repository.findAll(queryFromFilters(filter), pageable);
        } else {
            newsPage = repository.findAll(pageable);
        }
        return ResponseEntity.ok(newsPage);
    }

    private Query queryFromFilters(@NotNull String filters) throws JSONException {
        JSONObject rootObject = new JSONObject(filters);
        Iterator<String> keys = rootObject.keys();
        List<Criteria> criteriaList = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equals("title") || key.equals("description")) {
                String value = rootObject.getString(key);
                String regex = MongoRegexCreator.INSTANCE.toRegularExpression(value,
                        Part.Type.LIKE);
                criteriaList.add(Criteria.where(key).regex(regex));
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
        News news = repository.findOne(id);
        return ResponseEntity.ok(news);
    }
}

package com.madhatters.wazan.controllers;

import com.madhatters.wazan.fcm.UserModel;
import com.madhatters.wazan.repositories.UserModelRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/fcm")
@CrossOrigin(value = "*")
@Api("Firebase controller")
public class FcmController {
    private final UserModelRepository mongoOperations;

    @Autowired
    public FcmController(UserModelRepository repository) {
        this.mongoOperations = repository;
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> insert(@RequestBody UserModel userModel) {
        UserModel insertedModel = mongoOperations.findByUserId(userModel.getUserId());
        if (insertedModel != null) {
            if (userModel.getAndroidToken() != null) {
                userModel.setIosToken(insertedModel.getIosToken());
            } else {
                userModel.setAndroidToken(insertedModel.getAndroidToken());
            }
            mongoOperations.delete(insertedModel.getId());
        }
        mongoOperations.save(userModel);
        return ResponseEntity.ok().build();
    }

}

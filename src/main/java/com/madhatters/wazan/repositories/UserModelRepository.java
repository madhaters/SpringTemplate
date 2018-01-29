package com.madhatters.wazan.repositories;


import com.madhatters.wazan.fcm.UserModel;

public interface UserModelRepository extends ResourceRepository<UserModel, String> {
    UserModel findByUserId(String userId);
}

package com.madhatters.wazan.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Person implements Serializable {

    @Id
    private String id;
    @SerializedName("gender")
    private String gender;
    @SerializedName("weight")
    private double weight;
    @SerializedName("height")
    private double height;
    @SerializedName("age")
    private double age;
    @SerializedName("activity")
    private String activity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public static class Activity {
        public static final String ACTIVE = "active";
        public static final String VERY_ACTIVE = "very";
        public static final String NORMAL = "normal";
    }

}

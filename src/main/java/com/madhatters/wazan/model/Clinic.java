package com.madhatters.wazan.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Clinic implements Serializable {
    @Id
    private String id;

    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private double[] location;
    @SerializedName("openingTime")
    private String openingTime;
    @SerializedName("closingTime")
    private String closingTime;
    @SerializedName("openDays")
    private String[] openDays;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String[] getOpenDays() {
        return openDays;
    }

    public void setOpenDays(String[] openDays) {
        this.openDays = openDays;
    }
}

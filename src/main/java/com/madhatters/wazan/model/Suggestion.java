package com.madhatters.wazan.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class Suggestion implements Serializable {
    @Id
    private String id;

    private double minCalories;
    private double maxCalories;
    private String document;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(double minCalories) {
        this.minCalories = minCalories;
    }

    public double getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(double maxCalories) {
        this.maxCalories = maxCalories;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}

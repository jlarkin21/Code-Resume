package com.example.jl06014.travelpicker;

import java.util.UUID;

/**
 * Created by jl06014 on 2/28/2017.
 */

public class Country {

    String name;
    String description;
    int picture;
    UUID id;

    public Country() {
        id =  UUID.randomUUID();
    }

    public Country(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int flag) {
        this.picture = picture;
    }

    public UUID getId() {
        return  id;
    }

}

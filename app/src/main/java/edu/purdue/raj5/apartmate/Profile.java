package edu.purdue.raj5.apartmate;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

public class Profile {


    private String name;

    private String email;

    private String imageUrl;


    private Integer age;


    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String message) {
        this.email = message;
    }

    public String getEmail(){
        return this.email;
    }
}
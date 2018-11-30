package edu.purdue.raj5.apartmate;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

/*
* model for TinderCard
* Getters and Setters for name, email, imageURL, age, and location
*/
public class Profile {


    private String name;

    private String email;

    private String imageUrl;


    private Integer age;


    private String location;
    //Get the name
    public String getName() {
        return name;
    }
    //Set the name
    public void setName(String name) {
        this.name = name;
    }
    //Get the imageURL
    public String getImageUrl() {
        return imageUrl;
    }
    //Set the imageURL
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //Get the Age
    public Integer getAge() {
        return age;
    }
    //Set the Age
    public void setAge(Integer age) {
        this.age = age;
    }
    //Get the potential roommate location
    public String getLocation() {
        return location;
    }
    //Set the potential roommate location
    public void setLocation(String location) {
        this.location = location;
    }
    //Set email
    public void setEmail(String message) {
        this.email = message;
    }
    //Get email
    public String getEmail(){
        return this.email;
    }
}

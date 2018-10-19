package com.example.sid.apartmate;


import java.util.ArrayList;
/*
* Class for the details within the event
* Set the date, name, subject, description
*/
class HomeCollection {
    public String date="";
    public String name="";
    public String subject="";
    public String description="";


    public static ArrayList<HomeCollection> date_collection_arr;
    public HomeCollection(String date, String name, String subject, String description){

        this.date=date;
        this.name=name;
        this.subject=subject;
        this.description= description;

    }
}

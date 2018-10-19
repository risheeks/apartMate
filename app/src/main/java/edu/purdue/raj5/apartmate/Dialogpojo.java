package com.example.sid.apartmate;
/*
* POJO Class for the event dialog
* set and get the titles, subjects, types, duedates
*/
public class Dialogpojo {
    private String titles;
    private String subjects;
    private String types;
    private String duedates;
    private String descripts;
    private String attatchmentd;
    private String sections;
    private String classe;


/*
* set the title of the event
*/
    public void setTitles(String titles) {
        this.titles = titles;
    }
/*
* set the Subject of the event
*/
    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }
/*
* set the Type of the event
*/
    public void setTypes(String types) {
        this.types = types;
    }
/*
* set the duedates of the event
*/
    public void setDuedates(String duedates) {
        this.duedates = duedates;
    }
/*
* set the description of the event
*/
    public void setDescripts(String descripts) {
        this.descripts = descripts;
    }
/*
* set the id of the event
*/
    public void setAttatchmentd(String attatchmentd) {
        this.attatchmentd = attatchmentd;
    }
/*
* get the title of the event
*/
    public String getTitles() {
        return titles;
    }
/*
* get the subjects of the event
*/
    public String getSubjects() {
        return subjects;
    }
/*
* get the types of the event
*/
    public String getTypes() {
        return types;
    }
/*
* get the duedates of the event
*/
    public String getDuedates() {
        return duedates;
    }
/*
* get the description of the event
*/
    public String getDescripts() {
        return descripts;
    }
/*
* get the id of the event
*/
    public String getAttatchmentd() {
        return attatchmentd;
    }

    public void setSections(String sections) {
        this.sections = sections;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getClasse() {
        return classe;
    }

    public String getSections() {
        return sections;
    }
}

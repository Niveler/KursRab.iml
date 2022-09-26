package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Teacher {
    private IntegerProperty id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty midname;
    private StringProperty photo; //хранить в бд будем ссылку на файл
    private StringProperty education;

    public Teacher(Integer id, String surname, String name, String midname, String photo, String education) {
        this.id = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.midname = new SimpleStringProperty(midname);
        this.photo = new SimpleStringProperty(photo);
        this.education = new SimpleStringProperty(education);
    }
    public Teacher () {
        this(0,"","","","","");
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMidname() {
        return midname.get();
    }

    public StringProperty midnameProperty() {
        return midname;
    }

    public void setMidname(String midname) {
        this.midname.set(midname);
    }

    public String getPhoto() {
        return photo.get();
    }

    public StringProperty photoProperty() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo.set(photo);
    }

    public String getEducation() {
        return education.get();
    }

    public StringProperty educationProperty() {
        return education;
    }

    public void setEducation(String education) {
        this.education.set(education);
    }
}

package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private IntegerProperty id;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty midname;
    private StringProperty numclass;
    private IntegerProperty yearadmiss;


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
    public IntegerProperty ageProperty() {
        return yearadmiss;
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

    public String getNumclass() {
        return numclass.get();
    }

    public StringProperty numclassProperty() {
        return numclass;
    }

    public void setNumclass(String numclass) {
        this.numclass.set(numclass);
    }

    public int getYearadmiss() {
        return yearadmiss.get();
    }

    public IntegerProperty yearadmissProperty() {
        return yearadmiss;
    }

    public void setYearadmiss(int yearadmiss) {
        this.yearadmiss.set(yearadmiss);
    }

    public Student(Integer id, String surname, String name, String midname, String numclass, Integer yearadmiss) {
        this.id = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.midname = new SimpleStringProperty(midname);
        this.numclass = new SimpleStringProperty(numclass);
        this.yearadmiss = new SimpleIntegerProperty(yearadmiss);
    }
    public  Student() {
        this(0, "","","","",2020);
    }
}

package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {
    private IntegerProperty id;
    private StringProperty named;
    private IntegerProperty hours;

    public Subject(Integer id, String named, Integer hours) {
        this.id = new SimpleIntegerProperty(id);
        this.named = new SimpleStringProperty(named);
        this.hours = new SimpleIntegerProperty(hours);
    }

    public Subject () {this(0,"",0);}

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNamed() {
        return named.get();
    }

    public StringProperty namedProperty() {
        return named;
    }

    public void setNamed(String named) {
        this.named.set(named);
    }

    public int getHours() {
        return hours.get();
    }

    public IntegerProperty hoursProperty() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours.set(hours);
    }
}

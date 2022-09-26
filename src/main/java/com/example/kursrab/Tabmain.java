package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tabmain {
    private IntegerProperty rating;
    private IntegerProperty sumScore;
    private IntegerProperty sumMark;
    private StringProperty surname;
    private StringProperty name;
    private StringProperty midname;

    public int getRating() {
        return rating.get();
    }

    public IntegerProperty ratingProperty() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating.set(rating);
    }

    public int getSumScore() {
        return sumScore.get();
    }

    public IntegerProperty sumScoreProperty() {
        return sumScore;
    }

    public void setSumScore(int sumScore) {
        this.sumScore.set(sumScore);
    }

    public int getSumMark() {
        return sumMark.get();
    }

    public IntegerProperty sumMarkProperty() {
        return sumMark;
    }

    public void setSumMark(int sumMark) {
        this.sumMark.set(sumMark);
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
    public Tabmain(Integer rating, Integer sumScore, Integer sumMark, String surname, String name, String midname) {
        this.rating =new SimpleIntegerProperty(rating) ;
        this.sumScore = new SimpleIntegerProperty(sumScore);
        this.sumMark = new SimpleIntegerProperty(sumMark);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.midname = new SimpleStringProperty(midname);
    }
}

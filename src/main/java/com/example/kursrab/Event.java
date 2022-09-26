package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {
    private IntegerProperty id;
    private IntegerProperty prizeid;
    private IntegerProperty studentid;
    private SimpleStringProperty called;
    private SimpleStringProperty date;
    private SimpleStringProperty organize;
    private SimpleStringProperty place;
    private SimpleStringProperty prizetype;
    private SimpleStringProperty studsurname;
    private SimpleStringProperty studname;
    private SimpleStringProperty studmidname;



    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getPrizeid() {
        return prizeid.get();
    }

    public IntegerProperty prizeidProperty() {
        return prizeid;
    }

    public void setPrizeid(int prizeid) {
        this.prizeid.set(prizeid);
    }

    public int getStudentid() {
        return studentid.get();
    }

    public IntegerProperty studentidProperty() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid.set(studentid);
    }

    public String getCalled() {
        return called.get();
    }

    public SimpleStringProperty calledProperty() {
        return called;
    }

    public void setCalled(String called) {
        this.called.set(called);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getOrganize() {
        return organize.get();
    }

    public SimpleStringProperty organizeProperty() {
        return organize;
    }

    public void setOrganize(String organize) {
        this.organize.set(organize);
    }

    public String getPlace() {
        return place.get();
    }

    public SimpleStringProperty placeProperty() {
        return place;
    }

    public void setPlace(String place) {
        this.place.set(place);
    }

    public String getPrizetype() {
        return prizetype.get();
    }

    public SimpleStringProperty prizetypeProperty() {
        return prizetype;
    }

    public void setPrizetype(String prizetype) {
        this.prizetype.set(prizetype);
    }

    public String getStudsurname() {
        return studsurname.get();
    }

    public SimpleStringProperty studsurnameProperty() {
        return studsurname;
    }

    public void setStudsurname(String studsurname) {
        this.studsurname.set(studsurname);
    }

    public String getStudname() {
        return studname.get();
    }

    public SimpleStringProperty studnameProperty() {
        return studname;
    }

    public void setStudname(String studname) {
        this.studname.set(studname);
    }

    public String getStudmidname() {
        return studmidname.get();
    }

    public SimpleStringProperty studmidnameProperty() {
        return studmidname;
    }

    public void setStudmidname(String studmidname) {
        this.studmidname.set(studmidname);
    }

    public Event(Integer id, Integer prizeid, Integer studentid, String called, String date, String organize, String place, String prizetype, String studsurname, String studname, String studmidname) {
        this.id = new SimpleIntegerProperty(id);
        this.prizeid = new SimpleIntegerProperty(prizeid);
        this.studentid = new SimpleIntegerProperty(studentid);
        this.called = new SimpleStringProperty(called);
        this.date = new SimpleStringProperty(date);
        this.organize = new SimpleStringProperty(organize);
        this.place = new SimpleStringProperty(place);
        this.prizetype= new SimpleStringProperty(prizetype);
        this.studsurname = new SimpleStringProperty(studsurname);
        this.studname = new SimpleStringProperty(studname);
        this.studmidname = new SimpleStringProperty(studmidname);
    }
    public Event(){
        this(0,0,0,"","","","","","","","");
    }
}

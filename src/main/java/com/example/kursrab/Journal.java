package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Journal {
    private IntegerProperty id;
    private SimpleStringProperty date;
    private IntegerProperty score;
    private IntegerProperty studentid;
    private IntegerProperty subjectid;
    private SimpleStringProperty studsurname;
    private SimpleStringProperty studname;
    private SimpleStringProperty studmidname;
    private SimpleStringProperty subjectname;

    public String getSubjectname() {
        return subjectname.get();
    }

    public SimpleStringProperty subjectnameProperty() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname.set(subjectname);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
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

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public SimpleStringProperty getDate() {
        return date;
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    public int getScore() {
        return score.get();
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void setScore(int score) {
        this.score.set(score);
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

    public int getSubjectid() {
        return subjectid.get();
    }

    public IntegerProperty subjectidProperty() {
        return subjectid;
    }

    public void setSubjectid(int subjectid) {
        this.subjectid.set(subjectid);
    }

    public  Journal(Integer id,String date, Integer score,Integer studentid, Integer subjectid, String studsurname,String studname,String studmidname, String subjectname) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.score = new SimpleIntegerProperty(score);
        this.studentid = new SimpleIntegerProperty(studentid);
        this.subjectid = new SimpleIntegerProperty(subjectid);
        this.studsurname = new SimpleStringProperty(studsurname);
        this.studname = new SimpleStringProperty(studname);
        this.studmidname = new SimpleStringProperty(studmidname);
        this.subjectname = new SimpleStringProperty(subjectname);
    }
    public  Journal(){this(0,"",0,0,0,"","","","");}
}

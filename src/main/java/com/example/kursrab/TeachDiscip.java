package com.example.kursrab;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TeachDiscip {
    private IntegerProperty id;
    private IntegerProperty subjectid;
    private  IntegerProperty teacherid;

    private StringProperty teachername;
    private StringProperty teachersurname;
    private StringProperty teachermidname;
    private StringProperty subjectname;
    public int getId() {
        return id.get();
    }

    public String getTeachername() {
        return teachername.get();
    }

    public StringProperty teachernameProperty() {
        return teachername;
    }

    public void setTeachername(String teachername) {
        this.teachername.set(teachername);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public int getTeacherid() {
        return teacherid.get();
    }

    public IntegerProperty teacheridProperty() {
        return teacherid;
    }

    public void setTeacherid(int teacherid) {
        this.teacherid.set(teacherid);
    }

    public String getTeachersurname() {
        return teachersurname.get();
    }

    public StringProperty teachersurnameProperty() {
        return teachersurname;
    }

    public void setTeachersurname(String teachersurname) {
        this.teachersurname.set(teachersurname);
    }

    public String getTeachermidname() {
        return teachermidname.get();
    }

    public StringProperty teachermidnameProperty() {
        return teachermidname;
    }

    public void setTeachermidname(String teachermidname) {
        this.teachermidname.set(teachermidname);
    }

    public String getSubjectname() {
        return subjectname.get();
    }

    public StringProperty subjectnameProperty() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname.set(subjectname);
    }

    public TeachDiscip(Integer id, Integer subjectid, Integer teacherid,
                       String teachersurname,String teachername, String teachermidname, String subjectname) {
        this.id = new SimpleIntegerProperty(id);
        this.subjectid = new SimpleIntegerProperty(subjectid);
        this.teacherid = new SimpleIntegerProperty(teacherid);
        this.teachername = new SimpleStringProperty(teachername);
        this.teachersurname = new SimpleStringProperty(teachersurname);
        this.teachermidname = new SimpleStringProperty(teachermidname);
        this.subjectname = new SimpleStringProperty(subjectname);
    }
    public TeachDiscip() {this(0,0,0,"","","","");}
}

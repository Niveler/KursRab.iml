package com.example.kursrab.controllers;

import com.example.kursrab.Event;
import com.example.kursrab.Prize;
import com.example.kursrab.Student;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewEventController {
    public TextField tfCalled;
    public DatePicker dpDate;
    public ComboBox cmBoxStud;
    public ComboBox cmBoxTypePrize;
    public TextField tfOrganizer;
    public TextField tfPlace;
    private Event event;
    static Statement statement;
    static ResultSet resultSet;
    int studid;
    int prizeid;
    String studsurname;
    String studname;
    String studmidname;
    String prizetype;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Prize> prizes = FXCollections.observableArrayList();
    public Stage stageNewEvent;
    public void setEvent(Event event) {this.event = event;}
    public void setStage(Stage stage) {this.stageNewEvent= stage;}
    public void selectStudents() throws SQLException {
        statement = MainController.connection.createStatement();
        resultSet = statement.executeQuery("select * from student");
        while (resultSet.next()) {
            studid = resultSet.getInt("id");
            studsurname = resultSet.getString("surname");
            studname = resultSet.getString("name");
            studmidname = resultSet.getString("midname");
            students.add(new Student(studid,studsurname,studname,studmidname,"",0));
            cmBoxStud.getItems().add(studsurname + " " + studname + " " + studmidname);
        }
    }
    public void selectPrizeType() throws SQLException {
        statement = MainController.connection.createStatement();
        resultSet = statement.executeQuery("SELECT * from prize");
        while (resultSet.next()) {
            prizeid = resultSet.getInt("id");
            prizetype = resultSet.getString("type");
            prizes.add(new Prize(prizeid,0,prizetype));
            cmBoxTypePrize.getItems().add(prizetype);
        }
    }
    public int onChangedStud(ActionEvent actionEvent) throws SQLException {
        String[] tmp = cmBoxStud.getValue().toString().split(" ");
        studsurname = tmp[0];
        studname = tmp[1];
        studmidname = tmp[2];
        statement = MainController.connection.createStatement();
        resultSet = statement.executeQuery("select id from student where surname='"+studsurname+"'");
        while (resultSet.next()) {
            studid = resultSet.getInt("id");
        }
        return  studid;
    }

    public void onChangedType(ActionEvent actionEvent) throws SQLException {
        prizetype = cmBoxTypePrize.getValue().toString();
        statement = MainController.connection.createStatement();
        resultSet= statement.executeQuery("select id from prize where type='"+prizetype+"'");
        while (resultSet.next()) {
            prizeid = resultSet.getInt("id");
        }
    }

    public void onOk(ActionEvent actionEvent) {
        event.setCalled(tfCalled.getText());
        event.setDate(dpDate.getValue().toString());
        event.setOrganize(tfOrganizer.getText());
        event.setPlace(tfPlace.getText());
        event.setStudentid(studid);
        event.setPrizeid(prizeid);
        stageNewEvent.close();
    }

    public void onCancel(ActionEvent actionEvent) {stageNewEvent.close();}
}

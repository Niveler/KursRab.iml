package com.example.kursrab.controllers;

import com.example.kursrab.Journal;
import com.example.kursrab.Student;
import com.example.kursrab.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewJournalController {
    public ComboBox cmBoxStud;
    public ComboBox cmBoxSubj;
    public ComboBox<Integer> cmBoxScore;
    private Journal journal;
    static Statement statement;
    static ResultSet resultSet;
    int studid;
    int subjid;
    String studsurname;
    String studname;
    String studmidname;
    int score;
    String subjname;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    private ObservableList<Subject> subjects = FXCollections.observableArrayList();
    private Stage stageNewJournal;
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public void setStage(Stage stage) {
        this.stageNewJournal = stage;
    }

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

    public void selectSubjects() throws SQLException {
        statement = MainController.connection.createStatement();
        resultSet= statement.executeQuery("SELECT * from subject");
        while (resultSet.next()) {
            subjid = resultSet.getByte("id");
            subjname = resultSet.getString("named");
            subjects.add((new Subject(subjid,subjname,0)));
            cmBoxSubj.getItems().add(subjname);
        }
    }
    public void selectScores() {
        cmBoxScore.getItems().addAll(1,2,3,4,5);
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

    public void onChangedSubj(ActionEvent actionEvent) throws SQLException {
        subjname = cmBoxSubj.getValue().toString();
        statement = MainController.connection.createStatement();
        resultSet= statement.executeQuery("select id from subject where named='"+subjname+"'");
        while (resultSet.next()) {
            subjid = resultSet.getInt("id");
        }
    }

    public void onOk(ActionEvent actionEvent) {
        journal.setSubjectid(subjid);
        journal.setStudentid(studid);
        journal.setScore(score);
        stageNewJournal.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        stageNewJournal.close();
    }

    public void onChangedScore(ActionEvent actionEvent) {
        score = cmBoxScore.getValue();
    }
}

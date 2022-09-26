package com.example.kursrab.controllers;

import com.example.kursrab.Subject;
import com.example.kursrab.TeachDiscip;
import com.example.kursrab.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NewTeachDiscipController {
    public ComboBox cmBoxTeach;
    public ComboBox cmBoxSubj;
    public Label lbtext;
    private TeachDiscip teachDiscip;
    static Statement statement;
    static ResultSet resultSet;
    int teachid;
    String teachsurname;
    String teachname;
    String teachmidname;
    int subjid;
    String subjname;
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private ObservableList<Subject> subjects = FXCollections.observableArrayList();
    private Stage stageNewTeachDiscip;
    public void setStage(Stage stage) {
        this.stageNewTeachDiscip = stage;
    }

    public void setTeachDiscip(TeachDiscip teachDiscip) {
        this.teachDiscip = teachDiscip;
    }

    public void onOk(ActionEvent actionEvent) {
        teachDiscip.setSubjectid(subjid);
        teachDiscip.setTeacherid(teachid);
        stageNewTeachDiscip.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        stageNewTeachDiscip.close();
    }
    //Заполняем combobox учетилями
    public void selectTeachers() throws SQLException {
        statement = MainController.connection.createStatement();
        resultSet= statement.executeQuery("SELECT * from teachers");
        while (resultSet.next()) {
            teachid = resultSet.getInt("id");
            teachsurname = resultSet.getString("surname");
            teachname = resultSet.getString("name");
            teachmidname = resultSet.getString("midname");
            teachers.add(new Teacher(teachid,teachsurname,teachname,teachmidname,"",""));
            cmBoxTeach.getItems().add(teachsurname + " " + teachname + " " + teachmidname);
        }
    }
    //Заполняем combobox предметами
    public void selectSubjects() throws SQLException{
        statement = MainController.connection.createStatement();
        resultSet= statement.executeQuery("SELECT * from subject");
        while (resultSet.next()) {
            subjid = resultSet.getByte("id");
            subjname = resultSet.getString("named");
            subjects.add((new Subject(subjid,subjname,0)));
            cmBoxSubj.getItems().add(subjname);
        }
    }

    public int onChangedTeach(ActionEvent actionEvent)  {
            String[] tmp = cmBoxTeach.getValue().toString().split(" "); // разбираем полученное значение
            teachsurname = tmp[0];// забираем фамилию
        try {
            statement = MainController.connection.createStatement();
            resultSet= statement.executeQuery("select id from teachers where teachers.surname='"+teachsurname+"'");
            while (resultSet.next()) {
                teachid = resultSet.getInt("id");
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось получить данные из БД");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.exit(0);
        }
        return teachid;
    }

    public void onChangedSubj(ActionEvent actionEvent) throws SQLException {
        subjname = cmBoxSubj.getValue().toString();
        statement = MainController.connection.createStatement();
        resultSet= statement.executeQuery("select id from subject where named='"+subjname+"'");
        while (resultSet.next()) {
            subjid = resultSet.getInt("id");
        }
    }
}

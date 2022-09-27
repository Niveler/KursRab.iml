package com.example.kursrab.controllers;


import com.example.kursrab.Main;
import com.example.kursrab.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeacherController {
    static Statement statement;
    static ResultSet resultSet;
    public Label lblLog;
    int id ;
    String surname = "";
    String name = "";
    String midname = "";
    String linkImage = "";
    byte[] link;
    String education = "";
    private Stage tableTeachersStage;
    public void setStageTeachers(Stage stageTeachers) {this.tableTeachersStage=stageTeachers;}
    public TableColumn<Teacher, String> surnameColumn;
    public TableColumn<Teacher, String> nameColumn;
    public  TableColumn<Teacher, String> midnameColumn;
    public  TableColumn<Teacher, String> educationColumn;
    public  ImageView imageView;
    public TableView<Teacher> TeachersTable;
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();

    private void selectAllTeachers() {
        try {
            statement = MainController.connection.createStatement();
            resultSet = statement.executeQuery("select * from teachers");
            while (resultSet.next()) {
                id = resultSet.getByte("id");
                surname = resultSet.getString("surname");
                name = resultSet.getString("name");
                midname = resultSet.getString("midname");
                linkImage = resultSet.getString("photo");
                education = resultSet.getString("education");
                teachers.add(new Teacher(id,surname,name,midname,linkImage,education));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось получить данные из БД");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.exit(0);
        }
    }
    //Метод инициализации колонок таблицы и данных
    @FXML
    void initialize() {
        selectAllTeachers();
        surnameColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().surnameProperty());
        nameColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().nameProperty());
        midnameColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().midnameProperty());
        educationColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().educationProperty());
        TeachersTable.setItems(teachers);
        TeachersTable.getSelectionModel().selectedItemProperty().addListener((observableValue, student, t1) -> {
                    if (t1 != null) {
                        File file = new File(t1.getPhoto());
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                    }
                }
        );
    }
    public void onAdd(ActionEvent actionEvent) throws IOException {
        Teacher teacher = new Teacher();
        showDialog(teacher);
        if(teacher.getSurname()!="" && teacher.getPhoto()!="") {
            surname = teacher.getSurname();
            name = teacher.getName();
            midname = teacher.getMidname();
            linkImage = teacher.getPhoto();
            education = teacher.getEducation();
            teachers.add(teacher);
            try {
                statement.executeUpdate("INSERT teachers(surname,name,midname,photo,education) " +
                        "VALUES ('" + surname + "','" + name + "','" + midname + "','" + linkImage + "','" + education + "')");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setContentText("Новый преподаватель: " + surname + " " + name + " " + midname + " добавлен в БД");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Не удалось добавить данные в БД");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void onEdit(ActionEvent actionEvent) throws IOException {
        Teacher teacher = TeachersTable.getSelectionModel().getSelectedItem();
        if(teacher!=null) {
            showDialog(teacher);
        }
        id = teacher.getId();
        surname = teacher.getSurname();
        name = teacher.getName();
        midname = teacher.getMidname();
        linkImage = teacher.getPhoto();
        education = teacher.getEducation();
        try {
            statement.executeUpdate("UPDATE teachers SET surname='"+ surname + "',name='" + name + "',midname='" + midname +
                    "',photo='" + linkImage + "',education='" + education + "' WHERE teachers.id=" + id);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setContentText("Пользователь "+surname+" "+name+" "+midname+" обновлен в БД");
                alert.show();
            } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось обновить текущюю запись");
            alert.setHeaderText(String.valueOf(ex));
            alert.showAndWait();
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        Teacher teacher = TeachersTable.getSelectionModel().getSelectedItem();
        int selectIndex = TeachersTable.getSelectionModel().getSelectedIndex();
        id = teacher.getId();
        TeachersTable.getItems().remove(teacher);
        try {
            statement.executeUpdate("DELETE FROM teachers WHERE teacher.id=" + id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Пользователь "+surname+" "+name+" "+midname+" удален из БД");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось удалить текущюю запись");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onExit(ActionEvent actionEvent) {
        tableTeachersStage.close();
    }
    void showDialog(Teacher teacher) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newTeacherDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        NewTeacherController newTeacherController = fxmlLoader.getController();
        newTeacherController.setTeacher(teacher);
        newTeacherController.setStage(stage);
        stage.showAndWait();
    }
}

package com.example.kursrab.controllers;

import com.example.kursrab.Main;
import com.example.kursrab.Prize;
import com.example.kursrab.TeachDiscip;
import com.example.kursrab.Teacher;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TeachDiscipController {
    static Statement statement;
    static ResultSet resultSet;
    int id ;
    String surname = "";
    String name = "";
    String midname = "";
    String subject = "";
    private Stage tableTeachDiscipStage;
    private int id_teach;
    private int id_sub;

    public void setStageTeachDiscip (Stage stageTeachDiscip) {this.tableTeachDiscipStage=stageTeachDiscip;}

    public TableColumn<TeachDiscip, String> surnameColumn;
    public TableColumn<TeachDiscip, String> nameColumn;
    public TableColumn<TeachDiscip, String> midnameColumn;
    public TableColumn<TeachDiscip, String> subjectColumn;
    public TableView<TeachDiscip> TeachDiscipTable;
    private ObservableList<TeachDiscip> teachDiscips = FXCollections.observableArrayList();
    String tmp = "SELECT td.id, t.id id_teach, s.id id_sub, t.name, t.surname, t.midname, s.named FROM teachdiscip td JOIN teachers t ON t.id = td.teacherid JOIN subject s ON s.id = td.subjectid";

    private void selectAllTeachDiscip() {
        TeachDiscipTable.getItems().clear();
        try {
            statement = MainController.connection.createStatement();
            resultSet = statement.executeQuery(tmp);
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                id_teach = resultSet.getInt("id_teach");
                id_sub = resultSet.getInt("id_sub");
                surname = resultSet.getString("surname");
                name = resultSet.getString("name");
                midname = resultSet.getString("midname");
                subject = resultSet.getString("named");
                teachDiscips.add(new TeachDiscip(id,id_teach,id_sub,surname,name,midname,subject));
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
    @FXML
    void initialize() {
        selectAllTeachDiscip();
        surnameColumn.setCellValueFactory(teachDiscipStringCellDataFeatures -> teachDiscipStringCellDataFeatures.getValue().teachersurnameProperty());
        nameColumn.setCellValueFactory(teachDiscipStringCellDataFeatures -> teachDiscipStringCellDataFeatures.getValue().teachernameProperty());
        midnameColumn.setCellValueFactory(teachDiscipStringCellDataFeatures -> teachDiscipStringCellDataFeatures.getValue().teachermidnameProperty());
        subjectColumn.setCellValueFactory(teachDiscipStringCellDataFeatures -> teachDiscipStringCellDataFeatures.getValue().subjectnameProperty());
        TeachDiscipTable.setItems(teachDiscips);
    }


    public void onAdd(ActionEvent actionEvent) throws IOException, SQLException {
        TeachDiscip teachDiscip = new TeachDiscip();
        showDialog(teachDiscip);
        id_teach = teachDiscip.getTeacherid();
        id_sub = teachDiscip.getSubjectid();
        teachDiscips.add(teachDiscip);
        try {
            statement.executeUpdate("INSERT teachdiscip(subjectid,teacherid) " +
                    "VALUES ('"+id_sub+"','" + id_teach+ "')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Запись добавлена");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось добавить данные в БД");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        selectAllTeachDiscip();
    }

    private void showDialog(TeachDiscip teachDiscip) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newTeachDiscipDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 155);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        NewTeachDiscipController newTeachDiscipController = fxmlLoader.getController();
        newTeachDiscipController.setTeachDiscip(teachDiscip);
        newTeachDiscipController.setStage(stage);
        newTeachDiscipController.selectTeachers();
        newTeachDiscipController.selectSubjects();
        stage.showAndWait();
    }

    public void onDelete(ActionEvent actionEvent) {
        TeachDiscip teachDiscip = TeachDiscipTable.getSelectionModel().getSelectedItem();
        id = teachDiscip.getId();
        TeachDiscipTable.getItems().remove(teachDiscip);
        try {
            statement.executeUpdate("DELETE FROM teachdiscip WHERE id ='" + id + "'");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Запись удалена из БД");
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
        tableTeachDiscipStage.close();
    }

}

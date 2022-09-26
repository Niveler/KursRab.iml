package com.example.kursrab.controllers;

import com.example.kursrab.Main;
import com.example.kursrab.Subject;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SubjectController {
    static Statement statement;
    static ResultSet resultSet;
    public Label lblLog;
    int id;
    String named = "";
    int hours;
    private Stage tableSubjectStage;
    private String tmp;

    public void setSubjectStage(Stage stage) {this.tableSubjectStage = stage;}
    public TableColumn<Subject, String> namedColumn;
    public TableColumn<Subject, Integer> hoursColumn;
    public TableView<Subject> SubjectsTable;
    private ObservableList<Subject> subjects = FXCollections.observableArrayList();
    private  void selectAllSubjects() {
        try {
            statement = MainController.connection.createStatement();
            resultSet = statement.executeQuery("select * from subject");
            while (resultSet.next()) {
                id = resultSet.getByte("id");
                named = resultSet.getString("named");
                hours = resultSet.getByte("hours");
                subjects.add(new Subject(id,named,hours));
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
        selectAllSubjects();
        namedColumn.setCellValueFactory(subjectStringCellDataFeatures -> subjectStringCellDataFeatures.getValue().namedProperty());
        hoursColumn.setCellValueFactory(cellData -> cellData.getValue().hoursProperty().asObject());
        SubjectsTable.setItems(subjects);
    }
    public void onAdd(ActionEvent actionEvent) throws IOException{
        Subject subject = new Subject();
        showDialog(subject);
        named = subject.getNamed();
        hours = subject.getHours();
        subjects.add(subject);
        try {
            statement.executeUpdate("INSERT subject(named,hours) " +
                    "VALUES ('"+named+"','" + hours+ "')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Новый предмет: "+named+" добавлен в БД");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось добавить данные в БД");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onEdit(ActionEvent actionEvent) throws IOException{
        Subject subject = SubjectsTable.getSelectionModel().getSelectedItem();
        if (subject!=null)
            showDialog(subject);
        named = subject.getNamed();
        hours = subject.getHours();
        tmp ="UPDATE subject SET named='" + named+ "', hours='"+hours+"' WHERE subject.id='" + id+"'";
        try {
            statement.executeUpdate(tmp);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось обновить текущюю запись");
            alert.setContentText(tmp);
            alert.showAndWait();
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        Subject subject = SubjectsTable.getSelectionModel().getSelectedItem();
        id = subject.getId();
        SubjectsTable.getItems().remove(subject);
        try {
            statement.executeUpdate("DELETE FROM subject WHERE subject.id =" + id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Предмет" + named+ "удален из БД");
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
        tableSubjectStage.close();
    }
    void showDialog(Subject subject) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newSubjectDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        NewSubjectController newSubjectController = fxmlLoader.getController();
        newSubjectController.setSubject(subject);
        newSubjectController.setStage(stage);
        stage.showAndWait();
    }
}

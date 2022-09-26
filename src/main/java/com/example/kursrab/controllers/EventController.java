package com.example.kursrab.controllers;

import com.example.kursrab.Event;
import com.example.kursrab.Main;
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

public class EventController {
    static Statement statement;
    static ResultSet resultSet;

    int id;
    String called;
    String date;
    String place;
    String organizer;
    int studentid;
    int prizeid;
    String surname="";
    String name="";
    String midname = "";
    String prizename = "";
    private ObservableList<Event> events = FXCollections.observableArrayList();
    private Stage tableEventStage;
    public TableView EventTable;
    public TableColumn<Event, String> calledColumn;
    public TableColumn<Event, String> dateColumn;
    public TableColumn<Event, String> organizerColumn;
    public TableColumn<Event, String> placeColumn;
    public TableColumn<Event, String> surnameColumn;
    public TableColumn<Event, String> nameColumn;
    public TableColumn<Event, String> midnameColumn;
    public TableColumn <Event, String>prizeColumn;
    public void setStageEvent(Stage stageEvent) {this.tableEventStage=stageEvent;}

    private void selectAllEvent() {
        EventTable.getItems().clear();
        try {
            statement = MainController.connection.createStatement();
            resultSet = statement.executeQuery("SELECT e.id,e.called, e.date, e.organizer , e.place, e.prizeid, e.studentid, st.surname, st.name,st.midname,pr.type FROM event e JOIN student st ON e.studentid = st.id JOIN prize pr ON pr.id = e.prizeid");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                called = resultSet.getString("called");
                date = resultSet.getString("date");
                organizer = resultSet.getString("organizer");
                place = resultSet.getString("place");
                studentid = resultSet.getInt("studentid");
                prizeid = resultSet.getInt("prizeid");
                surname = resultSet.getString("surname");
                name = resultSet.getString("name");
                midname = resultSet.getString("midname");
                prizename =resultSet.getString("type");
                events.add((new Event(id,prizeid,studentid, called,date,organizer,place,prizename,surname,name,midname)));
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
        selectAllEvent();
        calledColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().calledProperty());
        dateColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().dateProperty());
        organizerColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().organizeProperty());
        placeColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().placeProperty());
        surnameColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().studsurnameProperty());
        nameColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().studnameProperty());
        midnameColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().studmidnameProperty());
        prizeColumn.setCellValueFactory(eventStringCellDataFeatures -> eventStringCellDataFeatures.getValue().prizetypeProperty());
        EventTable.setItems(events);
    }

    public void onAdd(ActionEvent actionEvent) throws IOException, SQLException{
        Event event = new Event();
        showDialog(event);
        called=event.getCalled();
        date=event.getDate();
        organizer=event.getOrganize();
        place=event.getPlace();
        prizeid=event.getPrizeid();
        studentid=event.getStudentid();
        statement.executeUpdate("INSERT event(called,date,organizer,place,prizeid,studentid) VALUES" +
                "('"+called+"','"+date+"','"+organizer+"','"+place+"','"+prizeid+"','"+studentid+"')");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setContentText("Запись добавлена");
        alert.show();
        selectAllEvent();
    }
    void showDialog(Event event) throws IOException, SQLException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newEventDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 680, 375);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Добавить новое мероприятие");
        stage.setScene(scene);
        NewEventController newEventController = fxmlLoader.getController();
        newEventController.setEvent(event);
        newEventController.setStage(stage);
        newEventController.selectStudents();
        newEventController.selectPrizeType();
        stage.showAndWait();
    }

    public void onDelete(ActionEvent actionEvent) {
        Event event = (Event) EventTable.getSelectionModel().getSelectedItem();
        id = event.getId();
        EventTable.getItems().remove(event);
        try {
            statement.executeUpdate("DELETE FROM event WHERE event.id =" + id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Выбранное мероприятие удалено из БД");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось удалить текущюю запись");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}

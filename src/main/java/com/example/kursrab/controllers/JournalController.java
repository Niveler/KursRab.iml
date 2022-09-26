package com.example.kursrab.controllers;

import com.example.kursrab.Journal;
import com.example.kursrab.Main;
import com.example.kursrab.TeachDiscip;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

public class JournalController {
    static Statement statement;
    static ResultSet resultSet;
    public TableView<Journal> JournalTable;
    public TableColumn<Journal, String> surnameColumn;
    public TableColumn<Journal, String> nameColumn;
    public TableColumn<Journal, String> midnameColumn;
    public TableColumn<Journal, String> subjectColumn;
    public TableColumn<Journal, String> dateColumn;
    public TableColumn<Journal, Integer> scoreColumn;
    public DatePicker dpStart;
    public DatePicker dpEnd;
    public TextField tfSurname;
    public Button butUpdate;

    int id;
    String date;
    int score;
    int studentid;
    int subjectid;
    String surname="";
    String name="";
    String midname = "";
    String subjectname = "";
    private ObservableList<Journal> journals = FXCollections.observableArrayList();
    private Stage tableJournalStage;
    public void setStageJournal(Stage stageJournal) {this.tableJournalStage=stageJournal;}
    private void selectAllJournal() {
        JournalTable.getItems().clear();
        try {
            statement = MainController.connection.createStatement();
            resultSet = statement.executeQuery("SELECT j.id, j.date, j.score , j.studentid, j.subjectid, st.surname, st.name,st.midname,su.named FROM journal j JOIN student st ON j.studentid = st.id JOIN subject su ON su.id = j.subjectid");
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                studentid = resultSet.getInt("studentid");
                subjectid = resultSet.getInt("subjectid");
                date = resultSet.getString("date");
                score = resultSet.getInt("score");
                surname = resultSet.getString("surname");
                name = resultSet.getString("name");
                midname = resultSet.getString("midname");
                subjectname = resultSet.getString("named");
                journals.add(new Journal(id,date,score,studentid,subjectid,surname,name,midname,subjectname));
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
        selectAllJournal();
        butUpdate.setVisible(false);
        surnameColumn.setCellValueFactory(journalStringCellDataFeatures -> journalStringCellDataFeatures.getValue().studsurnameProperty());
        nameColumn.setCellValueFactory(journalStringCellDataFeatures -> journalStringCellDataFeatures.getValue().studnameProperty());
        midnameColumn.setCellValueFactory(journalStringCellDataFeatures -> journalStringCellDataFeatures.getValue().studmidnameProperty());
        subjectColumn.setCellValueFactory(journalStringCellDataFeatures -> journalStringCellDataFeatures.getValue().subjectnameProperty());
        dateColumn.setCellValueFactory(journalStringCellDataFeatures -> journalStringCellDataFeatures.getValue().getDate());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        JournalTable.setItems(journals);
    }
    public void onAdd(ActionEvent actionEvent) throws IOException, SQLException {
        LocalDate curdate = LocalDate.now();//Текущая дата
        Journal journal = new Journal();
        showDialog(journal);
        studentid = journal.getStudentid();
        score = journal.getScore();
        subjectid = journal.getSubjectid();
        journals.add(journal);
        date = curdate.toString();
        statement.executeUpdate("INSERT journal(date,score,studentid,subjectid) VALUES ('"+date+"','"+score+"','"+studentid+"','"+subjectid+"')");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация");
        alert.setContentText("Запись добавлена");
        alert.show();
        selectAllJournal();
    }

    public void onExit(ActionEvent actionEvent) {
        tableJournalStage.close();
    }

    void  showDialog(Journal journal) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newJournalDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 190);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        NewJournalController newJournalController = fxmlLoader.getController();
        newJournalController.setJournal(journal);
        newJournalController.setStage(stage);
        newJournalController.selectStudents();
        newJournalController.selectSubjects();
        newJournalController.selectScores();
        stage.showAndWait();
    }

    public void onPeriodTime(ActionEvent actionEvent) throws SQLException {
        JournalTable.getItems().clear();//Очищаем таблицу
        ObservableList<Journal> jselect = FXCollections.observableArrayList();
        String data1 = dpStart.getValue().toString();//Дата начала периода
        String data2 = dpEnd.getValue().toString();//Дата окончания периода
        surname = tfSurname.getText();
        statement = MainController.connection.createStatement();
        resultSet = statement.executeQuery("SELECT j.id, j.date, j.score , j.studentid, j.subjectid, " +
                "st.surname, st.name,st.midname,su.named FROM journal j JOIN student st ON j.studentid = st.id " +
                "JOIN subject su ON su.id = j.subjectid WHERE st.surname='"+surname+"' and j.date BETWEEN '"+data1+"' AND '"+data2+"'");
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            studentid = resultSet.getInt("studentid");
            subjectid = resultSet.getInt("subjectid");
            date = resultSet.getString("date");
            score = resultSet.getInt("score");
            surname = resultSet.getString("surname");
            name = resultSet.getString("name");
            midname = resultSet.getString("midname");
            subjectname = resultSet.getString("named");
            jselect.add(new Journal(id,date,score,studentid,subjectid,surname,name,midname,subjectname));
        }
        JournalTable.setItems(jselect);
        butUpdate.setVisible(true);
    }

    public void onUpdateTable(ActionEvent actionEvent) {
        JournalTable.getItems().clear();
        tfSurname.clear();
        dpStart.getEditor().clear();
        dpEnd.getEditor().clear();
        initialize();
    }
}

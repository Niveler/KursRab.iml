package com.example.kursrab.controllers;

import com.example.kursrab.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class MainController {
    final static String DB_URL = "jdbc:mysql://localhost:3306/school";
    final static String LOGIN = "root";
    final static String PASS = "root";
    public static Connection connection;
    public static Statement statement;
    static ResultSet resultSet;
    public TableView<Tabmain> tableMain;
    public TableColumn<Tabmain, Integer> ratingColumn;
    public TableColumn<Tabmain, Integer>  scorEventColumn;
    public TableColumn<Tabmain, Integer>  scorLearnColumn;
    public TableColumn<Tabmain, String>  surnameColumn;
    public TableColumn<Tabmain, String> nameColumn;
    public TableColumn<Tabmain, String> midnameColumn;
    public Circle circle;
    int rating;
    int sumScore;
    int sumMark;
    String surname;
    String name;
    String midname;
    ObservableList<Tabmain> mainlist = FXCollections.observableArrayList();



    //метод подключения к БД
    public void connectBD () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось загрузить драйвер MySQL");
            alert.setContentText(e.getMessage() + "\n Приложение будет закрыто");
            alert.showAndWait();
            System.exit(0);
        }
        try {
            connection = DriverManager.getConnection(DB_URL,LOGIN,PASS);
            if(connection.isClosed()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("соединения нет. Connection.isClosed");
                alert.showAndWait();
            } else {
                circle.setFill(Color.GREEN);
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось подключиться к базе данных");
            alert.setContentText(e.getMessage() + "\n Приложение будет закрыто");
            alert.showAndWait();
            System.exit(0);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException var1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка создания statement");
            alert.setContentText(var1.getMessage() + "\n Приложение будет закрыто");
            alert.showAndWait();
            System.exit(0);
        }
    }
    //Загружка формы с таблицей студентов
    @FXML
    private void onLoadStudents(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Student.class.getResource("students-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900,500);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        StudentController studentController = fxmlLoader.getController();
        studentController.setStage2(stage);
        stage.showAndWait();
    }
    @FXML
    public void inicialize() {
        try {
            statement=connection.createStatement();
            resultSet= statement.executeQuery("select (ROW_NUMBER() OVER (ORDER BY tbl.SumRating desc)) as rating, tbl.SumScore, tbl.SumMark, tbl.Name, tbl.MidName, tbl.Surname from ( select (IFNULL(sum(pr.score), 0) + IFNULL(sum(jr.score), 0) ) as SumRating, sum(pr.score) as SumScore, sum(jr.score) as SumMark, st.name, st.midname, st.surname from student st left join Journal jr on  st.Id =  jr.studentId left join Event ev on st.Id = ev.studentId left join Prize pr on ev.prizeid = pr.Id group by st.name, st.midname, st.surname) tbl ORDER BY `rating` ASC");
            while (resultSet.next()) {
                rating= resultSet.getInt("rating");
                sumScore=resultSet.getInt("SumScore");
                sumMark=resultSet.getInt("SumMark");
                surname=resultSet.getString("Surname");
                name=resultSet.getString("Name");
                midname=resultSet.getString("MidName");
                mainlist.addAll(new Tabmain(rating,sumScore,sumMark,surname,name,midname));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось получить данные из БД");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.exit(0);
        }
        ratingColumn.setCellValueFactory(rating->rating.getValue().ratingProperty().asObject());
        scorEventColumn.setCellValueFactory(score->score.getValue().sumScoreProperty().asObject());
        scorLearnColumn.setCellValueFactory(mark->mark.getValue().sumMarkProperty().asObject());
        surnameColumn.setCellValueFactory(surname->surname.getValue().surnameProperty());
        nameColumn.setCellValueFactory(name->name.getValue().nameProperty());
        midnameColumn.setCellValueFactory(midname->midname.getValue().midnameProperty());
        tableMain.setItems(mainlist);
    }
    //Метод подключения к БД из меню
    public void connectionToDb(ActionEvent actionEvent) {
        connectBD();
    }
    //Выход из приложения
    public void onAppExit(ActionEvent actionEvent) {
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось закрыть соединение с БД");
            alert.showAndWait();
            System.exit(0);
        }
        Platform.exit();
    }

    public void onLoadTeachers(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Teacher.class.getResource("teachers-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900,500);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о преподователях");
        stage.setScene(scene);
        TeacherController teacherController = fxmlLoader.getController();
        teacherController.setStageTeachers(stage);
        stage.showAndWait();
    }

    public void onLoadSubject(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Subject.class.getResource("subject-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),470,400);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о предметах");
        stage.setScene(scene);
        SubjectController subjectController = fxmlLoader.getController();
        subjectController.setSubjectStage(stage);
        stage.showAndWait();
    }

    public void onLoadPrize(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Subject.class.getResource("prize-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),470,400);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о предметах");
        stage.setScene(scene);
        PrizeController prizeController = fxmlLoader.getController();
        prizeController.setStage2(stage);
        stage.showAndWait();
    }

    public void onLoadTeachDiscip(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Subject.class.getResource("teachdiscip-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900,500);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о предметах и преподователях");
        stage.setScene(scene);
        TeachDiscipController teachDiscipController = fxmlLoader.getController();
        teachDiscipController.setStageTeachDiscip(stage);
        stage.showAndWait();
    }

    public void onLoadJournal(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Journal.class.getResource("journal-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),900,500);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация об оценках");
        stage.setScene(scene);
        JournalController journalController = fxmlLoader.getController();
        journalController.setStageJournal(stage);
        stage.showAndWait();
    }

    public void onLoadEvents(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(Event.class.getResource("event-view.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),900,900);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            System.exit(0);
        }
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о мероприятиях");
        stage.setScene(scene);
        stage.setMaximized(true);
        EventController eventController = fxmlLoader.getController();
        eventController.setStageEvent(stage);
        stage.showAndWait();
    }
}

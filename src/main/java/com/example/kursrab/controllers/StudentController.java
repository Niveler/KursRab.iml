package com.example.kursrab.controllers;

import com.example.kursrab.Main;
import com.example.kursrab.Student;
import com.example.kursrab.controllers.MainController;
import com.example.kursrab.controllers.NewStudentController;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentController {
    static Statement statement;
    static ResultSet resultSet;
    public Label lbText;
    String tmp = "";
    int id ;
    String surname = "";
    String name = "";
    String midname = "";
    String numclass = "";
    int yearadmiss ;
    private Stage tableStudentStage;

    public void setStage2(Stage stage2) {
        this.tableStudentStage = stage2;
    }
    public TableColumn<Student, String> surnameColumn;
    public TableColumn<Student, String> nameColumn;
    public  TableColumn<Student, String> midnameColumn;
    public  TableColumn<Student,String> numclassColumn;
    public  TableColumn<Student,Integer> yearadmissColumn;
    public TableView<Student> StudentsTable;
    private ObservableList<Student> students = FXCollections.observableArrayList();
    //Загрузка таблицы из БД
    private void selectAllSQL() {
        try {
            statement = MainController.connection.createStatement();
            resultSet =statement.executeQuery("select * from student");
            while (resultSet.next()) {
                id = resultSet.getByte("id");
                surname = resultSet.getString("surname");
                name = resultSet.getString("name");
                midname = resultSet.getString("midname");
                numclass = resultSet.getString("numclass");
                yearadmiss = resultSet.getInt("yearadmiss");
                students.add(new Student(id,surname,name,midname,numclass,yearadmiss));
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
        selectAllSQL();
        surnameColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().surnameProperty());
        nameColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().nameProperty());
        midnameColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().midnameProperty());
        numclassColumn.setCellValueFactory(studentStringCellDataFeatures -> studentStringCellDataFeatures.getValue().numclassProperty());
        yearadmissColumn.setCellValueFactory(cellData -> cellData.getValue().ageProperty().asObject());
        StudentsTable.setItems(students);
        StudentsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, student, t1) -> {
            if (t1 != null) {
                //showStudent(t1);
            }
        }
        );
    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        Student student = new Student();
        showDialog(student);
        if(student.getSurname()!="" && student.getName() !="" ) {
            surname = student.getSurname();
            name = student.getName();
            midname = student.getMidname();
            numclass = student.getNumclass();
            yearadmiss = student.getYearadmiss();
            students.add(student);
            tmp = "INSERT student(surname,name,midname,numclass,yearadmiss) " +
                    "VALUES ('"+surname+"','" + name+ "','" + midname +"','" + numclass +"','" + yearadmiss +"')";
            lbText.setText(tmp);
            try {
                statement.executeUpdate(tmp);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setContentText("Новый ученик: "+surname+" "+name+" "+midname+" добавлен в БД");
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
        Student student = StudentsTable.getSelectionModel().getSelectedItem();
        if(student!=null)
            showDialog(student);
        id = student.getId();
        surname = student.getSurname();
        name = student.getName();
        midname = student.getMidname();
        numclass = student.getNumclass();
        yearadmiss = student.getYearadmiss();
        try {

            if(statement.executeUpdate("UPDATE student SET surname='"+ surname + "',name='" + name + "',midname='" + midname +
                     "',numclass='" + numclass + "',yearadmiss='" + yearadmiss + "' WHERE student.id=" + id)==0) {
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Информация");
                alert.setContentText("Пользователь "+surname+" "+name+" "+midname+" обновлен в БД");
                alert.show();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось обновить текущюю запись");
            alert.showAndWait();
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        Student student = StudentsTable.getSelectionModel().getSelectedItem();
        int selectIndex = StudentsTable.getSelectionModel().getSelectedIndex();
        id = student.getId();
        StudentsTable.getItems().remove(student);
        try {
            statement.executeUpdate("DELETE FROM student WHERE student.id=" + id);
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
        tableStudentStage.close();

    }
    void showDialog(Student student) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("NewStudentDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        NewStudentController newStudentController = fxmlLoader.getController();
        newStudentController.setStudent(student);
        newStudentController.setStage(stage);
        stage.showAndWait();
    }

    public void onChoise(MouseEvent mouseEvent) {
        //Здесть функционал выбора одного студента с его оценками и заслугами
        //Возможно лучше вынести этот фук-нал, т.к. в т.з. нужно в поле вписать фамилию и произвести выбор.(Думаю так лучше будет)
    }
}

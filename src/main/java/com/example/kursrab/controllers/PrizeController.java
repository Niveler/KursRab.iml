package com.example.kursrab.controllers;

import com.example.kursrab.Main;
import com.example.kursrab.Prize;
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

public class PrizeController {
    static Statement statement;
    static ResultSet resultSet;
    public TableColumn<Prize,Integer> scoreColumn;
    public TableColumn<Prize, String> typeColumn;
    public Label lblLog;
    public TableView PrizesTable;
    private ObservableList<Prize> prizes = FXCollections.observableArrayList();
    int id ;
    int score;
    String type = "";

    private Stage tablePrizeStage;
    public void setStage2(Stage stage) {this.tablePrizeStage= stage;}
    private void selectAllPrizes() {
        try {
            statement = MainController.connection.createStatement();
            resultSet = statement.executeQuery("select * from prize");
            while (resultSet.next()) {
                id = resultSet.getByte("id");
                score = resultSet.getByte("score");
                type = resultSet.getString("type");
                prizes.add(new Prize(id,score,type));
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
        selectAllPrizes();
        scoreColumn.setCellValueFactory(cellData->cellData.getValue().scoreProperty().asObject());
        typeColumn.setCellValueFactory(prizeStringCellDataFeatures -> prizeStringCellDataFeatures.getValue().typeProperty());
        PrizesTable.setItems(prizes);
    }
    public void onAdd(ActionEvent actionEvent) throws IOException {
        Prize prize = new Prize();
        showDialog(prize);
        score = prize.getScore();
        type = prize.getType();
        prizes.add(prize);
        try {
            statement.executeUpdate("INSERT prize(score,type) VALUES ('" + score+"','"+type+ "')");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Новый тип награды: "+type+" добавлен в БД");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось добавить данные в БД");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private void showDialog(Prize prize) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("newPrizeDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 350);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.getStage());
        stage.setTitle("Информация о студенте");
        stage.setScene(scene);
        NewPrizeController newPrizeController = fxmlLoader.getController();
        newPrizeController.setPrize(prize);
        newPrizeController.setStage(stage);
        stage.showAndWait();
    }

    public void onEdit(ActionEvent actionEvent) throws IOException{
        Prize prize = (Prize) PrizesTable.getSelectionModel().getSelectedItem();
        if(prize!=null)
            showDialog(prize);
        score = prize.getScore();
        type = prize.getType();
        try{
            statement.executeUpdate("UPDATE prize SET score='"+score+ "', type ='" + type + "' WHERE prize.id='"+id+"'");
        }catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось обновить текущюю запись");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        Prize prize = (Prize) PrizesTable.getSelectionModel().getSelectedItem();
        id = prize.getId();
        PrizesTable.getItems().remove(prize);
        try {
            statement.executeUpdate("DELETE FROM subject WHERE subject.id =" + id);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            alert.setContentText("Данный тип награды " + type+ " удален из БД");
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
        tablePrizeStage.close();
    }
}

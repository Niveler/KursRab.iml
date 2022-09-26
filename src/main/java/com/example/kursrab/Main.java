package com.example.kursrab;

import com.example.kursrab.controllers.JournalController;
import com.example.kursrab.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main  extends Application {
    private static Stage studStage;
    public static Stage getStage() {
        return studStage;
    }
    @Override
    public void start(Stage stage) throws Exception {
        studStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1280,800);
        scene.getStylesheets().add(Main.class.getResource("bootstrap3.css").toExternalForm());
        stage.setTitle("Журнал успеваимости учеников");
        stage.setScene(scene);
        MainController mainController = fxmlLoader.getController();
        mainController.connectBD();
        mainController.inicialize();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

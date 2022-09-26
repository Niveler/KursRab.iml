package com.example.kursrab.controllers;

import com.example.kursrab.Prize;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewPrizeController {
    public TextField tfScore;
    public TextField tfType;
    private Prize prize;
    private Stage stageNewPrize;
    public void setStage(Stage stage) {
        this.stageNewPrize = stage;
    }

    public void onOk(ActionEvent actionEvent) {
        prize.setScore(Integer.parseInt(tfScore.getText()));
        prize.setType(tfType.getText());
        stageNewPrize.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        stageNewPrize.close();
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
        tfScore.setText(String.valueOf(prize.getScore()));
        tfType.setText(prize.getType());
    }
}

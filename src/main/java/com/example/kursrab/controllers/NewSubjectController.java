package com.example.kursrab.controllers;

import com.example.kursrab.Subject;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewSubjectController {

    public TextField tfNamed;
    public TextField tfHours;
    private Subject subject;
    private Stage stageNewSubject;

    public void setSubject(Subject subject) {
        this.subject = subject;
        tfNamed.setText(subject.getNamed());
        tfHours.setText(String.valueOf(subject.getHours()));
    }

    public void setStage(Stage stage) {
        this.stageNewSubject = stage;
    }

    public void onOk(ActionEvent actionEvent) {
        subject.setNamed(tfNamed.getText());
        subject.setHours(Integer.parseInt(tfHours.getText()));
        stageNewSubject.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        stageNewSubject.close();
    }
}

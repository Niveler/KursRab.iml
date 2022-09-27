package com.example.kursrab.controllers;

import com.example.kursrab.Teacher;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NewTeacherController {
    public TextField tfSurname;
    public TextField tfName;
    public TextField tfMidName;
    public TextField tfEducation;
    public TextField tfLink;
    private Teacher teacher;
    final FileChooser fileChooser = new FileChooser();
    private Stage stageNewTeacher;
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        tfSurname.setText(teacher.getSurname());
        tfName.setText(teacher.getName());
        tfMidName.setText(teacher.getMidname());
        tfEducation.setText(teacher.getEducation());
        tfLink.setText(teacher.getPhoto());
    }
    public void onOk(ActionEvent actionEvent) {
        teacher.setSurname(tfSurname.getText());
        teacher.setName(tfName.getText());
        teacher.setMidname(tfMidName.getText());
        teacher.setEducation(tfEducation.getText());
        teacher.setPhoto(tfLink.getText());
        stageNewTeacher.close();
    }
    public void setStage(Stage stage) {
        this.stageNewTeacher = stage;
    }
    public void onOpenFile(ActionEvent actionEvent) {
        File file = fileChooser.showOpenDialog(stageNewTeacher);
        try {
            FileInputStream fin = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        tfLink.setText(file.getAbsolutePath());
    }

    public void onCancel(ActionEvent actionEvent) {
        stageNewTeacher.close();
    }
}

package com.example.kursrab.controllers;

import com.example.kursrab.Student;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class NewStudentController {
    public TextField tfSurname;
    public TextField tfName;
    public TextField tfMidName;
    public TextField tfNumclass;
    public TextField tfYearadmiss;
    private Student student;

    public void setStage(Stage stageNewStudent) {
        this.stageNewStudent = stageNewStudent;
    }

    private Stage stageNewStudent;

    public void setStudent(Student student) {
        this.student = student;
        tfSurname.setText(student.getSurname());
        tfName.setText(student.getName());
        tfMidName.setText(student.getMidname());
        tfNumclass.setText(student.getNumclass());
        tfYearadmiss.setText(String.valueOf(student.getYearadmiss()));
    }

    public void onOk(ActionEvent actionEvent) {
        student.setSurname(tfSurname.getText());
        student.setName(tfName.getText());
        student.setMidname(tfMidName.getText());
        student.setNumclass(tfNumclass.getText());
        student.setYearadmiss(Integer.parseInt(tfYearadmiss.getText()));
        stageNewStudent.close();
    }

    public void onCancel(ActionEvent actionEvent) {
        stageNewStudent.close();
    }


}

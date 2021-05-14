package main.controller.WorkSchedule;

import java.io.PrintStream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class WeekdaysController {


    @FXML
    public CheckBox checkMON;
    @FXML
    public CheckBox checkTUE;
    @FXML
    public CheckBox checkWED;
    @FXML
    public CheckBox checkTHU;
    @FXML
    public CheckBox checkFRI;


    static boolean monday = false;
    static boolean tuesday = false;
    static boolean wednesday = false;
    static boolean thursday = false;
    static boolean friday = false;
    static int count = 0;


    @FXML
    public void selectedDays(ActionEvent event) {
        count = 0;
        if (checkMON.isSelected()) {
            monday = true;
            count++;
        } else {
            monday = false;
        }
        if (checkTUE.isSelected()) {
            tuesday = true;
            count++;
        } else {
            tuesday = false;
        }
        if (checkWED.isSelected()) {
            wednesday = true;
            count++;
        } else {
            wednesday = false;
        }
        if (checkTHU.isSelected()) {
            thursday = true;
            count++;
        } else {
            thursday = false;
        }
        if (checkFRI.isSelected()) {
            friday = true;
            count++;
        } else {
            friday = false;
        }
    }

}



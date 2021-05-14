package main.controller.WorkSchedule;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class WeekendController {


    @FXML
    public  CheckBox checkSAT;
    @FXML
    public  CheckBox checkSUN;
     static int count;
     static boolean saturday = false;
     static boolean sunday = false;

    @FXML
    void selectedDays(ActionEvent event) {
        count = 0;
        if (checkSAT.isSelected()) {
            count++;
            saturday=true;
        }
        if (checkSUN.isSelected()) {
            count++;
            sunday=true;
        }
    }



}
package main.controller.Location;

import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import main.model.Building;
import main.model.PrefReserved;
import main.model.Room;
import main.service.BuildingService;
import main.service.PrefReservedService;
import main.service.PrefTagService;
import main.service.RoomService;
import main.service.impl.BuildingServiceImpl;
import main.service.impl.PrefReservedServiceImpl;
import main.service.impl.PrefTagServiceImpl;
import main.service.impl.RoomServiceImpl;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddPrefReservedController implements Initializable {

    @FXML
    private TextField txtBuildingOpt1;

    @FXML
    private TextField txtRoomOpt1;

    @FXML
    private ComboBox<String> cmbCenter;

    @FXML
    private ComboBox<String> cmbCenter1;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private Button btnReservedOptions;

    ArrayList<Building> buildingsId = new ArrayList<>();
    ArrayList<String> buildingName = new ArrayList<>();
    ArrayList<Room> roomsId = new ArrayList<>();
    ArrayList<String> roomName = new ArrayList<>();
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AutoCompletionBinding<String> autoCompletionBinding2;

    public static final Logger log = Logger.getLogger(AddPrefReservedController.class.getName());
    private PrefReservedService prefReservedService;
    private PrefTagService prefTagService;


    public AddPrefReservedController() {
        this.prefReservedService = new PrefReservedServiceImpl();
        this.prefTagService = new PrefTagServiceImpl();
    }

    @FXML
    void getBuilding(ActionEvent event) {
        String center = cmbCenter.getValue();

        try {
            BuildingService buildingService = new BuildingServiceImpl();


            ArrayList<Building> list = buildingService.searchBuildingDetailsByUsingCenter(center);
            buildingsId = new ArrayList<>();
            buildingName = new ArrayList<>();

            for (Building building : list
            ) {
                buildingsId.add(building);
                buildingName.add(building.getBuilding());
            }

            if(autoCompletionBinding!=null){
                autoCompletionBinding.dispose();
            }
            autoCompletionBinding  = TextFields.bindAutoCompletion(txtBuildingOpt1, buildingName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    @FXML
    void getRoom(ActionEvent event) {
        String building = txtBuildingOpt1.getText();

        try {
            RoomService roomService = new RoomServiceImpl();

            ArrayList<Room> list = roomService.searchRoomDetailsByUsingbuilding(building);
            roomsId = new ArrayList<>();
            roomName = new ArrayList<>();

            for (Room room : list
            ) {
                roomsId.add(room);
                roomName.add(room.getRoom());
            }

            if(autoCompletionBinding2!=null){
                autoCompletionBinding2.dispose();
            }
            autoCompletionBinding2  = TextFields.bindAutoCompletion(txtRoomOpt1, roomName);

        } catch (SQLException ex) {
            log.log(Level.SEVERE,ex.getMessage());
        }
    }

    @FXML
    void saveReservedRoom(ActionEvent event) throws SQLException {

        String center = (String) cmbCenter.getValue();
        String building = txtBuildingOpt1.getText();
        String room = txtRoomOpt1.getText();
        String day = (String) cmbCenter1.getValue();
        String getToTime = toTime.getValue().toString();
        String getFromTime = fromTime.getValue().toString();
        int roomId=0;


        if(building != null ){
            if(center != null) {
                if(room != null) {
                    roomId = prefTagService.getRoomId(center, building, room);

                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select room");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select center");
                al.setHeaderText(null);
                al.showAndWait();
            }

        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select building");
            al.setHeaderText(null);
            al.showAndWait();
        }



        PrefReserved prefRes = new PrefReserved();
        prefRes.setRoomId(roomId);
        prefRes.setDay(day);
        prefRes.setToTime(getToTime);
        prefRes.setFromTime(getFromTime);


        boolean isAdded = false;

        if(center != null){
            if(building != null){
                if(room != null){
                    if(day != null){
                        if(getToTime!= null){
                            if(getFromTime != null){
                        isAdded = this.prefReservedService.savePrefReservedRoom(prefRes);

                        if (isAdded) {
                            Alert al = new Alert(Alert.AlertType.INFORMATION);
                            al.setTitle(null);
                            al.setContentText("Added Successfully  !!");
                            al.setHeaderText(null);
                            al.showAndWait();

                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Added Failed  !!");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select From Time");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                        }else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Please Select To Time");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    }else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("Please Select Day");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Please Select Room");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            }else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Please Select Building");
                al.setHeaderText(null);
                al.showAndWait();
            }
        }else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Please Select Center");
            al.setHeaderText(null);
            al.showAndWait();
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String curTime;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        curTime = dtf.format(now);
        toTime.setValue(LocalTime.parse(curTime));
        fromTime.setValue(LocalTime.parse(curTime));
    }
}

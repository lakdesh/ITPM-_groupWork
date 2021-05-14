package main.controller.WorkSchedule;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.WorkingHoursPerDay;
import main.service.WorkingHoursService;
import main.service.impl.WorkingHoursImpl;

public class WorkingHoursController implements Initializable {

    final int initialValue = 0;
    SpinnerValueFactory svfH;
    SpinnerValueFactory svfM;
    @FXML
    private RadioButton checkbxOneHour;
    @FXML
    private RadioButton checkbxThirtyMin;
    @FXML
    private Button btnAdd;
    @FXML
    private TableView<WorkingHoursPerDay> tblWorkingDays;
    @FXML
    private TableColumn<WorkingHoursPerDay, Boolean> colEdit;
    @FXML
    private TableColumn<WorkingHoursPerDay, Boolean> colDelete;
    @FXML
    private Spinner spinnerHour;
    @FXML
    private Spinner spinnerMinute;
    private WorkingHoursService workingHoursService;
    private boolean updateStatus = false;
    private int perId=0;
    public static final Logger log = Logger.getLogger(WorkingHoursController.class.getName());

    public WorkingHoursController() {
        svfH = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0);
        svfM = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, 0);
        this.workingHoursService = new WorkingHoursImpl();
    }



    @FXML
    void addWorkingHours(ActionEvent event) {
        if (checkbxThirtyMin.isSelected() && ((Integer) spinnerHour.getValue()).intValue() < 1 && ((Integer) spinnerMinute.getValue()).intValue() < 30) {

                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Atleast 30min time is needed for Thirty minute time slot ");
                al.setHeaderText(null);
                al.showAndWait();


        } else if (checkbxOneHour.isSelected() && ((Integer) spinnerHour.getValue()).intValue() < 1) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Atleast 1 hour time is needed for One hour time slot");
            al.setHeaderText(null);
            al.showAndWait();

        } else {
            WorkingHoursPerDay workingHoursPerDay = new WorkingHoursPerDay();
            workingHoursPerDay.setWorkingTime(((Integer) spinnerHour.getValue()).intValue() + "." + ((Integer) spinnerMinute.getValue()).intValue());
            if(checkbxThirtyMin.isSelected()){
                workingHoursPerDay.setTimeSlot("Thirty minute");
            }else if(checkbxOneHour.isSelected()){
                workingHoursPerDay.setTimeSlot("One Hour");
            }
            try {
                if(!updateStatus){

                    boolean isAdded = this.workingHoursService.saveWorkingHours(workingHoursPerDay);
                    if (isAdded) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle(null);
                        al.setContentText(" Added Successfully ");
                        al.setHeaderText(null);
                        al.showAndWait();
                        this.getAllDetails();
                    } else {
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle(null);
                        al.setContentText(" Added Fail ");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }else{
                    workingHoursPerDay.setWhpId(perId);
                    boolean isUpdated = this.workingHoursService.updateWorkingHours(workingHoursPerDay);
                    if(isUpdated){
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle(null);
                        al.setContentText(" Updated Successfully ");
                        al.setHeaderText(null);
                        al.showAndWait();
                        updateStatus=false;
                        this.getAllDetails();
                    }else{
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle(null);
                        al.setContentText(" Updated Fail ");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }

            } catch (SQLException e) {
                log.log(Level.SEVERE,e.getMessage());
            }
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        spinnerHour.setValueFactory(svfH);
        spinnerMinute.setValueFactory(svfM);
        checkbxOneHour.setSelected(true);
        this.setTableProperties();
        this.getAllDetails();
    }

    public void setTableProperties() {
        tblWorkingDays.getSelectionModel().getTableView().getItems().clear();
        tblWorkingDays.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("whpId"));
        tblWorkingDays.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("workingTime"));
        tblWorkingDays.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("timeSlot"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<WorkingHoursPerDay, Boolean>, TableCell<WorkingHoursPerDay, Boolean>> cellFactoryBtnEdit =
            new Callback<TableColumn<WorkingHoursPerDay, Boolean>, TableCell<WorkingHoursPerDay, Boolean>>() {
                @Override
                public TableCell<WorkingHoursPerDay, Boolean> call(TableColumn<WorkingHoursPerDay, Boolean> param) {
                    final TableCell<WorkingHoursPerDay, Boolean> cell = new TableCell<WorkingHoursPerDay, Boolean>() {
                        FontAwesomeIconView iconView = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        final Button btnEdit = new Button();
                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnEdit.setOnAction(e -> {
                                    WorkingHoursPerDay whp = getTableView().getItems().get(getIndex());
                                    setWorkingHoursPerDayDetailsToFiled(whp);
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                        private void setWorkingHoursPerDayDetailsToFiled(WorkingHoursPerDay workingHoursPerDay) {
                            spinnerHour.getValueFactory().setValue(Integer.parseInt(workingHoursPerDay.getWorkingTime().substring(0, workingHoursPerDay.getWorkingTime().indexOf("."))));
                            spinnerMinute.getValueFactory().setValue(Integer.parseInt(workingHoursPerDay.getWorkingTime().substring(workingHoursPerDay.getWorkingTime().lastIndexOf(".") + 1)));
                            updateStatus = true;
                            perId = workingHoursPerDay.getWhpId();

                            if(workingHoursPerDay.getTimeSlot().equals("One Hour")){
                                checkbxOneHour.setSelected(true);
                            }else if(workingHoursPerDay.getTimeSlot().equals("Thirty minute")){
                                checkbxThirtyMin.setSelected(true);
                            }
                        }
                    };
                    return cell;
                }
            };

    Callback<TableColumn<WorkingHoursPerDay, Boolean>, TableCell<WorkingHoursPerDay, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<WorkingHoursPerDay, Boolean>, TableCell<WorkingHoursPerDay, Boolean>>() {
                @Override
                public TableCell<WorkingHoursPerDay, Boolean> call(TableColumn<WorkingHoursPerDay, Boolean> param) {
                    final TableCell<WorkingHoursPerDay, Boolean> cell = new TableCell<WorkingHoursPerDay, Boolean>() {
                        FontAwesomeIconView iconViewDelete = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        final Button btnDelete = new Button();

                        @Override
                        public void updateItem(Boolean check, boolean empty) {
                            super.updateItem(check, empty);
                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                btnDelete.setOnAction(e -> {
                                    WorkingHoursPerDay whp = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row ?");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteWorkingHoursPerDay(whp.getWhpId());
                                    }
                                });
                                btnDelete.setStyle("-fx-background-color: transparent;");
                                btnDelete.setGraphic(iconViewDelete);
                                setGraphic(btnDelete);
                                setAlignment(Pos.CENTER);
                                setText(null);

                            }
                        }
                    };
                    return cell;
                }
            };

    public void deleteWorkingHoursPerDay(int id){
        try {
            boolean isDeleted = this.workingHoursService.deleteWorkingHours(id);
            if(isDeleted){
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText(" Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                this.getAllDetails();
            }else{
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText(" Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
    public void getAllDetails() {
        try {
            ArrayList<WorkingHoursPerDay> list = this.workingHoursService.getAllWorkingHours();
            tblWorkingDays.setItems(FXCollections.observableArrayList(list));

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

}
package main.controller.WorkSchedule;

import com.gluonhq.charm.glisten.control.ToggleButtonGroup;

import java.io.IOException;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import main.model.WorkingDaysMain;
import main.model.WorkingDaysSub;
import main.service.WorkingDaysService;
import main.service.impl.WorkingDaysServiceImpl;

// Referenced classes of package main.controller.WorkSchedule:
//            WeekdaysController, WeekendController
public class WorkingDaysController implements Initializable {

    ObservableList WeekdayNumber;
    ObservableList WeekendNumber;
    @FXML
    private Button btnAdd;
    @FXML
    private ComboBox cmbNoDays;
    @FXML
    private TableView<WorkingDaysMain> tblWorkingDays;
    @FXML
    private TableColumn<WorkingDaysMain, Boolean> colEdit;
    @FXML
    private TableColumn<WorkingDaysMain, Boolean> colDelete;
    @FXML
    private BorderPane pnlWorkingDays;
    private Label pnlW;
    @FXML
    private RadioButton btnRadioWeekEnd;
    @FXML
    private RadioButton btnRadioWeekday;
    @FXML
    private ToggleButtonGroup togglebtnDays;
    private WorkingDaysService workingDaysService;
    private boolean updateStatus = false;
    private int updateId = 0;
    public static final Logger log = Logger.getLogger(WorkingDaysController.class.getName());
    private static final String MONDAY="Monday";
    private static final String TUESDAY="Tuesday";
    private static final String WEDNESDAY= "Wednesday";
    private static final String THURSDAY= "Thursday";
    private static final String FRIDAY= "Friday";
    private static final String SATURDAY= "Saturday";
    private static final String SUNDAY= "Sunday";
    private static final String WEEKEND="Weekends";
    private static final String WEEKDAY="Weekdays";
    public WorkingDaysController() {
        this.workingDaysService = new WorkingDaysServiceImpl();
    }

    @FXML
    void addDetails(ActionEvent event) {
        String noDays = (String) cmbNoDays.getValue();
        int countcheckboxWeekdays = Integer.valueOf(WeekdaysController.count).intValue();
        int countcheckboxWeekends = Integer.valueOf(WeekendController.count).intValue();
        if (!btnRadioWeekEnd.isSelected() && !btnRadioWeekday.isSelected()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Select Weekend or Weekday ");
            al.setHeaderText(null);
            al.showAndWait();
        } else if (noDays == null) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Select number of working days ");
            al.setHeaderText(null);
            al.showAndWait();
        } else if (!((String) cmbNoDays.getValue()).matches(String.valueOf(countcheckboxWeekdays)) && !((String) cmbNoDays.getValue()).matches(String.valueOf(countcheckboxWeekends))) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Number of working days doesn't match the days selected !");
            al.setHeaderText(null);
            al.showAndWait();
        } else {

            if (!updateStatus) {
                saveDetails();
            } else {
                updateDetails();
            }

        }
    }

    public void updateDetails() {
        try {
            boolean isDeleted = this.workingDaysService.deleteWorkingDaysfromSub(updateId);
            if (isDeleted) {
                ArrayList<String> updateArrayListDay = new ArrayList<>();
                String selectedType = "";
                int noOfDays = Integer.parseInt(cmbNoDays.getValue().toString());
                if (btnRadioWeekday.isSelected()) {
                    selectedType = WEEKDAY;;
                    if (WeekdaysController.monday) {
                        updateArrayListDay.add(MONDAY);
                    }
                    if (WeekdaysController.tuesday) {
                        updateArrayListDay.add(TUESDAY);
                    }
                    if (WeekdaysController.wednesday) {
                        updateArrayListDay.add(WEDNESDAY);
                    }
                    if (WeekdaysController.thursday) {
                        updateArrayListDay.add(THURSDAY);
                    }
                    if (WeekdaysController.friday) {
                        updateArrayListDay.add(FRIDAY);
                    }
                } else if (btnRadioWeekEnd.isSelected()) {
                    selectedType = WEEKEND;
                    if (WeekendController.saturday) {
                        updateArrayListDay.add(SATURDAY);
                    }
                    if (WeekendController.sunday) {
                        updateArrayListDay.add(SUNDAY);
                    }
                }
                WorkingDaysMain workingDaysMain = new WorkingDaysMain(updateId, selectedType, noOfDays);
                boolean isUpdated = this.workingDaysService.updateNoOfWorkingDays(workingDaysMain);
                int count = 0;

                if (isUpdated) {
                    for (String day : updateArrayListDay) {
                        WorkingDaysSub workingDaysSub = new WorkingDaysSub(updateId, day);
                        this.workingDaysService.addWorkingDaysSub(workingDaysSub);
                        count++;
                    }
                    if (count == updateArrayListDay.size()) {
                        Alert al = new Alert(Alert.AlertType.INFORMATION);
                        al.setTitle(null);
                        al.setContentText("Updated Successfully ");
                        WeekendController.count = 0;
                        WeekdaysController.count = 0;
                        al.setHeaderText(null);
                        al.showAndWait();
                        updateStatus = false;
                        this.getAllDetails();
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText(" Updated Fail ");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                }
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void saveDetails() {
        try {
            ArrayList<String> arrayListDay = new ArrayList<>();
            String selectedType = "";
            int noOfDays = Integer.parseInt(cmbNoDays.getValue().toString());
            if (btnRadioWeekday.isSelected()) {
                selectedType = WEEKDAY;
                if (WeekdaysController.monday) {
                    arrayListDay.add(MONDAY);
                }
                if (WeekdaysController.tuesday) {
                    arrayListDay.add(TUESDAY);
                }
                if (WeekdaysController.wednesday) {
                    arrayListDay.add(WEDNESDAY);
                }
                if (WeekdaysController.thursday) {
                    arrayListDay.add(THURSDAY);
                }
                if (WeekdaysController.friday) {
                    arrayListDay.add(FRIDAY);
                }
            } else if (btnRadioWeekEnd.isSelected()) {
                selectedType = WEEKEND;
                if (WeekendController.saturday) {
                    arrayListDay.add(SATURDAY);
                }
                if (WeekendController.sunday) {
                    arrayListDay.add(SUNDAY);
                }
            }
            WorkingDaysMain workingDaysMain = new WorkingDaysMain(selectedType, noOfDays);
            boolean status = this.workingDaysService.checkWeekDayOrWeekEndIsAdded(selectedType);
            if (!status) {
                int lastId = this.workingDaysService.addWorkingDays(workingDaysMain);
                int count = 0;
                if (lastId != 0) {
                    for (String day : arrayListDay) {
                        WorkingDaysSub workingDaysSub = new WorkingDaysSub(lastId, day);
                        this.workingDaysService.addWorkingDaysSub(workingDaysSub);
                        count++;
                    }
                }
                if (count == arrayListDay.size()) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle(null);
                    al.setContentText("Added Successfully ");
                    WeekendController.count = 0;
                    WeekdaysController.count = 0;
                    al.setHeaderText(null);
                    al.showAndWait();
                    this.getAllDetails();
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" Added Fail ");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Already an record exist ! You can add only one record !");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @FXML
    void changePanel(ActionEvent event) {
        pnlWorkingDays.getChildren().removeAll(new Node[0]);
        try {
            if (btnRadioWeekday.isSelected()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/WorkSchedule/Weekdays.fxml"));
                Parent root = loader.load();
                pnlWorkingDays.setCenter(root);
            } else if (btnRadioWeekEnd.isSelected()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/WorkSchedule/Weekends.fxml"));
                Parent root = loader.load();
                pnlWorkingDays.setCenter(root);
            }
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        WeekdayNumber = FXCollections.observableArrayList(new String[]{"1", "2", "3", "4", "5"});
        WeekendNumber = FXCollections.observableArrayList(new String[]{"1", "2"});
        btnRadioWeekday.setSelected(true);
        if (btnRadioWeekday.isSelected()) {
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/WorkSchedule/Weekdays.fxml"));
                root = loader.load();
            } catch (IOException e) {
                log.log(Level.SEVERE,e.getMessage());
            }
            pnlWorkingDays.setCenter(root);
            cmbNoDays.setItems(WeekdayNumber);
        }
        this.setTableProperties();
        this.getAllDetails();
    }

    public void getAllDetails() {
        try {
            ArrayList<WorkingDaysMain> list = this.workingDaysService.getAllNoOfWorkingDays();
            tblWorkingDays.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblWorkingDays.getSelectionModel().getTableView().getItems().clear();
        tblWorkingDays.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("workingId"));
        tblWorkingDays.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblWorkingDays.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("noOfDays"));
        colEdit.setCellFactory(cellFactoryBtnEdit);
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>> cellFactoryBtnEdit
            = new Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>>() {
                @Override
                public TableCell<WorkingDaysMain, Boolean> call(TableColumn<WorkingDaysMain, Boolean> param) {
                    final TableCell<WorkingDaysMain, Boolean> cell = new TableCell<WorkingDaysMain, Boolean>() {
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
                                    WorkingDaysMain mainDays = getTableView().getItems().get(getIndex());
                                    setWorkingDaysMainDetailsToFiled(mainDays);
                                    updateStatus = true;
                                    updateId = mainDays.getWorkingId();
                                });
                                btnEdit.setStyle("-fx-background-color: transparent;");
                                btnEdit.setGraphic(iconView);
                                setGraphic(btnEdit);
                                setAlignment(Pos.CENTER);
                                setText(null);
                            }
                        }

                    };
                    return cell;
                }
            };

    Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>> cellFactoryBtnDelete
            = new Callback<TableColumn<WorkingDaysMain, Boolean>, TableCell<WorkingDaysMain, Boolean>>() {
                @Override
                public TableCell<WorkingDaysMain, Boolean> call(TableColumn<WorkingDaysMain, Boolean> param) {
                    final TableCell<WorkingDaysMain, Boolean> cell = new TableCell<WorkingDaysMain, Boolean>() {
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
                                    WorkingDaysMain daysMain = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are you sure you want to delete this record ?");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteWorkingDay(daysMain.getWorkingId());
                                        getAllDetails();
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

    public void deleteWorkingDay(int workingId) {
        try {
            boolean isDeleted = this.workingDaysService.deleteWorkingDay(workingId);

            if (isDeleted) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                this.getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText("Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    private void setWorkingDaysMainDetailsToFiled(WorkingDaysMain mainDays) {
        try {
            ArrayList<String> days = this.workingDaysService.getWorkingDaysAccordingId(mainDays.getWorkingId());
            if (mainDays.getType().equals(WEEKEND)) {
                btnRadioWeekEnd.setSelected(true);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/views/WorkSchedule/Weekends.fxml"));
                Parent root = fxmlLoader.load();
                pnlWorkingDays.setCenter(root);
                WeekendController weekendController = fxmlLoader.getController();
                weekendController.checkSUN.setSelected(false);
                weekendController.checkSAT.setSelected(false);
                cmbNoDays.setItems(WeekendNumber);
                cmbNoDays.setValue(mainDays.getNoOfDays());
                for (String s : days) {
                    if (s.equals(SUNDAY)) {
                        weekendController.checkSUN.setSelected(true);
                        weekendController.selectedDays(new ActionEvent());
                    } else if (s.equals(SATURDAY)) {
                        weekendController.checkSAT.setSelected(true);
                        weekendController.selectedDays(new ActionEvent());
                    }
                }

            } else if (mainDays.getType().equals(WEEKDAY)) {
                btnRadioWeekday.setSelected(true);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/main/views/WorkSchedule/Weekdays.fxml"));
                Parent root = loader.load();

                pnlWorkingDays.setCenter(root);
                cmbNoDays.setItems(WeekdayNumber);
                cmbNoDays.setValue(mainDays.getNoOfDays());
                WeekdaysController weekdaysController = loader.getController();
                for (String s : days) {

                    if (s.equals(MONDAY)) {
                        weekdaysController.checkMON.setSelected(true);
                    } else if (s.equals(TUESDAY)) {
                        weekdaysController.checkTUE.setSelected(true);
                    } else if (s.equals(WEDNESDAY)) {
                        weekdaysController.checkWED.setSelected(true);
                    } else if (s.equals(THURSDAY)) {
                        weekdaysController.checkTHU.setSelected(true);
                    } else if (s.equals(FRIDAY)) {
                        weekdaysController.checkFRI.setSelected(true);
                    }
                }
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        } catch (IOException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

}

package main.controller.WorkSchedule;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import main.model.WorkingDaysSub;
import main.service.WorkingDaysService;
import main.service.WorkingHoursService;
import main.service.impl.WorkingDaysServiceImpl;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkingDaysDeleteController implements Initializable {




    @FXML
    private TableView<WorkingDaysSub> tblWorkingDays;

    @FXML
    private TableColumn<WorkingDaysSub, Boolean> colDelete;

    private WorkingDaysService workingDaysService;
    public static final Logger log = Logger.getLogger(WorkingDaysDeleteController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            workingDaysService = new WorkingDaysServiceImpl();
            this.setTableProperties();
            this.getAllDetails();
    }



    public void getAllDetails() {
        try {
            ArrayList<WorkingDaysSub> list = this.workingDaysService.getAllSubDetails();
            tblWorkingDays.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    public void setTableProperties() {
        tblWorkingDays.getSelectionModel().getTableView().getItems().clear();
        tblWorkingDays.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("subId"));
        tblWorkingDays.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("workingday"));
        colDelete.setCellFactory(cellFactoryBtnDelete);
    }

    Callback<TableColumn<WorkingDaysSub, Boolean>, TableCell<WorkingDaysSub, Boolean>> cellFactoryBtnDelete =
            new Callback<TableColumn<WorkingDaysSub, Boolean>, TableCell<WorkingDaysSub, Boolean>>() {
                @Override
                public TableCell<WorkingDaysSub, Boolean> call(TableColumn<WorkingDaysSub, Boolean> param) {
                    final TableCell<WorkingDaysSub, Boolean> cell = new TableCell<WorkingDaysSub, Boolean>() {
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
                                    WorkingDaysSub wds = getTableView().getItems().get(getIndex());
                                    Alert a2 = new Alert(Alert.AlertType.CONFIRMATION);
                                    a2.setTitle(null);
                                    a2.setHeaderText("Are You Okay To Delete This Row !!!");
                                    a2.setContentText(null);
                                    Optional<ButtonType> result = a2.showAndWait();
                                    if (result.get() == ButtonType.OK) {
                                        deleteWorkingDaysSub(wds.getSubId(),wds.getWorkingId());
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

    public void deleteWorkingDaysSub(int id,int workingId){

        try {
            boolean staus = workingDaysService.deleteWorkingDaysSub(id,workingId);
            if (staus) {
                Alert al = new Alert(Alert.AlertType.INFORMATION);
                al.setTitle(null);
                al.setContentText(" Deleted SuccessFully ");
                al.setHeaderText(null);
                al.showAndWait();
                getAllDetails();
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Deleted Fail ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }
}

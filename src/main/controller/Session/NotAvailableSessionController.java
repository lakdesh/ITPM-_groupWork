package main.controller.Session;

import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.model.*;
import main.service.*;
import main.service.impl.*;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NotAvailableSessionController implements Initializable {

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<?> cmbDate;

    @FXML
    private JFXTimePicker toTime;

    @FXML
    private JFXTimePicker fromTime;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtSubject;

    @FXML
    private TextField txtTag;

    @FXML
    private TextField txtGroup;

    @FXML
    private Button btnSearch;

    @FXML
    private Label lblSessionId;


    @FXML
    private RadioButton btnRadioMain;

    @FXML
    private RadioButton btnRadioSub;

    private MainGroupService mainGroupservice;
    private List<String> groupNameList;
    private List<Object> groupList;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private SubGroupService subGroupService;
    private String curTime;
    private LecturerService lecturerService;
    private TagService tagService;
    private SubjectService subjectService;
    private List<Lecturer> lectureList;
    private List<String> lectureNameList;
    private List<Tag> tagList;
    private List<String> tagNameList;
    private List<Subject> subList;
    private List<String> subNameList;
    private SessionService sessionService;
    String groupType = "";
    public static final Logger log = Logger.getLogger(NotAvailableSessionController.class.getName());

    @FXML
    void loadGroupDetails() {

        if (btnRadioMain.isSelected()) {
            loadMainGroupDetails();

        } else if (btnRadioSub.isSelected()) {
            loadSubGroupDetails();
        }
    }

    public void loadTagDetails() {
        try {
            ArrayList<Tag> tag = tagService.getAllDetails();
            for (Tag t : tag
            ) {
                tagList.add(t);
                tagNameList.add(t.getTagName());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtTag, tagNameList);
    }

    public void loadSubjectDetails() {
        try {
            ArrayList<Subject> subjects = subjectService.getAllSubjectDetails();
            for (Subject s : subjects
            ) {
                subList.add(s);
                subNameList.add(s.getSubName());
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtSubject, subNameList);
    }

    public void loadLectureDetails() {
        try {
            ArrayList<Lecturer> lec = lecturerService.getAllLecturerDetails();
            for (Lecturer l : lec
            ) {
                lectureNameList.add(l.getEmpName());
                lectureList.add(l);
            }

        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
        TextFields.bindAutoCompletion(txtLecturer, lectureNameList);
    }

    private void loadMainGroupDetails() {
        try {

            ArrayList<MainGroup> mainList = this.mainGroupservice.getAllMainGroupDetails();
            groupNameList.clear();

            groupList.clear();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            for (MainGroup m : mainList
            ) {
                groupNameList.add(m.getGroupid());
                groupList.add(m);
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroup, groupNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    private void loadSubGroupDetails() {
        try {

            ArrayList<SubGroup> subList1 = this.subGroupService.getAllSubGroupDetails(0);

            groupNameList.clear();
            groupList.clear();
            if (autoCompletionBinding != null) {
                autoCompletionBinding.dispose();
            }
            for (SubGroup s : subList1
            ) {
                groupNameList.add(s.getSubgroupid());
                groupList.add(s);
            }
            autoCompletionBinding = TextFields.bindAutoCompletion(txtGroup, groupNameList);
        } catch (SQLException e) {
            log.log(Level.SEVERE,e.getMessage());
        }
    }

    @FXML
    void searchSessionDetails(ActionEvent event) {
        String lectureName = txtLecturer.getText();
        int lecId = 0;
        int lecCount = 0;
        String subjectName = txtSubject.getText();
        String subId = "";
        int subCount = 0;
        String tagName = txtTag.getText();
        int tagId = 0;
        int tagCount = 0;

        int groupCount = 0;
        int subGroupId = 0;
        int mainGroupId = 0;
        if (btnRadioMain.isSelected()) {
            groupType = "MainGroup";
        } else if (btnRadioSub.isSelected()) {
            groupType = "SubGroup";
        }
        String groupId = txtGroup.getText();

        for (Lecturer l1 : lectureList) {
            if (l1.getEmpName().equals(lectureName.trim())) {
                lecId = l1.getEmpId();
                lecCount++;
            }
        }
        for (Subject s1 : subList) {
            if (s1.getSubName().equals(subjectName.trim())) {
                subId = s1.getSubId();
                subCount++;
            }
        }
        for (Tag t1 : tagList) {
            if (t1.getTagName().equals(tagName.trim())) {
                tagId = t1.getTagId();
                tagCount++;
            }
        }
        for (Object m : this.groupList
        ) {
            if (m instanceof MainGroup && groupId.equals(((MainGroup) m).getGroupid())) {
                    mainGroupId = ((MainGroup) m).getId();
                    groupCount++;


            }
            if (m instanceof SubGroup && groupId.equals(((SubGroup) m).getSubgroupid())) {
                    subGroupId = ((SubGroup) m).getId();
                    groupCount++;

            }
        }

        if (!lectureName.isEmpty()) {
            if (!subjectName.isEmpty()) {
                if (!tagName.isEmpty()) {
                    if (!groupId.isEmpty()) {
                        if (lecCount != 0) {
                            if (subCount != 0) {
                                if (tagCount != 0) {
                                    if (groupCount != 0) {
                                        try {
                                            int sessionId = sessionService.searchSession(lecId, subId, tagId, subGroupId, mainGroupId);
                                            if (sessionId != 0) {
                                                lblSessionId.setText(Integer.toString(sessionId));
                                            } else {
                                                Alert al = new Alert(Alert.AlertType.ERROR);
                                                al.setTitle(null);
                                                al.setContentText("This session Is not Available");
                                                al.setHeaderText(null);
                                                al.showAndWait();
                                            }
                                        } catch (SQLException e) {
                                            log.log(Level.SEVERE,e.getMessage());
                                        }

                                    } else {
                                        Alert al = new Alert(Alert.AlertType.ERROR);
                                        al.setTitle(null);
                                        al.setContentText("Group Is not Exists In this System");
                                        al.setHeaderText(null);
                                        al.showAndWait();
                                    }
                                } else {
                                    Alert al = new Alert(Alert.AlertType.ERROR);
                                    al.setTitle(null);
                                    al.setContentText("Tag Is not Exists In this System !");
                                    al.setHeaderText(null);
                                    al.showAndWait();
                                }
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Subject Is not Exists In this System !");
                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } else {
                            Alert al = new Alert(Alert.AlertType.ERROR);
                            al.setTitle(null);
                            al.setContentText("Lecture Is not Exists In this System");
                            al.setHeaderText(null);
                            al.showAndWait();
                        }
                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText("GroupId Field Is Empty");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText("Tag Field Is Empty");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText("Subject Field Is Empty");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("Lecture Field Is Empty");
            al.setHeaderText(null);
            al.showAndWait();
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        curTime = dtf.format(now);
        toTime.setValue(LocalTime.parse(curTime));
        fromTime.setValue(LocalTime.parse(curTime));
        mainGroupservice = new MainGroupServiceImpl();
        groupNameList = new ArrayList<>();
        groupList = new ArrayList<>();
        btnRadioMain.setSelected(true);
        lectureList = new ArrayList<>();
        lectureNameList = new ArrayList<>();
        tagList = new ArrayList<>();
        tagNameList = new ArrayList<>();
        subList = new ArrayList<>();
        subNameList = new ArrayList<>();
        subGroupService = new SubGroupServiceImpl();
        lecturerService = new LectureServiceImpl();
        tagService = new TagServiceImpl();
        subjectService = new SubjectServiceImpl();
        sessionService = new SessionServiceImpl();
        this.loadTagDetails();
        this.loadSubjectDetails();
        this.loadLectureDetails();
        this.loadGroupDetails();
    }

    @FXML
    void saveDetails(ActionEvent event) {

        String day = (String) cmbDate.getValue();
        String getToTime = toTime.getValue().toString();
        String getFromTime = fromTime.getValue().toString();
        String sessionId = lblSessionId.getText();
        if (!sessionId.isEmpty()) {
            if (day != null) {
                if (!getToTime.isEmpty()) {
                    if (!getFromTime.isEmpty()) {

                        NotAvailableSession nas = new NotAvailableSession();
                        nas.setDay(day);
                        nas.setFromTime(getFromTime);
                        nas.setToTime(getToTime);
                        nas.setSessionId(Integer.parseInt(sessionId));
                        try {
                            boolean isAdded = this.sessionService.saveDetails(nas);
                            if (isAdded) {

                                Alert al = new Alert(Alert.AlertType.INFORMATION);
                                al.setTitle(null);
                                al.setContentText("Added Successfully ");
                                al.setHeaderText(null);
                                al.showAndWait();
                                lblSessionId.setText("");
                                toTime.setValue(LocalTime.parse(curTime));
                                fromTime.setValue(LocalTime.parse(curTime));
                                txtGroup.setText("");
                                txtSubject.setText("");
                                txtLecturer.setText("");
                                txtTag.setText("");
                            } else {
                                Alert al = new Alert(Alert.AlertType.ERROR);
                                al.setTitle(null);
                                al.setContentText("Added Fail");

                                al.setHeaderText(null);
                                al.showAndWait();
                            }
                        } catch (SQLException e) {
                            log.log(Level.SEVERE,e.getMessage());
                        }


                    } else {
                        Alert al = new Alert(Alert.AlertType.ERROR);
                        al.setTitle(null);
                        al.setContentText(" FromTime Field is Empty.Please Select From Time!!!");
                        al.setHeaderText(null);
                        al.showAndWait();
                    }
                } else {
                    Alert al = new Alert(Alert.AlertType.ERROR);
                    al.setTitle(null);
                    al.setContentText(" ToTime Field is Empty.Please Select To Time!!! ");
                    al.setHeaderText(null);
                    al.showAndWait();
                }
            } else {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.setTitle(null);
                al.setContentText(" Day Field is Empty.Please Select Day!!! ");
                al.setHeaderText(null);
                al.showAndWait();
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.setTitle(null);
            al.setContentText("ConsectiveSession Id is not searched!!!");
            al.setHeaderText(null);
            al.showAndWait();
        }
    }
}

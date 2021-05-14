package main.service;

import main.model.WorkingDaysMain;
import main.model.WorkingDaysSub;

import java.sql.SQLException;
import java.util.ArrayList;

public interface WorkingDaysService {

    int addWorkingDays(WorkingDaysMain workingDaysMain) throws SQLException;

    boolean addWorkingDaysSub(WorkingDaysSub workingDaysSub) throws SQLException;

    public ArrayList<WorkingDaysMain> getAllNoOfWorkingDays() throws SQLException;

    ArrayList<String> getWorkingDaysAccordingId(int workingId) throws SQLException;

    boolean deleteWorkingDay(int workingId)throws SQLException;

    boolean deleteWorkingDaysfromSub(int updateId) throws SQLException;

    boolean updateNoOfWorkingDays(WorkingDaysMain workingDaysMain) throws SQLException;

    ArrayList<WorkingDaysSub> getAllSubDetails() throws SQLException;

    boolean deleteWorkingDaysSub(int id,int workingId)throws SQLException;

    boolean checkWeekDayOrWeekEndIsAdded(String selectedType) throws SQLException;

    int getCountOfWorkingDays() throws SQLException;

    double getWorkingTime() throws SQLException;

    String getWorkingTimeType() throws SQLException;
}

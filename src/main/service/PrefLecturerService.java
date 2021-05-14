package main.service;

import main.model.PrefLecturer;
import main.model.Room;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PrefLecturerService {

    int getBuildingIdFromLecturer(String lecturer) throws SQLException;


    ArrayList<Room> getRoomNamesFromRooms(int buildingId) throws SQLException;

    int getRoomId(String room) throws SQLException;

    int getLecturerIdFromLecturers(String lecturer) throws SQLException;

    boolean savePrefLecturerRoom(PrefLecturer prefLecturer) throws SQLException;
}

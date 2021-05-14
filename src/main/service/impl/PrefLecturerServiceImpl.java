package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefLecturer;
import main.model.Room;
import main.service.PrefLecturerService;

import java.sql.*;
import java.util.ArrayList;

public class PrefLecturerServiceImpl implements PrefLecturerService {

    private Connection connection;
    public PrefLecturerServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public int getBuildingIdFromLecturer(String lecturer) throws SQLException {

        Statement stm = null;
        try {
            String sql ="Select buildingId from lecturer where employeeName LIKE '%" +lecturer+ "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result=0;
                if(rst.next()){
                    result = rst.getInt("buildingId");
                }

                return result;

            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public ArrayList<Room> getRoomNamesFromRooms(int buildingId) throws SQLException {
        Statement stm = null;
        try {
            String sql ="Select room from room where buildingid ='" +buildingId+ "'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Room> roomidList = new ArrayList<>();
                while(rst.next()){
                    Room roomRows = new Room(rst.getString("room"));
                    roomidList.add(roomRows);
                }
                return roomidList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getRoomId(String room) throws SQLException {
        Statement stm = null;
        try {
            String sql ="Select rid from room where room LIKE '%"+room+"%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result=0;
                if(rst.next()){
                    result = rst.getInt("rid");
                }
                return result;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getLecturerIdFromLecturers(String lecturer) throws SQLException {
        Statement stm = null;
        try {
            String sql  ="Select employeeId from lecturer where employeeName LIKE '%"+lecturer+"%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result=0;
                if(rst.next()){
                    result = rst.getInt("employeeId");
                }
                return result;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public boolean savePrefLecturerRoom(PrefLecturer prefLecturer) throws SQLException {
        String sql = "Insert into prefroomlecturer Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, prefLecturer.getEmployeeId());
            stm.setObject(3, prefLecturer.getRoomId());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }


}

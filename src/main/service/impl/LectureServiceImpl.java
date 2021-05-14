package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Lecturer;
import main.model.NotAvailableLecturer;
import main.service.LecturerService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LectureServiceImpl implements LecturerService {
    private Connection connection;

    public LectureServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }
    private static final String EMPLOYEE_NAME = "employeeName";
    @Override
    public boolean saveLecturer(Lecturer lecturer) throws SQLException {
        String sql = "Insert into lecturer  Values(?,?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, lecturer.getEmpId());
            stm.setObject(2, lecturer.getEmpName());
            stm.setObject(3, lecturer.getFaculty());
            stm.setObject(4, lecturer.getDepartment());
            stm.setObject(5, lecturer.getDesignation());
            stm.setObject(6, lecturer.getCenter());
            stm.setObject(7, lecturer.getBuilding());
            stm.setObject(8, lecturer.getLevel());
            stm.setObject(9, lecturer.getRank());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<Lecturer> getAllLecturerDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from lecturer";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Lecturer> lecturers = new ArrayList<>();
                while (rst.next()) {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setEmpId(Integer.parseInt(rst.getString("employeeId")));
                    lecturer.setEmpName(rst.getString(EMPLOYEE_NAME));
                    lecturer.setFaculty(rst.getString("faculty"));
                    lecturer.setDepartment(Integer.parseInt(rst.getString("departmentId")));
                    lecturer.setCenter(rst.getString("center"));
                    lecturer.setBuilding(Integer.parseInt(rst.getString("buildingId")));
                    lecturer.setDesignation(rst.getString("designation"));
                    lecturer.setLevel(Integer.parseInt(rst.getString("level")));
                    lecturer.setRank(rst.getString("ranks"));
                    lecturers.add(lecturer);
                }
                return lecturers;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public ArrayList<Lecturer> searchLecturerDetails(String name) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from lecturer where employeeName LIKE '%" + name + "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Lecturer> lecturers = new ArrayList<>();
                while (rst.next()) {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setEmpId(Integer.parseInt(rst.getString("employeeId")));
                    lecturer.setEmpName(rst.getString(EMPLOYEE_NAME));
                    lecturer.setFaculty(rst.getString("faculty"));
                    lecturer.setDepartment(Integer.parseInt(rst.getString("departmentId")));
                    lecturer.setDesignation(rst.getString("designation"));
                    lecturer.setCenter(rst.getString("center"));
                    lecturer.setBuilding(Integer.parseInt(rst.getString("buildingId")));
                    lecturer.setLevel(Integer.parseInt(rst.getString("level")));
                    lecturer.setRank(rst.getString("ranks"));
                    lecturers.add(lecturer);
                }
                return lecturers;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public void deleteLecturerDetails(int id) throws SQLException {
        String sql = "delete from lecturer where employeeId LIKE '%" + id + "%'";
        Statement stm = connection.createStatement();
        try {
            stm.executeUpdate(sql);
        } finally {
            stm.close();
        }

    }

    @Override
    public boolean updateLecturer(Lecturer lecturer) throws SQLException {
        lecturer.setBuilding(1);
        lecturer.setDepartment(1);
        String sql = "Update lecturer set employeeName='" + lecturer.getEmpName() + "',faculty='" + lecturer.getFaculty() + "',departmentId='" + lecturer.getDepartment() + "' ,center='" + lecturer.getCenter() + "',designation='" + lecturer.getDesignation() + "',level='" + lecturer.getLevel() + "'" +
                "where employeeId='" + lecturer.getEmpId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean addNotAvailableLectures(NotAvailableLecturer nal) throws SQLException {
        String sql = "Insert into notavailablelecture  Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, nal.getDay());
            stm.setObject(3, nal.getToTime());
            stm.setObject(4, nal.getFromTime());
            stm.setObject(5, nal.getLectureId());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean deleteNotAvailableLecturer(int id) throws SQLException {
        String sql = "delete from notavailablelecture where id LIKE '%" + id + "%'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public List<NotAvailableLecturer> getAllNotAvailableLecturers(String name) throws SQLException {
        Statement stm = null;
        String sql = "";
        try {
            if (name.isEmpty()) {
                sql = "select * from notavailablelecture nal ,lecturer l where nal.lectureId=l.employeeId ";
            } else {
                sql = "select * from notavailablelecture nal,lecturer l where nal.lectureId=l.employeeId and " +
                        "l.employeeName LIKE '%" + name + "%' ";
            }
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<NotAvailableLecturer> main = new ArrayList<>();
                while (rst.next()) {
                    NotAvailableLecturer nal = new NotAvailableLecturer();
                    nal.setId(Integer.parseInt(rst.getString("id")));
                    nal.setToTime(rst.getString("toTime"));
                    nal.setFromTime(rst.getString("fromTime"));
                    nal.setDay(rst.getString("day"));
                    nal.setLectureName(rst.getString(EMPLOYEE_NAME));
                    main.add(nal);
                }
                return main;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}

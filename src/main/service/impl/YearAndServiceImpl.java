package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.YearAndSemester;
import main.service.YearandSemesterService;

import java.sql.*;
import java.util.ArrayList;


public class YearAndServiceImpl implements YearandSemesterService {

    private Connection connection;
    private static String yearSem;

    public YearAndServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveAcademiceYear(YearAndSemester yearAndSemester) throws SQLException {

        String sql = "Insert into academicyearandsemester Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {

            stm.setObject(1, 0);
            stm.setObject(2, yearAndSemester.getYearName());
            stm.setObject(3, yearAndSemester.getSemesterName());
            stm.setObject(4, yearAndSemester.getFullName());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean searchYearAndSemester(String year, String semester) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select id from academicyearandsemester where yearName = '" + year + "' " +
                    "&& semesterName='" + semester + "'";
            stm = connection.createStatement();
            boolean result = false;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            return result;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public ArrayList<YearAndSemester> getAllDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from academicyearandsemester";
            stm = connection.createStatement();
            ArrayList<YearAndSemester> yearAndSemesterList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    YearAndSemester yearAndSemester = new YearAndSemester(Integer.parseInt(rst.getString("id")),
                            rst.getString("yearName"),
                            rst.getString("semesterName"),
                            rst.getString("fullName"));
                    yearAndSemesterList.add(yearAndSemester);
                }
            }
            return yearAndSemesterList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }


    @Override
    public boolean updateYearAndSemester(YearAndSemester semester) throws SQLException {

        String sql = "Update academicyearandsemester set yearName='" + semester.getYearName() + "'," +
                "semesterName='" + semester.getSemesterName() + "',fullName='" + semester.getFullName() + "'  " +
                "where id='" + semester.getId() + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;

        } finally {
            stm.close();
        }

    }

    @Override
    public boolean deleteYearAndSemester(int key) throws SQLException {
        String sql = "Delete From academicyearandsemester where id = '" + key + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public String searchYearAndSemesterName(int id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select fullName  from academicyearandsemester where id = '" + id + "' ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    yearSem = rst.getString("fullName");
                    System.out.println(yearSem);
                }
            }
            return yearSem;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}

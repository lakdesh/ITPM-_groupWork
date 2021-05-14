package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.Department;
import main.service.DepartmentService;

import java.sql.*;
import java.util.ArrayList;

public class DepartmentServiceImpl implements DepartmentService {

    private Connection connection;
    static String departmentName;
    public DepartmentServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean searchDepartment(String departmentName) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select dId from department where departmentName = '" + departmentName + "' ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                boolean result = false;
                if (rst.next()) {
                    if (rst.getString("dId") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
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
    public boolean saveDepartment(Department department) throws SQLException {
        String sql = "Insert into department Values(?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, department.getDepartmentName());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean updateDepartment(Department department) throws SQLException {
        String sql="Update department set departmentName='"+department.getDepartmentName()+"' where " +
                "dId='"+department.getDepartmentId()+"'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<Department> getAllDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql ="Select * from department";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Department> dptList = new ArrayList<>();
                while(rst.next()){
                    Department t = new Department(Integer.parseInt(rst.getString("dId")),
                            rst.getString("departmentName"));
                    dptList.add(t);
                }
                return dptList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public String searchDepartmentName(int id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select departmentName  from department where dId = '" + id + "' ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Department> dptList = new ArrayList<>();
                while(rst.next()) {

                    departmentName=rst.getString("departmentName");
                }
                return departmentName;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }
}

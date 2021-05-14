package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefSubject;
import main.service.PrefSubjectService;

import java.sql.*;


public class PrefSubjectServiceImpl implements PrefSubjectService {

    private Connection connection;
    public PrefSubjectServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }


    @Override
    public String getSubIdFromSubjects(String subject) throws SQLException {
        Statement stm = null;
        try {
            String sql ="Select subId from subject where subName LIKE '%" +subject+ "%'";
            stm = connection.createStatement();
            String result="";
            try (ResultSet rst = stm.executeQuery(sql)) {
                if(rst.next()){
                    result = rst.getString("subId");
                }
                return  result;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean savePrefSubjectRoom(PrefSubject prefSub) throws SQLException {
        String sql = "Insert into prefroomsubject Values(?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, prefSub.getTagId());
            stm.setObject(3, prefSub.getSubjectId());
            stm.setObject(4, prefSub.getRoomId());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }
}

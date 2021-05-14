package main.service.impl;


import main.dbconnection.DBConnection;
import main.model.Subject;
import main.service.SubjectService;

import java.sql.*;
import java.util.ArrayList;

public class SubjectServiceImpl implements SubjectService {
    private Connection connection;
    private Subject subject;
    public SubjectServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean saveSubject(Subject subject) throws SQLException {
        String sql = "Insert into subject  Values(?,?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, subject.getSubId());
            stm.setObject(2, subject.getSubName());
            stm.setObject(3, subject.getOfferedYearSem());
            stm.setObject(4, subject.getNoLecHrs());
            stm.setObject(5, subject.getNoTutHrs());
            stm.setObject(6, subject.getNoEvalHrs());
            stm.setObject(7, subject.getSubType());
            stm.setObject(8, subject.getCategory());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<Subject> getAllSubjectDetails() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from subject";
            stm = connection.createStatement();
            ArrayList<Subject>  subjects = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while(rst.next()){
                    Subject subject=new Subject();
                    subject.setSubId(rst.getString("subId"));
                    subject.setSubName(rst.getString("subName"));
                    subject.setOfferedYearSem(Integer.parseInt(rst.getString("offeredYearSemId")));
                    subject.setNoLecHrs(Integer.parseInt(rst.getString("noLecHrs")));
                    subject.setNoTutHrs(Integer.parseInt(rst.getString("noTutHrs")));
                    subject.setNoEvalHrs(Integer.parseInt(rst.getString("noEvalHrs")));
                    subjects.add(subject);
                }
            }
            return subjects;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public void deleteSubjectDetails(String id) throws SQLException {
        String sql = "delete from subject where subId LIKE '%" + id + "%'";
        Statement stm = connection.createStatement();
        try {
            stm.executeUpdate(sql);
        } finally {
            stm.close();
        }

    }

    @Override
    public boolean updateSubject(Subject subject) throws SQLException {
        String sql="Update subject set subName='"+subject.getSubName()+"',offeredYearSemId='"+subject.getOfferedYearSem()+"',noLecHrs='"+subject.getNoLecHrs()+"' ,noTutHrs='"+subject.getNoTutHrs()+"',noEvalHrs='"+subject.getNoEvalHrs()+"'" +
                "where subId='"+subject.getSubId()+"'";
        Statement stm = connection.createStatement();
        try {
           return  stm.executeUpdate(sql)>0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<Subject> searchSubjectDetails(String name) throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select * from subject where subId LIKE '%" + name + "%'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<Subject>  subjects = new ArrayList<>();
                while(rst.next()){
                    Subject subject=new Subject();
                    subject.setSubId(rst.getString("subId"));
                    subject.setSubName(rst.getString("subName"));
                    subject.setOfferedYearSem(Integer.parseInt(rst.getString("offeredYearSemId")));
                    subject.setNoLecHrs(Integer.parseInt(rst.getString("noLecHrs")));
                    subject.setNoTutHrs(Integer.parseInt(rst.getString("noTutHrs")));
                    subject.setNoEvalHrs(Integer.parseInt(rst.getString("noEvalHrs")));
                    subjects.add(subject);
                }
                return subjects;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Subject getCategory(String id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select sub.category, sub.subjectType from  subject sub where  sub.subId = '"+id+"'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while(rst.next()){
                    subject=new Subject(rst.getString("subjectType"),rst.getString("category"));
                }
                return subject;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }


}

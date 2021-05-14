package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.*;
import main.service.SessionService;

import java.sql.*;
import java.util.ArrayList;

public class SessionServiceImpl implements SessionService {

    private Connection connection;

    private static final String AND_TAGID = "and tagId='";
    private static final String SESSION_ID = "sessionId";
    private static final String TAG_NAME ="tagName";
    private static final String SUB_NAME ="subName";
    private static final String DURATION ="duration";
    private static final String STUDENT_COUNT = "studentCount";
    private static final String SUBJECT_ID = "subjectId";
    private static final String TAG_ID = "tagId";
    private static final String GROUP_ID = "groupId";
    private static final String SUBGROUP_ID = "subGroupId";
    private static final String ISCONSECUTIVE = "isConsecutive";
    private static final String CONSECTIVE_ADDED = "consectiveAdded";
    private ArrayList<SessionDTO> csList = new ArrayList<>();

    public SessionServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public int searchSession(int lecId, String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException {
        Statement stm = null;
        String sql = "";
        try {
            if (subGroupId != 0) {
                sql = "select s.sessionId from session s ,sessionlecture sl where s.sessionId= sl.sessionId and " +
                        "sl.lecturerId = '" + lecId + "' and subjectId ='" + subId + "' " +
                        AND_TAGID + tagId + "' and (subGroupId ='" + subGroupId + "' or groupId =NULL)";
            } else if (mainGroupId != 0) {
                sql = "select s.sessionId from session s ,sessionlecture sl  where s.sessionId= sl.sessionId and " +
                        "sl.lecturerId  = '" + lecId + "' " +
                        "and subjectId ='" + subId + "' " +
                        AND_TAGID + tagId + "' and (subGroupId =NULL or groupId ='" + mainGroupId + "')";
            }
            stm = connection.createStatement();
            int result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    if (!rst.getString(SESSION_ID).isEmpty()) {
                        result = Integer.parseInt(rst.getString(SESSION_ID));
                    } else {
                        result = 0;
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
    public int searchSessionByDetails(String subId, int tagId, int subGroupId, int mainGroupId) throws SQLException {
        Statement stm = null;
        String sql = "";
        try {
            if (subGroupId != 0) {
                sql = "select sessionId from session where" +
                        " subjectId ='" + subId + "' " +
                        AND_TAGID + tagId + "' and (subGroupId ='" + subGroupId + "' or groupId =NULL)";
            } else if (mainGroupId != 0) {
                sql = "select sessionId from session where" +
                        " subjectId ='" + subId + "' " +
                        AND_TAGID + tagId + "' and (groupId ='" + mainGroupId + "' or  subGroupId =NULL)";
            }
            stm = connection.createStatement();
            int result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {

                if (rst.next()) {
                    if (!rst.getString(SESSION_ID).isEmpty()) {
                        result = Integer.parseInt(rst.getString(SESSION_ID));
                    } else {
                        result = 0;
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
    public boolean saveDetails(NotAvailableSession nas) throws SQLException {
        String sql = "Insert into notavailablesession Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, nas.getSessionId());
            stm.setObject(3, nas.getDay());
            stm.setObject(4, nas.getToTime());
            stm.setObject(5, nas.getFromTime());
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public ArrayList<ConsectiveSession> getAllConsectiveSessions(String lecturer, String subject) throws SQLException {
        Statement stm = null;
        String sql = "";
        try {
            if (!lecturer.isEmpty() && !subject.isEmpty()) {
                sql = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                        "from session s ,subject su,tag t,maingroup mg,sessionlecture sl ,lecturer l " +
                        "where s.isConsecutive = 'Yes' and mg.id=s.groupId  and sl.sessionId= s.sessionId " +
                        "and l.employeeId= sl.lecturerId and " +
                        "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' " +
                        "and l.employeeName='" + lecturer + "' and su.subId='" + subject + "' order by su.subName";
            } else if (lecturer.isEmpty() && subject.isEmpty()) {
                sql = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                        "from session s ,subject su,tag t,maingroup mg " +
                        "where s.isConsecutive = 'Yes' and mg.id=s.groupId  and " +
                        "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' order by su.subName";
            } else if (lecturer.isEmpty() && !subject.isEmpty()) {
                sql = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                        "from session s ,subject su,tag t,maingroup mg " +
                        "where s.isConsecutive = 'Yes' and mg.id=s.groupId and " +
                        "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' " +
                        "and su.subId='" + subject + "' order by su.subName";
            } else if (!lecturer.isEmpty() && subject.isEmpty()) {
                sql = "select s.sessionId,mg.groupid,t.tagName,su.subName " +
                        "from session s ,subject su,tag t,maingroup mg,sessionlecture sl ,Lecturer l " +
                        "where s.isConsecutive = 'Yes' and mg.id=s.groupId  and sl.sessionId= s.sessionId " +
                        "and l.employeeId= sl.lecturerId and " +
                        "su.subId = s.subjectId and t.tagid = s.tagId and s.consectiveAdded ='No' " +
                        "and l.employeeName='" + lecturer + "' order by su.subName";
            }
            stm = connection.createStatement();
            int result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {

                ArrayList<ConsectiveSession> csList = new ArrayList<>();
                while (rst.next()) {
                    ConsectiveSession cs = new ConsectiveSession();
                    cs.setId(Integer.parseInt(rst.getString(SESSION_ID)));
                    cs.setGroupId(rst.getString(GROUP_ID));
                    cs.setSubject(rst.getString(SUB_NAME));
                    cs.setTag(rst.getString(TAG_NAME));
                    csList.add(cs);
                }

                return csList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getSessionIdForConsectiveSession(String groupId, String subject, String tagName) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select sessionId " +
                    "from session s ,maingroup mg ,subject su,tag t " +
                    "where s.isConsecutive = 'Yes'  and  mg.id=s.groupId and " +
                    "su.subId = s.subjectId and t.tagid = s.tagId  and " +
                    "mg.groupid ='" + groupId + "' and su.subName='" + subject + "' and t.tagName='" + tagName + "' ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                int result = 0;
                if (rst.next()) {
                    if (!rst.getString(SESSION_ID).isEmpty()) {
                        result = Integer.parseInt(rst.getString(SESSION_ID));
                    } else {
                        result = 0;
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
    public boolean updateRowForConsectiveSession(int id) throws SQLException {
        String sql = "Update session set consectiveAdded='Yes' where sessionId='" + id + "'";
        Statement stm = connection.createStatement();
        try {
            return stm.executeUpdate(sql) > 0;
        } finally {
            stm.close();
        }

    }

    @Override
    public boolean saveCosectiveSession(int id, int id1) throws SQLException {
        String sql = "Insert into consectivesession Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, id);
            stm.setObject(3, id1);
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean addSession(Session s1) throws SQLException {
        if (s1.getSubGroupId() != null) {
            String sql = "Insert into session(subjectId,tagId,groupId,subGroupId,studentCount,duration,isConsecutive,consectiveAdded,isParallel,category)  Values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm1 = connection.prepareStatement(sql);
            try {
                stm1.setObject(1, s1.getSubjectId());
                stm1.setObject(2, s1.getTagId());
                stm1.setObject(3, Integer.parseInt(s1.getGroupId()));
                stm1.setObject(4, Integer.parseInt(s1.getSubGroupId()));
                stm1.setObject(5, s1.getStudentCount());
                stm1.setObject(6, s1.getDuration());
                stm1.setObject(7, s1.getIsConsecutive());
                stm1.setObject(8, "No");
                stm1.setObject(9, s1.getIsParallel());
                stm1.setObject(10, s1.getCategory());
                int res = stm1.executeUpdate();
                return res > 0;
            } finally {
                stm1.close();
            }

        } else {
            String sql = "Insert into session(subjectId,tagId,groupId,studentCount,duration,isConsecutive,consectiveAdded,isParallel,category)  Values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement stm2 = connection.prepareStatement(sql);
            try {
                stm2.setObject(1, s1.getSubjectId());
                stm2.setObject(2, s1.getTagId());
                stm2.setObject(3, Integer.parseInt(s1.getGroupId()));
                stm2.setObject(4, s1.getStudentCount());
                stm2.setObject(5, s1.getDuration());
                stm2.setObject(6, s1.getIsConsecutive());
                stm2.setObject(7, "No");
                stm2.setObject(8, s1.getIsParallel());
                stm2.setObject(9, s1.getCategory());
                int res = stm2.executeUpdate();
                return res > 0;
            } finally {
                stm2.close();
            }
        }

    }

    @Override
    public boolean addLectureSession(int lecturerId, int sessionId) throws SQLException {

        String sql = "Insert into sessionlecture(lecturerId,sessionId) Values(?,?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        try {
            stmt.setObject(1, lecturerId);
            stmt.setObject(2, sessionId);
            int res = stmt.executeUpdate();
            return res > 0;
        } finally {
            stmt.close();
        }
    }

    @Override
    public ArrayList<SessionDTO> getAllSessions() throws SQLException {
        Statement stm = null;
        try {
            String sql = "Select s.sessionId,sub.subName,t.tagName,s.studentCount,s.duration,l.employeeName,m.mgroupName from session s ,sessionlecture sl,subject sub,lecturer l,tag t,maingroup m where s. sessionId=sl.sessionId and s.tagId=t.tagid and s.subjectId=sub.subId and sl.lecturerId=l.employeeId and s.groupId=m.id order by sub.subName";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    SessionDTO cs = new SessionDTO(Integer.parseInt(rst.getString(SESSION_ID)), rst.getString(SUB_NAME), rst.getString(TAG_NAME), Integer.parseInt(rst.getString(STUDENT_COUNT)), Float.parseFloat(rst.getString(DURATION)), rst.getString("mgroupName"), rst.getString("employeeName"));
                    csList.add(cs);
                }
                return csList;

            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<SessionDTO> searchSessions(String id) throws SQLException {

        Statement stm = null;
        try {
            String sql="Select s.sessionId,sub.subName,t.tagName,s.studentCount,s.duration,l.employeeName,m.mgroupName " +
                    "from session s ,sessionlecture sl,subject sub,lecturer l,tag t,maingroup m " +
                    "where s. sessionId=sl.sessionId and s.tagId=t.tagid and s.subjectId=sub.subId " +
                    "and sl.lecturerId=l.employeeId and s.groupId=m.id and (sub.subName='" + id+ "' OR l.employeeName='" + id+ "' " +
                    "OR m.mgroupName='" + id+ "')";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    SessionDTO cs = new SessionDTO(Integer.parseInt(rst.getString(SESSION_ID)), rst.getString(SUB_NAME), rst.getString(TAG_NAME), Integer.parseInt(rst.getString(STUDENT_COUNT)), Float.parseFloat(rst.getString(DURATION)), rst.getString("mgroupName"), rst.getString("employeeName"));
                    csList.add(cs);
                }
                return csList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<SessionTagGroup> getSessionsAccordingToMainGroupId(String groupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select s.*,t.tagname " +
                    "from session s, tag t ,maingroup m " +
                    "where s.tagId = t.tagid and s.groupId = m.id and m.groupid='" + groupId + "' and isParallel='No' " +
                    "order by s.subjectId";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<SessionTagGroup> csList = new ArrayList<>();
                while (rst.next()) {
                    SessionTagGroup stg = new SessionTagGroup(
                            Integer.parseInt(rst.getString(SESSION_ID)),
                            rst.getString(SUBJECT_ID),
                            Integer.parseInt(rst.getString(TAG_ID)),
                            rst.getString(GROUP_ID),
                            rst.getString(SUBGROUP_ID),
                            Integer.parseInt(rst.getString(STUDENT_COUNT)),
                            Float.parseFloat(rst.getString(DURATION)),
                            rst.getString(ISCONSECUTIVE),
                            rst.getString(CONSECTIVE_ADDED),
                            rst.getString(TAG_NAME));
                    csList.add(stg);
                }
                return csList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<SessionTagGroup> getParallelSessionsAccordingToMainGroupId(String trim) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select s.*,t.tagname " +
                    "from session s, tag t ,maingroup m " +
                    "where s.tagId = t.tagid and s.groupId = m.id and m.groupid='" + trim + "' and s.isParallel='Yes' " +
                    "order by s.subjectId ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<SessionTagGroup> csList = new ArrayList<>();
                while (rst.next()) {
                    SessionTagGroup stg = new SessionTagGroup(
                            Integer.parseInt(rst.getString(SESSION_ID)),
                            rst.getString(SUBJECT_ID),
                            Integer.parseInt(rst.getString(TAG_ID)),
                            rst.getString(GROUP_ID),
                            rst.getString(SUBGROUP_ID),
                            Integer.parseInt(rst.getString(STUDENT_COUNT)),
                            Float.parseFloat(rst.getString(DURATION)),
                            rst.getString(ISCONSECUTIVE),
                            rst.getString(CONSECTIVE_ADDED),
                            rst.getString(TAG_NAME));
                    csList.add(stg);
                }
                return csList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<SessionTagGroup> getParallelSessionsAccordingOrderId(String orderId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select s.*,t.tagname " +
                    "from session s, tag t ,parrellsessions p " +
                    "where s.tagId = t.tagid and s.sessionId = p.sessionId and p.orderId='" + orderId + "' " +
                    "order by s.subjectId ";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                ArrayList<SessionTagGroup> csList = new ArrayList<>();
                while (rst.next()) {
                    SessionTagGroup stg = new SessionTagGroup(
                            Integer.parseInt(rst.getString(SESSION_ID)),
                            rst.getString(SUBJECT_ID),
                            Integer.parseInt(rst.getString(TAG_ID)),
                            rst.getString(GROUP_ID),
                            rst.getString(SUBGROUP_ID),
                            Integer.parseInt(rst.getString(STUDENT_COUNT)),
                            Float.parseFloat(rst.getString(DURATION)),
                            rst.getString(ISCONSECUTIVE),
                            rst.getString(CONSECTIVE_ADDED),
                            rst.getString(TAG_NAME));
                    csList.add(stg);
                }
                return csList;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }


}






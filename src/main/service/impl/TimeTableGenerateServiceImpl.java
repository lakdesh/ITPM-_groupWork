package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.LecturerTimeTable;
import main.model.ParallelSession;
import main.model.RoomTimeTable;
import main.model.SessionArray;
import main.service.TimeTableGenerateService;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;

public class TimeTableGenerateServiceImpl implements TimeTableGenerateService {
    private Connection connection;

    public TimeTableGenerateServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }
    private static final String SUB_ID= "subId";
    private static final String SUB_NAME= "subName";
    private static final String TAG_NAME= "tagName";
    private static final String SUBGROUP_ID= "subgroupid";
    private static final String GROUP_ID= "groupId";

    @Override
    public ArrayList<Integer> getSubjectPreferedRoom(String subjectId, int tagId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select roomId from prefroomsubject where subjectId ='" + subjectId + "' and tagId='" + tagId + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("roomId")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Integer> getLecturersAccordingToSessionId(int sessionId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select lecturerId from sessionlecture where sessionId ='" + sessionId + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("lecturerId")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Integer> getLecturerPrefferedList(int i) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select roomId from prefroomlecturer where employeeId ='" + i + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("roomId")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Integer> getPreferredRoomListForGroup(int groupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select roomId from prefroomgroup where groupId ='" + groupId + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("roomId")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<Integer> getPreferredRoomListForSession(int sessionId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select roomId from prefroomsession where sessionId ='" + sessionId + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("roomId")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean getNotAvailableGroupStaus(String toTime, String fromTime, Integer spr, String day) throws SQLException {
        String sqlto = "select id from notavailablegroup " +
                "where  mainGroupId='" + spr + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String sqlfrom = "select id from notavailablegroup " +
                "where  mainGroupId='" + spr + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
        Statement stm = null;
        boolean result = false;
        boolean result1 = false;
        try {
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sqlto)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            try (ResultSet rst = stm.executeQuery(sqlfrom)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result1 = true;
                    } else {
                        result1 = false;
                    }
                }
            }
            if (result || result1) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }


    }

    @Override
    public boolean getNotAvailableSessionStatus(int sessionId, String day, String toTime, String fromTime) throws SQLException {
        String sqlto = "select id from notavailablesession " +
                "where  sessionId='" + sessionId + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String sqlfrom = "select id from notavailablesession " +
                "where  sessionId='" + sessionId + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";

        Statement stm = null;
        boolean result = false;
        boolean result1 = false;
        try {
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sqlto)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            try (ResultSet rst = stm.executeQuery(sqlfrom)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result1 = true;
                    } else {
                        result1 = false;
                    }
                }
            }
            if (result || result1) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public boolean getNotAvailableLectureStatus(String toTime, String fromTime, String day, Integer lec) throws SQLException {
        String sqlto = "select id from notavailablelecture " +
                "where  lectureId='" + lec + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String sqlfrom = "select id from notavailablelecture " +
                "where  lectureId='" + lec + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
        Statement stm = null;
        boolean result = false;
        boolean result1 = false;
        try {
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sqlto)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            try (ResultSet rst = stm.executeQuery(sqlfrom)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result1 = true;
                    } else {
                        result1 = false;
                    }
                }
            }
            if (result || result1) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public int getRoomSize(int roomId) throws SQLException {
        Statement stm = null;
        int result = 0;
        try {
            String sql = "select capacity from room where rid='" + roomId + "'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                if(rst.next()){
                    result = Integer.parseInt(rst.getString("capacity"));
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
    public boolean getNotAvailableSubGroupStaus(String toTime, String fromTime, int parseInt, String day) throws SQLException {
        String sqlto = "select id from notavailablegroup " +
                "where  subgroupId='" + parseInt + "' and day='" + day + "' and '" + LocalTime.parse(toTime) + "' > fromTime " +
                "and '" + LocalTime.parse(toTime) + "' < toTime";
        String sqlfrom = "select id from notavailablegroup " +
                "where  subgroupId='" + parseInt + "' and day='" + day + "' and '" + LocalTime.parse(fromTime) + "' > fromTime " +
                "and '" + LocalTime.parse(fromTime) + "' < toTime";
        Statement stm = null;
        boolean result = false;
        boolean result1 = false;
        try {
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sqlto)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result = true;
                    } else {
                        result = false;
                    }
                }
            }
            try (ResultSet rst = stm.executeQuery(sqlfrom)) {
                if (rst.next()) {
                    if (rst.getString("id") != null) {
                        result1 = true;
                    } else {
                        result1 = false;
                    }
                }
            }
            if (result || result1) {
                return true;
            } else {
                return false;
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public double getConsectiveSessionHourAccordingToSession(int sessionId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select duration from session s where s.sessionId In " +
                    "(Select consectiveId from consectivesession cs where cs.sessionId='" + sessionId + "') ";
            stm = connection.createStatement();
            double result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if(rst.next()){
                    if (rst.getString("duration") != null) {
                        result = Double.parseDouble(rst.getString("duration"));
                    } else {
                        result = 0;
                    }
                }else{
                    result=0;
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
    public int getConsectiveSessionIdAccordingToSession(int sessionId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select consectiveId from consectivesession cs where cs.sessionId='" + sessionId + "'";
            stm = connection.createStatement();
            int result = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                if(rst.next()){
                    if (rst.getString("consectiveId") != null) {
                        result = Integer.parseInt(rst.getString("consectiveId"));
                    } else {
                        result = 0;
                    }
                }else{
                    result=0;
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
    public ArrayList<Integer> getPreferredRoomListForSubGroup(int parseInt) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select roomId from prefroomgroup where subGroupId ='" + parseInt + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("roomId")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public Integer getBuilidingForLecturer(Integer i) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select buildingId from lecturer where employeeId ='" + i + "'";
            stm = connection.createStatement();
            int buidlingId = 0;
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    buidlingId = Integer.parseInt(rst.getString("buildingId"));
                }
            }
            return buidlingId;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }

    @Override
    public ArrayList<Integer> getRoomsAccordingToBuilding(Integer i) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select rid from room where buildingid ='" + i + "'";
            stm = connection.createStatement();
            ArrayList<Integer> roomList = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    roomList.add(Integer.parseInt(rst.getString("rid")));
                }
            }
            return roomList;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public SessionArray getSessionDetailsAccordingToSessionId(String s) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select su.subId,su.subName,t.tagName " +
                    "from subject su ,session s,tag t " +
                    "where s.sessionId='" + Integer.parseInt(s.trim()) + "' and s.tagId = t.tagid and s.subjectId = su.subId ";
            stm = connection.createStatement();
            SessionArray session = new SessionArray();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    session.setSubjectCode(rst.getString(SUB_ID));
                    session.setSubjectName(rst.getString(SUB_NAME));
                    session.setTagName(rst.getString(TAG_NAME));

                }
            }
            return session;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public ArrayList<String> getLecturerNamesAccordingTo(String s) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select l.employeeName " +
                    "from sessionlecture sl ,session s ,lecturer l " +
                    "where s.sessionId = sl.sessionId and s.sessionId='" + Integer.parseInt(s.trim()) + "' and l.employeeId = sl.lecturerId";
            stm = connection.createStatement();
            ArrayList<String> list = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    list.add(rst.getString("employeeName"));
                }
            }
            return list;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public String getRoomNumberAccordingToRoomId(String s) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select r.room " +
                    "from room r " +
                    "where rid='" + Integer.parseInt(s.trim()) + "' ";
            stm = connection.createStatement();
            String result = "";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString("room");
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
    public String getSubgroupIdAccordingToSession(String s) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select sg.subgroupid " +
                    "from session s, subgroup sg " +
                    "where s.subGroupId = sg.id and s.sessionId='" + Integer.parseInt(s.trim()) + "'";
            stm = connection.createStatement();
            String result = "";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString(SUBGROUP_ID);
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
    public boolean SaveTimeTable(String newday, String toTime, String fromTime, String s, String s1, String time) throws SQLException {
        String sql = "Insert into timetable values(?,?,?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, Integer.parseInt(s.trim()));
            stm.setObject(3, newday);
            stm.setObject(4, Integer.parseInt(s1.trim()));
            stm.setObject(5, toTime);
            stm.setObject(6, fromTime);
            stm.setObject(7, time);
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public boolean getRoomIsAvailable(String toTime, String fromTime, String day, int roomId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select id " +
                    "from timetable t " +
                    "where toTime='" + toTime + "' and fromTime='" + fromTime + "' and roomId='" + roomId + "' and day='" + day + "' ";
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
    public ArrayList<LecturerTimeTable> getLectureTimeTableDetails(String lecName) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select  t.day,r.room,t.timeString,sb.subName,ta.tagName,sb.subId,s.groupId,s.subGroupId " +
                    "from session s,timetable t , sessionlecture sl, room r,lecturer l ,subject sb ,tag ta " +
                    "where s.sessionId = t.sessionId and s.sessionId=sl.sessionId and sl.lecturerId = l.employeeId " +
                    "and sb.subId = s.subjectId and r.rid = t.roomId and ta.tagid=s.tagId " +
                    " and l.employeeName ='" + lecName.trim() + "'";
            stm = connection.createStatement();
            boolean result = false;
            ArrayList<LecturerTimeTable> lecturerTimeTables = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    LecturerTimeTable t1 = new LecturerTimeTable();
                    t1.setDay(rst.getString("day"));
                    t1.setRomm(rst.getString("room"));
                    t1.setTagName(rst.getString(TAG_NAME));
                    t1.setSubName(rst.getString(SUB_NAME));
                    t1.setTimeString(rst.getString("timeString"));
                    t1.setMainGroupId(rst.getString(GROUP_ID));
                    t1.setSubGroupId(rst.getString(SUBGROUP_ID));
                    t1.setSubCode(rst.getString(SUB_ID));
                    lecturerTimeTables.add(t1);
                }
            }
            return lecturerTimeTables;
        } finally {
            if (stm != null) {
                stm.close();
            }
        }
    }

    @Override
    public String getSubGroupId(int parseInt) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select subgroupid " +
                    "from subgroup sg " +
                    "where sg.id='" + parseInt + "' ";
            stm = connection.createStatement();
            String result = "";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString(SUBGROUP_ID);
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
    public String getMainGroupId(int parseInt) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select groupid " +
                    "from maingroup mg " +
                    "where mg.id='" + parseInt + "' ";
            stm = connection.createStatement();
            String result = "";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString(GROUP_ID);
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
    public ArrayList<RoomTimeTable> getTimeTableForRoom(String center, String building, String room) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select t.day,t.timeString,sb.subName,ta.tagName,sb.subId,t.sessionId,s.groupId,s.subGroupId " +
                    "from session s,timetable t ,subject sb ,tag ta " +
                    "where s.sessionId = t.sessionId and sb.subId = s.subjectId and ta.tagid=s.tagId " +
                    "and t.roomId In (select r.rid " +
                    "from room r ,building b  " +
                    "where r.buildingid = b.bid and  b.center='" + center + "' " +
                    "and b.building='" + building + "' and r.room='" + room + "') ";
            stm = connection.createStatement();
            ArrayList<RoomTimeTable> result = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    RoomTimeTable roomTimeTable = new RoomTimeTable();
                    roomTimeTable.setDay(rst.getString("day"));
                    roomTimeTable.setTagName(rst.getString(TAG_NAME));
                    roomTimeTable.setSubName(rst.getString(SUB_NAME));
                    roomTimeTable.setTimeString(rst.getString("timeString"));
                    roomTimeTable.setMainGroupId(rst.getString(GROUP_ID));
                    roomTimeTable.setSubGroupId(rst.getString(SUBGROUP_ID));
                    roomTimeTable.setSubCode(rst.getString(SUB_ID));
                    roomTimeTable.setSessionId(rst.getString("sessionId"));
                    result.add(roomTimeTable);
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
    public ArrayList<ParallelSession> getParalleSessions(String id) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select sb.subName,ta.tagName,sb.subId,s.sessionId,sb.subName,mg.groupId,s.subgroupid,s.category " +
                    "from session s,subject sb ,tag ta,maingroup mg " +
                    "where  sb.subId = s.subjectId and ta.tagid=s.tagId and s.isParallel='Yes' " +
                    "and s.groupId=mg.id ";
            stm = connection.createStatement();
            ArrayList<ParallelSession> result = new ArrayList<>();
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    ParallelSession roomTimeTable = new ParallelSession();
                    roomTimeTable.setSessionId(Integer.parseInt(rst.getString("sessionId")));
                    roomTimeTable.setCategory(rst.getString("category"));
                    roomTimeTable.setGroupId(rst.getString(GROUP_ID));
                    roomTimeTable.setSubgroupid(rst.getString(SUBGROUP_ID));
                    roomTimeTable.setTagName(rst.getString(TAG_NAME));
                    roomTimeTable.setSubjectName(rst.getString(SUB_NAME));
                    result.add(roomTimeTable);
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
    public String getResult() throws SQLException {
        Statement stm = null;
        try {
            String sql = "select orderId from parrellsessions order by 1 desc limit 1";
            stm = connection.createStatement();
            String result = "";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString("orderId");
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
    public boolean addParallelSessions(ParallelSession p, String orderID) throws SQLException {
        String sql = "Insert into parrellsessions values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, p.getSessionId());
            stm.setObject(3, orderID);
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public String getParallelSesionOrderNumberAccordingToId(int sessionId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select orderId from parrellsessions where sessionId='" + sessionId + "'";
            stm = connection.createStatement();
            String result = "";
            try (ResultSet rst = stm.executeQuery(sql)) {
                while (rst.next()) {
                    result = rst.getString("orderId");
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
    public boolean saveGroupPdf(String fileBytes, String groupId) throws SQLException {
        String sql = "Insert into savegrouptimetable values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, groupId);
            stm.setObject(3, fileBytes);
            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }

    @Override
    public String getPdf(String groupId) throws SQLException {
        Statement stm = null;
        try {
            String sql = "select id,file from savegrouptimetable where groupId='" + groupId + "'";
            stm = connection.createStatement();
            try (ResultSet rst = stm.executeQuery(sql)) {
                if (rst.next()) {
                    return rst.getString("file");
                } else {
                    return null;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
        }

    }


}

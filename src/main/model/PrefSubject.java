package main.model;

public class PrefSubject {

    private int id;
    private int tagId;
    private String subjectId;
    private int roomId;
    private String tagName;
    private String subName;
    private String centerName;
    private String buidlingName;
    private String roomName;

    public PrefSubject() { }

    public PrefSubject(int id, int tagId, String subjectId, int roomId) {
        this.id = id;
        this.tagId = tagId;
        this.subjectId = subjectId;
        this.roomId = roomId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getBuidlingName() {
        return buidlingName;
    }

    public void setBuidlingName(String buidlingName) {
        this.buidlingName = buidlingName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}

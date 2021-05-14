package main.model;

public class NotAvailableLecturer {
    private int id;
    private String day;
    private String toTime;
    private String FromTime;
    private int lectureId;
    private String lectureName;

    public NotAvailableLecturer() {
    }

    public NotAvailableLecturer(int id, String day, String toTime, String fromTime, int lectureId, String lectureName) {
        this.id = id;
        this.day = day;
        this.toTime = toTime;
        FromTime = fromTime;
        this.lectureId = lectureId;
        this.lectureName = lectureName;
    }

    public NotAvailableLecturer(int id, String day, String toTime, String fromTime, int lectureId) {
        this.id = id;
        this.day = day;
        this.toTime = toTime;
        FromTime = fromTime;
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}

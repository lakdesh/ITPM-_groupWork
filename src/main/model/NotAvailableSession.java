package main.model;

public class NotAvailableSession {

    private int id;
    private int sessionId;
    private String day;
    private String toTime;
    private String fromTime;

    public NotAvailableSession() {
    }

    public NotAvailableSession(int id, int sessionId, String day, String toTime, String fromTime) {
        this.id = id;
        this.sessionId = sessionId;
        this.day = day;
        this.toTime = toTime;
        this.fromTime = fromTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }
}

package main.model;

public class NotAvailableGroup {
    private int id;
    private String day;
    private String toTime;
    private String fromTime;
    private String groupId;
    private int  subGroupId;
    private int  mainGroupId;

    public NotAvailableGroup() {
    }

    public NotAvailableGroup(int id, String day, String toTime, String fromTime, String groupId, int subGroupId, int mainGroupId) {
        this.id = id;
        this.day = day;
        this.toTime = toTime;
        this.fromTime = fromTime;
        this.groupId = groupId;
        this.subGroupId = subGroupId;
        this.mainGroupId = mainGroupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public int getSubGroupId() {
        return subGroupId;
    }

    public void setSubGroupId(int subGroupId) {
        this.subGroupId = subGroupId;
    }

    public int getMainGroupId() {
        return mainGroupId;
    }

    public void setMainGroupId(int mainGroupId) {
        this.mainGroupId = mainGroupId;
    }
}

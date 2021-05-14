package main.model;

public class WorkingDaysSub {

    private int subId;
    private int workingId;
    private String workingday;

    public WorkingDaysSub() {
    }

    public WorkingDaysSub(int workingId, String workingday) {
        this.workingId = workingId;
        this.workingday = workingday;
    }

    public WorkingDaysSub(int subId, int workingId, String workingday) {
        this.subId = subId;
        this.workingId = workingId;
        this.workingday = workingday;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public int getWorkingId() {
        return workingId;
    }

    public void setWorkingId(int workingId) {
        this.workingId = workingId;
    }

    public String getWorkingday() {
        return workingday;
    }

    public void setWorkingday(String workingday) {
        this.workingday = workingday;
    }
}

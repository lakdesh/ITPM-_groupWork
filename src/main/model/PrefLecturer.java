package main.model;

public class PrefLecturer {

    int id;
    int employeeId;
    int roomId;

    public PrefLecturer() { }

    public PrefLecturer(int id, int employeeId, int roomId) {
        this.id = id;
        this.employeeId = employeeId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}

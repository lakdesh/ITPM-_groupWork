package main.model;

public class PrefSession {

    int id;
    int sessionId;
    int roomId;

    public PrefSession() { }

    public PrefSession(int id, int sessionId, int roomId) {
        this.id = id;
        this.sessionId = sessionId;
        this.roomId = roomId;
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

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}

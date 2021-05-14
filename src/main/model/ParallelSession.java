package main.model;

public class ParallelSession {
    private int id;
    private int sessionId;
    private String groupId;
    private String subjectName;
    private String tagName;
    private String category;
    private String subgroupid;

    public ParallelSession() {
    }

    public ParallelSession(int id, int sessionId, String groupId, String subjectName, String tagName, String category) {
        this.id = id;
        this.sessionId = sessionId;
        this.groupId = groupId;
        this.subjectName = subjectName;
        this.tagName = tagName;
        this.category = category;
    }

    public String getSubgroupid() {
        return subgroupid;
    }

    public void setSubgroupid(String subgroupid) {
        this.subgroupid = subgroupid;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

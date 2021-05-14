package main.service;

import main.model.PrefSubject;

import java.sql.SQLException;

public interface PrefSubjectService {

    String getSubIdFromSubjects(String subject) throws SQLException;

    boolean savePrefSubjectRoom(PrefSubject prefSub) throws SQLException;
}

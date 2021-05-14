package main.service;

import main.model.PrefSession;

import java.sql.SQLException;

public interface PrefSessionService {
    boolean savePrefSessionRoom(PrefSession prefSession) throws SQLException;
}

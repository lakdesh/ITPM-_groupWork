package main.service;

import main.model.PrefReserved;

import java.sql.SQLException;

public interface PrefReservedService {
    boolean savePrefReservedRoom(PrefReserved prefRes) throws SQLException;
}

package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefSession;
import main.service.PrefSessionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class PrefSessionServiceImpl implements PrefSessionService {

    private Connection connection;
    public PrefSessionServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean savePrefSessionRoom(PrefSession prefSession) throws SQLException {
        String sql = "Insert into  prefroomsession Values(?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, prefSession.getSessionId());
            stm.setObject(3, prefSession.getRoomId());

            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }
}

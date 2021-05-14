package main.service.impl;

import main.dbconnection.DBConnection;
import main.model.PrefReserved;
import main.service.PrefReservedService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;

public class PrefReservedServiceImpl implements PrefReservedService {

    private Connection connection;
    public PrefReservedServiceImpl() {
        connection = DBConnection.getInstance().getConnection();
    }

    @Override
    public boolean savePrefReservedRoom(PrefReserved prefRes) throws SQLException {
        String sql = "Insert into prefroomreserved Values(?,?,?,?,?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        try {
            stm.setObject(1, 0);
            stm.setObject(2, prefRes.getRoomId());
            stm.setObject(3, prefRes.getDay());
            stm.setObject(4, LocalTime.parse(prefRes.getToTime()));
            stm.setObject(5, LocalTime.parse(prefRes.getFromTime()));

            int res = stm.executeUpdate();
            return res > 0;
        } finally {
            stm.close();
        }
    }
}

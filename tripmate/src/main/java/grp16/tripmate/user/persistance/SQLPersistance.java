package grp16.tripmate.user.persistance;

import grp16.tripmate.db.DatabaseConnectionException;
import grp16.tripmate.db.connection.DatabaseConnection;
import grp16.tripmate.db.connection.DatabaseConnectionDAO;
import grp16.tripmate.user.model.User;

import java.sql.Connection;

public class SQLPersistance implements IUserPersistance{
    DatabaseConnectionDAO connectionDAO = new DatabaseConnection();
    Connection connection;

    @Override
    public User getUser() throws Exception {
        connection = connectionDAO.getDatabaseConnection();

        return null;
    }
}
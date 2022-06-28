package finanzmanager;

import database.JavaPostgres;
import modelclasses.UserLogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DauerauftragLogik {

    public void test(int id_dauerauftrag) throws SQLException {

        int id = UserLogin.id;

        JavaPostgres javaPostgres = new JavaPostgres();
        Connection connection = javaPostgres.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM dauerauftrag WHERE user_ausgabenid=?");
            statement.setInt(1, id);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM dauerauftrag ");
            ResultSet res = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package predefinedDatabasetable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


class PostgreSQLJDBC {
    public static void main(String[] args) {
        Connection connectionDb;
        Statement statementDb;
        try {
            Class.forName("org.postgresql.Driver");
            connectionDb = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/FinanzmanagerDb",
                            "postgres", "root");
            System.out.println("Opened database successfully");

            statementDb = connectionDb.createStatement();

            String sql = "CREATE TABLE USERINFO " +
                    "(Fullname           TEXT     NOT NULL," +
                    " Username           TEXT     NOT NULL," +
                    " Password           TEXT     NOT NULL)";
            statementDb.executeUpdate(sql);

/*

            sql = "INSERT INTO Finanzen (ID,Username,Password) "
                    + "VALUES (1, 'Paul', '54665423' );";
            statementDb.executeUpdate(sql);

            sql = "INSERT INTO Finanzen (ID,Username,Password) "
                    + "VALUES (2, 'Max', '123654' );";
            statementDb.executeUpdate(sql);

*/

            ResultSet rs = statementDb.executeQuery("SELECT * FROM USERINFO;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                int age = rs.getInt("password");

                System.out.println("ID = " + id);
                System.out.println("Username = " + name);
                System.out.println("Password= " + age);

                System.out.println();
            }
            rs.close();


            statementDb.close();
            connectionDb.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
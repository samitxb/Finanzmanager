package com.example.finanzmanager_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


class PostgreSQLJDBC {
    public static void main(String[] args) {
        Connection c;
        Statement stmt;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/Finanzmanager",
                            "postgres", "root");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE Finanzen " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " Username           TEXT    NOT NULL, " +
                    " Password            INT     NOT NULL)";
            //stmt.executeUpdate(sql);

            sql = "INSERT INTO Finanzen (ID,Username,Password) "
                    + "VALUES (1, 'Paul', '54665423' );";
            //stmt.executeUpdate(sql);

            sql = "INSERT INTO Finanzen (ID,Username,Password) "
                    + "VALUES (2, 'Max', '123654' );";
            //stmt.executeUpdate(sql);

            ResultSet rs = stmt.executeQuery("SELECT * FROM Finanzen;");
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


            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
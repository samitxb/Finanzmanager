package com.example.finanzmanager_java;

import java.sql.Connection;
import java.sql.DriverManager;
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
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
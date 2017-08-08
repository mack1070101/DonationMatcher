package controllers;


import models.Person;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by mackenzie on 07/08/17.
 */
public class DatabaseController {
    private String databaseName = "results.db";
    private Connection conn;
    Statement statement = null;

    public DatabaseController(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:results.db");

            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createPersonsTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS Person;");
        String sqlStatement = "CREATE TABLE Person" +
                                "(firstName TEXT NOT NULL," +
                                "lastName TEXT NOT NULL," +
                                "street TEXT NOT NULL, " +
                                "city TEXT NOT NULL, " +
                                "state TEXT NOT NULL, " +
                                "postal TEXT NOT NULL, " +
                                "country TEXT NOT NULL, " +
                                "email TEXT NOT NULL," +
                                "phone INT PRIMARY KEY);";
        statement.executeUpdate(sqlStatement);
    }

    public void insertIntoPersonTable(String[] string) throws SQLException {
        String queryString = "INSERT INTO Person VALUES('"+string[0]+"','"+string[1]+"'," + "'"+string[2]+"','"+string[3]+"','"+string[4]+"','"+string[5]+"'," + "'"+string[6]+"','"+string[7]+"','"+string[8]+"');";

        System.out.println(queryString);
        this.statement.executeUpdate("INSERT INTO Person VALUES('"+string[0]+"','"+string[1]+"'," + "'"+string[2]+"','"+string[3]+"','"+string[4]+"','"+string[5]+"'," + "'"+string[6]+"','"+string[7]+"','"+string[8]+"');");

    }


}

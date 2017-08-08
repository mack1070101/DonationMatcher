package controllers;

import models.Pickup;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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

    public void createPickupTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS Pickup;");
        String sqlstatement = "CREATE TABLE Pickup" +
                "(personId TEXT NOT NULL," +
                "latitude REAL NOT NULL," +
                "longitude REAL NOT NULL, " +
                "categories int NOT NULL," +
                "PickupAt TEXT NOT NULL, " +
                "TimeZoneId TEXT NOT NULL);";
        statement.executeUpdate(sqlstatement);
    }

    public void createRecipientTable() throws SQLException {
        statement.executeUpdate("DROP TABLE IF EXISTS Recipient;");
        String sqlstatement = "CREATE TABLE Recipient" +
                "(personId TEXT NOT NULL," +
                "latitude REAL NOT NULL," +
                "longitude REAL NOT NULL, " +
                "restrictions INT NOT NULL," +
                "sundayHours INT NOT NULL," +
                "mondayHours INT NOT NULL," +
                "tuesdayHours INT NOT NULL," +
                "wednesdayHours INT NOT NULL," +
                "thursdayHours INT NOT NULL," +
                "fridayHours INT NOT NULL," +
                "saturdayHours INT NOT NULL);";
        statement.executeUpdate(sqlstatement);
    }

    public void insertIntoPersonTable(String[] string) throws SQLException {
        this.statement.executeUpdate("INSERT INTO Person VALUES('"+string[0]+"','"+string[1]+"'," + "'"+string[2]+"','"+string[3]+"','"+string[4]+"','"+string[5]+"'," + "'"+string[6]+"','"+string[7]+"','"+string[8]+"');");
    }

     public void insertIntoPickupTable(String[] string) throws SQLException {
         this.statement.executeUpdate("INSERT INTO Pickup VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] +  "');");
     }

     public void insertIntoRecipientTable(String[] string) throws SQLException {
         this.statement.executeUpdate("INSERT INTO Recipient VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "'," + "'" + string[6] + "','" + string[7] + "','" + string[8] + "','" + string[9] + "','" + string[10] + "');");
     }

     public ResultSet fetchAllPickups() throws SQLException {
        return this.statement.executeQuery("SELECT * FROM pickup;");
     }

     public ResultSet fetchSuitableRecipients(Pickup pickup) throws SQLException {
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E"); // Day of week
         String dayOfWeek = simpleDateFormat.format(pickup.getPickupAt());
         simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
         simpleDateFormat.setTimeZone(TimeZone.getTimeZone(pickup.getTimeZoneId()));
         String time = simpleDateFormat.format(pickup.getPickupAt());
         int timeAsInt = Integer.parseInt(time.substring(0,2));
         int binaryRepTime = 1 << timeAsInt -8;

         String dateLine;

         if (dayOfWeek.equals("Sun")) {
             dateLine = "((sundayHours & " + binaryRepTime+ ") = " + binaryRepTime + ")";
         } else if (dayOfWeek.equals("Mon")) {
             dateLine = "((mondayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
         } else if (dayOfWeek.equals("Tue")) {
             dateLine = "((tuesdayHours & " + binaryRepTime + ") = " + binaryRepTime +")" ;
         } else if (dayOfWeek.equals("Wed")) {
             dateLine = "((wednesdayHours & " + binaryRepTime + ") = " + binaryRepTime +")" ;
         } else if (dayOfWeek.equals("Thu")) {
              dateLine = "((thursdayHours & " + binaryRepTime + ") = " + binaryRepTime +")" ;
         } else if (dayOfWeek.equals("Fri")) {
             dateLine = "((fridayHours & " + binaryRepTime + ") = " + binaryRepTime +")" ;
         } else if (dayOfWeek.equals("Sat")) {
             dateLine = "((saturdayHours & " + binaryRepTime + ") = " + binaryRepTime +")" ;
         } else {
             throw new IllegalArgumentException();
         }

        String query = "SELECT * FROM RECIPIENT " +
                        "WHERE "+ dateLine + ";" ;

         System.out.println(query + time + " " + pickup.getPersonId());

        return statement.executeQuery(query);
     }
}

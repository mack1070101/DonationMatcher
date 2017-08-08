package controllers;

import models.Pickup;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by mackenzie on 07/08/17.
 *
 * A controller for handling all interaction with the database
 * used to store and query for information.
 *
 *
 * @TODO in the future, track amounts of food to factor that in on matching
 */
public class DatabaseController {
    private String databaseName = "results.db";
    private Connection conn;
    Statement statement = null;

    /**
     * Constructor for database controller
     */
    public DatabaseController(){
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:results.db");

            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Generates table in SQLITE to hold person objects
     * @throws SQLException
     */
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

    /**
     * Generates table in SQLITE to hold pickup objects
     * @throws SQLException
     */
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

    /**
     * Generates table in SQLITE to hold recipient objects
     * @throws SQLException
     */
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

    /**
     *  Inserts the passed in array of strings into the persons table
     *  from where it is called in CLI main. It is currently taking
     *  a string for expediency
     *
     *  @TODO make it take person object.
     * @param string
     * @throws SQLException
     */
    public void insertIntoPersonTable(String[] string) throws SQLException {
        this.statement.executeUpdate("INSERT INTO Person VALUES('"+string[0]+"','"+string[1]+"'," + "'"+string[2]+"','"+string[3]+"','"+string[4]+"','"+string[5]+"'," + "'"+string[6]+"','"+string[7]+"','"+string[8]+"');");
    }

    /**
     *  Inserts the passed in array of strings into the pickup table
     *  from where it is called in CLI main. It is currently taking
     *  a string for expediency
     *
     *  @TODO make it take pickup object.
     * @param string
     * @throws SQLException
     */
    public void insertIntoPickupTable(String[] string) throws SQLException {
        this.statement.executeUpdate("INSERT INTO Pickup VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] +  "');");
    }


    /**
     *  Inserts the passed in array of strings into the persons table
     *  from where it is called in CLI main. It is currently taking
     *  a string for expediency
     *
     *  @TODO make it take recipient object.
     * @param string
     * @throws SQLException
     */
    public void insertIntoRecipientTable(String[] string) throws SQLException {
        this.statement.executeUpdate("INSERT INTO Recipient VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "'," + "'" + string[6] + "','" + string[7] + "','" + string[8] + "','" + string[9] + "','" + string[10] + "');");
    }

    /**
     * Fetches all the recipients that match the criteria for a match.
     * It builds a query string to execute as a ResultSet so that
     * the database can be queried line by line in your main function to reduce
     * memory overhead in the event of a large database
     *
     *
     * NOTE: Distance query is done as a simple bounding box. An Rtree would be implemented
     * in the future to support rapid geographic queries
     * @param pickup
     * @return result set to iterate on in main
     * @throws SQLException
     */
    public ResultSet fetchSuitableRecipients(Pickup pickup) throws SQLException {
        // Pull out day of week
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        String dayOfWeek = simpleDateFormat.format(pickup.getPickupAt());
        //Pull out time and set time zone
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(pickup.getTimeZoneId()));
        String time = simpleDateFormat.format(pickup.getPickupAt());
        //Determine binary representation of time
        int timeAsInt = Integer.parseInt(time.substring(0,2));
        int binaryRepTime = 1 << timeAsInt -8;

        //Sidelength requirement
        double side = 10 * 1.609344; // Sidelength of 10 miles converted to KM

        //String that will be used to build time requirement for query
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

        // Build category requirement
        // Used to implement bitwise XOR in SQLITE as it lacks many nice things
        // https://stackoverflow.com/questions/16440831/bitwise-xor-in-sqlite-bitwise-not-not-working-as-i-expect
        String restrictions = "(~(restrictions&"+ pickup.getCategory()+"))&(restrictions|"+ pickup.getCategory()+"" +
                ") <= " +pickup.getCategory()+"";

        //Build distance requirement
        GeoController geoController = new GeoController();
        double boundingBox[] = geoController.boundingBox(pickup.getLatitude(), pickup.getLongitude(), side);

        String distance = "((latitude >= "+boundingBox[0]+") AND (latitude <= "+boundingBox[1]+"))";
        distance = distance + " AND " + "((longitude >= "+ boundingBox[2]+") AND (longitude <= "+boundingBox[3]+"))";

        //Build query
        String query = "SELECT * FROM RECIPIENT " +
                "WHERE "+ dateLine + " AND " +
                        restrictions + " AND " +
                        distance + " " +
                "ORDER BY restrictions DESC;" ;


//        System.out.println(query);

        return statement.executeQuery(query);
    }
}

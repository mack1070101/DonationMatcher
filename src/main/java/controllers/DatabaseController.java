package controllers;

import models.Person;
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
 * TODO in the future, track amounts of food to factor that in on matching
 * TODO convert to singleton pattern
 */
public class DatabaseController {
    private Connection conn;
    Statement statement = null;
    final int foodFieldMax = 63;

    /**
     * Constructor for database controller
     */
    public DatabaseController() {
        try {
            Class.forName("org.sqlite.JDBC");
            String databaseName = "results.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor for database controller
     * @param databaseName name of database you want to use
     */
    public DatabaseController(String databaseName) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" +databaseName);

            statement = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Generates table in SQLITE to hold person objects
     *
     * @throws SQLException     if problem occurs
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
     *
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
     *
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
     * Inserts the passed in array of strings into the persons table
     * from where it is called in CLI main. It is currently taking
     * a string for expediency. Note it will take empty strings for
     * every field execept Phone. This was done to ensure maximum flexibility
     * and so that inputs are not lost.
     *
     * @param string array of strings containing person data
     * @throws SQLException
     * TODO make it take person object.
     */
    public void insertIntoPersonTable(String[] string) throws SQLException {
        if(!string[8].matches(".*\\w.*")) throw new SQLException(); // Phone number must be specified
        this.statement.executeUpdate("INSERT INTO Person VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "'," + "'" + string[6] + "','" + string[7] + "','" + string[8] + "');");
    }

    /**
     * Inserts the passed in array of strings into the pickup table
     * from where it is called in CLI main. It is currently taking
     * a string for expediency
     *
     * @param string containing pickup data
     * @throws SQLException
     * TODO make it take pickup object.
     */
    public void insertIntoPickupTable(String[] string) throws SQLException {
        this.statement.executeUpdate("INSERT INTO Pickup VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "');");
    }


    /**
     * Inserts the passed in array of strings into the persons table
     * from where it is called in CLI main. It is currently taking
     * a string for expediency
     *
     * @param string array of strings that represents a person data
     * @throws SQLException
     * TODO make it take recipient object.
     */
    public void insertIntoRecipientTable(String[] string) throws SQLException {
        this.statement.executeUpdate("INSERT INTO Recipient VALUES('" + string[0] + "','" + string[1] + "'," + "'" + string[2] + "','" + string[3] + "','" + string[4] + "','" + string[5] + "'," + "'" + string[6] + "','" + string[7] + "','" + string[8] + "','" + string[9] + "','" + string[10] + "');");
    }

    /**
     * Fetches all the recipients that match the criteria for a match.
     * It builds a query string to execute as a ResultSet so that
     * the database can be queried line by line in your main function to reduce
     * memory overhead in the event of a large database
     * <p>
     * <p>
     * NOTE: Distance query is done as a simple bounding box. An Rtree would be implemented
     * in the future to support rapid geographic queries
     *
     * @param pickup pickup object that suitable recipients should be found for
     * @return result set to iterate on in main
     * @throws SQLException
     */
    public ResultSet fetchSuitableRecipients(Pickup pickup) throws SQLException {
        // Pull out day of week
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
        String dayOfWeek = simpleDateFormat.format(pickup.getPickupAt());
        String fullDayOfWeek;
        //Pull out time and set time zone
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(pickup.getTimeZoneId()));
        String time = simpleDateFormat.format(pickup.getPickupAt());
        //Determine binary representation of time
        int timeAsInt = Integer.parseInt(time.substring(0, 2));
        int binaryRepTime = 1 << timeAsInt - 8;

        //Sidelength requirement
        double side = 10; // 10 miles

        //String that will be used to build time requirement for query
        String dateLine;

        if (dayOfWeek.equals("Sun")) {
            dateLine = "((sundayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
            fullDayOfWeek = "sundayHours";
        } else if (dayOfWeek.equals("Mon")) {
            dateLine = "((mondayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
                        fullDayOfWeek = "mondayHours";
        } else if (dayOfWeek.equals("Tue")) {
            dateLine = "((tuesdayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
                        fullDayOfWeek = "tuesdayHours";
        } else if (dayOfWeek.equals("Wed")) {
            dateLine = "((wednesdayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
                        fullDayOfWeek = "wednesdayHours";
        } else if (dayOfWeek.equals("Thu")) {
            dateLine = "((thursdayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
                        fullDayOfWeek = "thursdayHours";
        } else if (dayOfWeek.equals("Fri")) {
            dateLine = "((fridayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
                        fullDayOfWeek = "fridayHours";
        } else if (dayOfWeek.equals("Sat")) {
            dateLine = "((saturdayHours & " + binaryRepTime + ") = " + binaryRepTime + ")";
                        fullDayOfWeek = "saturdayHours";
        } else {
            throw new IllegalArgumentException();
        }

        // Build category requirement
        // Used to implement bitwise XOR in SQLITE as it lacks many nice things
        // https://stackoverflow.com/questions/16440831/bitwise-xor-in-sqlite-bitwise-not-not-working-as-i-expect
        String restrictions = "((((~(restrictions&" + foodFieldMax + "))&(restrictions|" + foodFieldMax + "" +
                ")) & " + pickup.getCategory() + ") != 0)";


        //Build distance requirement
        GeoController geoController = new GeoController();

        double boundingBox[] = geoController.boundingBox(pickup.getLatitude(), pickup.getLongitude(), side);

        String distance = "((latitude >= " + boundingBox[0] + ") AND (latitude <= " + boundingBox[1] + "))";
        distance = distance + " AND " + "((longitude >= " + boundingBox[2] + ") AND (longitude <= " + boundingBox[3] + "))";

        //Build query
        String query = "SELECT * FROM RECIPIENT " +
                "WHERE " + dateLine + " AND " +
                restrictions + " AND " +
                distance + " " +
                "ORDER BY restrictions ASC, " +fullDayOfWeek+" DESC;";

        return statement.executeQuery(query);
    }

    public Pickup getPickup(String personId) throws SQLException {
        String query = "SELECT * FROM pickup " +
                "WHERE personId = " + "'"+personId+"'" + ";";

        ResultSet resultSet = statement.executeQuery(query);

        Pickup pickup;
        resultSet.next();
        String person = resultSet.getString("personId");
        double latitude = resultSet.getDouble("latitude");
        double longitude = resultSet.getDouble("latitude");
        int categories = resultSet.getInt("categories");
        String pickupAt = resultSet.getString("pickupAt");
        String timeZoneId = resultSet.getString("timeZoneId");

        pickup = new Pickup(person, latitude, longitude, categories,pickupAt, timeZoneId);
        return pickup;
    }


    /**
     * TODO future improvement: cache the Person
     * Fetches from the database and returns a Person object
     *
     * @param personId identifier of the person
     * @return a Person object
     * @throws SQLException
     */
    public Person getPerson(String personId) throws SQLException {
        String query = "SELECT * FROM person " +
                "WHERE phone = " + "'" + personId + "'" + ";";
        Person person;

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String street = resultSet.getString("street");
        String city = resultSet.getString("city");
        String state = resultSet.getString("state");
        String postal = resultSet.getString("postal");
        String country = resultSet.getString("country");
        String email = resultSet.getString("email");
        String phone = resultSet.getString("phone");
        person = new Person(firstName, lastName, street,
                city, state, postal, country, email,
                phone);
        return person;

    }
    public void close() throws SQLException {
        statement.close();
        conn.close();
    }
}

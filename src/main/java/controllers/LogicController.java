package controllers;

import models.Pickup;
import models.Recipient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mackenzie on 08/08/17.
 *
 * http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates#Latitude
 * used for a semi crude implementation of geographic distance
 * Would use sqlite and Rtrees if time allowed
 *
 * Handles the logic of querying and selecting the appropriate matches
 * @TODO change from keying on PERSONID to a UUID?
 * @TODO in the future, track amounts of food to factor that in on matching
 */
public class LogicController {
    private DatabaseController dbc;

    /**
     * Constructor for a logic controller
     * @param dbc
     */
    public LogicController(DatabaseController dbc) {
        this.dbc = dbc;
    }

    /**
     * Creates a hashmap contianing the ID of a pickup and their matching recipients
     *
     * @return a hashmap that has a key of a pickup and an arraylist of recipients
     * @throws SQLException
     */
    public HashMap<String, ArrayList<Recipient>> findMatches() throws SQLException {
        HashMap<String, ArrayList<Recipient>> map = new HashMap<String, ArrayList<Recipient>>();

        // Make array of pickups  @TODO refactor to just pull ID
        ResultSet rs = dbc.statement.executeQuery("SELECT personId from pickup;");
        ArrayList<String> pickupStrings = new ArrayList<String>();
        while (rs.next()) {
            String personId = rs.getString("personId");

            pickupStrings.add(p);
        }

        // Search array of pickups for suitable recipients and return them
        // maybe this should be moved into database controller @TODO
        for(Pickup p : pickups){
            rs = dbc.fetchSuitableRecipients(p);
            ArrayList<Recipient> recipients = new ArrayList<Recipient>();

            // Query for matching recipients
            while (rs.next()){
                 String personId = rs.getString("personId");
                 double latitude = rs.getDouble("latitude");
                 double longitude = rs.getDouble("longitude");
                 int restrictions = rs.getInt("restrictions");
                 int sundayHours = rs.getInt("sundayHours");
                 int mondayHours = rs.getInt("mondayHours");
                 int tuesdayHours = rs.getInt("tuesdayHours");
                 int wednesdayHours = rs.getInt("wednesdayHours");
                 int thursdayHours = rs.getInt("thursdayHours");
                 int fridayHours = rs.getInt("fridayHours");
                 int saturdayHours = rs.getInt("saturdayHours");

                 Recipient r = new Recipient(personId, latitude, longitude,
                     restrictions, sundayHours, mondayHours,
                     tuesdayHours, wednesdayHours, thursdayHours,
                     fridayHours, saturdayHours);

                 recipients.add(r);
            }
            // Insert into map the ID and recipients
            map.put(p.getPersonId(), recipients);
        }
        return map;
    }
}
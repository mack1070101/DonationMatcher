package controllers;

import models.Person;
import models.Pickup;
import models.Recipient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by mackenzie on 08/08/17.
 *
 * http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates#Latitude
 * used for a semi crude implementation of geographic distance
 * Would use sqlite and Rtrees if time allowed
 */
public class LogicController {
    private DatabaseController dbc;
    private int earthRadius = 3959; //miles
    private double angluarRadius = 10 / earthRadius;

    public LogicController(DatabaseController dbc) {
        this.dbc = dbc;
    }

    public void findMatches() throws SQLException {
        ResultSet rs = dbc.statement.executeQuery("SELECT * from pickup;");
        ArrayList<Pickup> pickups = new ArrayList<Pickup>();
        HashMap<Pickup, ArrayList<Recipient>> map = new HashMap<Pickup, ArrayList<Recipient>>();

        while (rs.next()) {
            // Make array of pickups
            String personId = rs.getString("personId");
            double latitude = rs.getDouble("latitude");
            double longitude = rs.getDouble("longitude");
            int category = rs.getInt("categories");
            String pickupAt = rs.getString("pickupAt");
            String timeZoneId = rs.getString("timeZoneId");
            Pickup p = new Pickup(personId, latitude, longitude, category,
                    pickupAt, timeZoneId);
            pickups.add(p);
        }
        // Search array of pickups for suitable recipients and return them

        for(Pickup p : pickups){
            rs = dbc.fetchSuitableRecipients(9);
            ArrayList<Recipient> recipients = new ArrayList<Recipient>();

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
            map.put(p, recipients);

        }
    }
}